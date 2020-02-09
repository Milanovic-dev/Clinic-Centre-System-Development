package controller;

import helpers.DateInterval;
import helpers.DateUtil;
import helpers.Scheduler;
import model.*;
import model.Appointment.AppointmentType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.*;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/scheduled")
public class ScheduledAppointmentsController {

	private final static Logger logger = LoggerFactory.getLogger(ScheduledAppointmentsController.class);
	
    @Autowired
    AppointmentRequestService appointmentRequestService;
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    HallService hallService;  
    @Autowired
    DoctorService doctorService;   
    @Autowired
    UserService userService;

    @Scheduled(cron = "0 0 0 * * *")
    public void onSchedule() {
    	logger.info("Starting automatic scheduling.");
        reserveAlgorithmSurgery();
        reserveAlgorithmExamination();
        logger.info("Automatic scheduling ended.");
    }

    public void reserveAlgorithmSurgery() {

        List<AppointmentRequest> requests = appointmentRequestService.findAllSurgeries();

        System.out.println("OPERACIJE : " + requests.size() );

        boolean done;
        boolean nemoze;
        List<DateInterval> intervals;

        for (AppointmentRequest r : requests) {
            done = false;
            List<Hall> halls = hallService.findAllByClinic(r.getClinic());
            for (Hall h : halls) {
                if (done) continue;
                nemoze = false;
                List<Appointment> appointments = appointmentService.findAllByHall(h);


                Date endDate = Scheduler.addHoursToJavaUtilDate(r.getDate(), 3);
                DateInterval diRikvesta = new DateInterval(r.getDate(), endDate);

                for (Appointment app : appointments) {
                    DateInterval di1 = new DateInterval(app.getDate(), app.getEndDate());

                    if (DateUtil.getInstance().overlappingInterval(di1, diRikvesta)) {
                        System.out.println("POKLAPA SE++++++++++++++++++++++++");
                        nemoze = true;
                    }
                }
                System.out.println(nemoze + "++++++++++++++++++++++++");
                if (!nemoze) {
                    Appointment appointment = new Appointment.Builder(r.getDate())
                            .withClinic(r.getClinic())
                            .withHall(h)
                            .withPatient(r.getPatient())
                            .withType(r.getAppointmentType())
                            .withPriceslist(r.getPriceslist())
                            .withEndingDate(endDate)
                            .build();
                    System.out.println(r.getDate() + "********************prvi put" + endDate);
                    appointmentService.save(appointment);
                    appointmentRequestService.delete(r);

                    done = true;
                }
                if (done) continue;
            }
        }

        //za ostale koji nisu dodeljeni
        requests = appointmentRequestService.findAllSurgeries();
        System.out.println(requests + "REQUESTS++++++++++++++");

        int i = 0;

        for (AppointmentRequest r : requests) {
            done = false;
            List<Hall> halls = hallService.findAllByClinic(r.getClinic());

            for (Hall h : halls) {
                if (done) continue;
                List<Appointment> appointments = appointmentService.findAllByHall(h);
                intervals = Scheduler.getFreeIntervalsForSurgery(appointments, r.getDate());

                for (DateInterval interval : intervals) {
                    if (done) continue;
                    Date startDate = interval.getStart();
                    Date endDate1 = Scheduler.addHoursToJavaUtilDate(interval.getStart(), 3);

                    if (DateUtil.getInstance().overlappingInterval(startDate, endDate1, startDate, interval.getEnd())) {
                        Appointment appointment = new Appointment.Builder(startDate)
                                .withClinic(r.getClinic())
                                .withHall(h)
                                .withPatient(r.getPatient())
                                .withType(r.getAppointmentType())
                                .withPriceslist(r.getPriceslist())
                                .withEndingDate(endDate1)
                                .build();
                        System.out.println(startDate + "********************drugi put" + endDate1);
                        appointmentService.save(appointment);
                        appointmentRequestService.delete(r);

                        done = true;
                    }
                }
            }

        }
    }
    
    //Test
    @PutMapping(value="/reserve")
    public ResponseEntity<Void> Reserve()
    {
    	reserveAlgorithmExamination();
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    //2_3.18
    public void reserveAlgorithmExamination()
    {
    	List<AppointmentRequest> requests = appointmentRequestService.findAll();
    		
    	for(AppointmentRequest request : requests)
    	{
    		if(request.getAppointmentType().equals(AppointmentType.Examination))
    		{
    			reserve(request, request.getDate());    			
    		}
    	}
    }
    
    public void reserve(AppointmentRequest request, Date start)
    {   	
    	int hoursDelta = 1; //Pomeraj termina ukoliko ne moze da se zakaze(1 sat)
    						//TODO: Moze ovo lepse
    	Date end = Scheduler.addHoursToJavaUtilDate(start, hoursDelta);
    	
    	Hall hall = findAvailableHall(request, start, end);
    	
    	if(hall == null)
    	{
    		start = Scheduler.addHoursToJavaUtilDate(start, hoursDelta);
    		reserve(request, start);
    		return;
    	}
    	
    	Doctor doctor = findAvailableDoctor(request, start, end);
    	
    	if(doctor == null)
    	{
    		start = Scheduler.addHoursToJavaUtilDate(start, hoursDelta);
    		reserve(request, start);
    	}
    	else
    	{
    		ArrayList<Doctor> d = new ArrayList<Doctor>();
    		d.add(doctor);
    		
    		Appointment appointment = new Appointment.Builder(start)
                    .withClinic(request.getClinic())
                    .withHall(hall)
                    .withPatient(request.getPatient())
                    .withType(request.getAppointmentType())
                    .withPriceslist(request.getPriceslist())
                    .withEndingDate(end)
                    .withDoctors(d)
                    .build();
    		
    		try {    			
    			appointmentService.save(appointment);
    			doctor.getAppointments().add(appointment);
    			userService.save(doctor);
    			appointmentRequestService.delete(request);
    		}
    		catch(Exception e){
    			logger.error("Failed saving: " + e.getMessage());
    		}
    		 		
    	}
    	
    }
    
    public Doctor findAvailableDoctor(AppointmentRequest request, Date start, Date end)
    {
    	DateUtil util = DateUtil.getInstance();
   	
    	for(Doctor d : request.getDoctors())
    	{
    		DateInterval di = new DateInterval(util.transformToDay(start, d.getShiftStart()), util.transformToDay(end, d.getShiftEnd()));
    				
    		if(d.IsFreeOn(start) //Vacations
    		   && checkAppointments(d, start, end) //Appointments
    		   && util.insideInterval(start,di)//Shift start
    		   && util.insideInterval(end, di)){//Shift end
    			
    			return d;
    		}
    	}
    	
    	
    	List<Doctor> doctors = doctorService.findAllByClinicAndType(request.getClinic(), request.getPriceslist().getTypeOfExamination());
    	
    	for(Doctor d : doctors)
    	{
    		if(d.IsFreeOn(start) && checkAppointments(d, start, end))
    		{
    			return d;
    		}
    	}
    	  	
    	return null;
    }
    
  
    public Boolean checkAppointments(Doctor d, Date start, Date end)
    {
    	List<Appointment> apps = d.getAppointments();
		
		for(Appointment app : apps)
		{
			DateInterval di1 = new DateInterval(start,end);
			DateInterval di2 = new DateInterval(app.getDate(),app.getEndDate());
			
			if(DateUtil.getInstance().overlappingInterval(di1, di2))
			{
				return false;
			}
		}
		
		return true;
    }
    
    public Hall findAvailableHall(AppointmentRequest request, Date start, Date end)
    {
    	List<Hall> halls = hallService.findAllByClinic(request.getClinic());
    	for(Hall hall : halls)
    	{
    		List<Appointment> apps = appointmentService.findAllByHall(hall);   		
    		List<DateInterval> intervals = Scheduler.getFreeIntervals(apps, start);
    		
    		if(intervals.size() == 0)
    		{
    			return hall;
    		}
    		
    		for(DateInterval di : intervals)
    		{
    			if(DateUtil.getInstance().insideInterval(start, di) && DateUtil.getInstance().insideInterval(end, di))
    			{
    				return hall;
    			}
    		}
    	}
    	
    	return null;
    }
    
}
