package controller;

import helpers.DateInterval;
import helpers.DateUtil;
import helpers.Scheduler;
import model.*;
import model.Appointment.AppointmentType;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import service.*;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/scheduled")
@EnableScheduling
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

        try {       	
        	RestTemplate rest = new RestTemplate();
        	rest.put("http://localhost:8282/api/scheduled/reserve", null);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        logger.info("Automatic scheduling ended.");
    }


    
    //Test
    @PutMapping(value="/reserve")
    public ResponseEntity<Void> Reserve()
    {
    	reserveAlgorithmExamination();
        reserveAlgorithmSurgery();
    	return new ResponseEntity<>(HttpStatus.OK);
    }

    //2_3.18
    @Transactional
    public void reserveAlgorithmExamination()
    {
    	List<AppointmentRequest> requests = appointmentRequestService.findAll();

    	for(AppointmentRequest request : requests)
    	{
    		if(request.getAppointmentType().equals(AppointmentType.Examination))
    		{
    			List<Doctor> doctors = request.getDoctors();
    			reserve(request, request.getDate());    			
    		}
    	}
    }


    //2_3.20
    @Transactional
    public void reserveAlgorithmSurgery()
    {
        List<AppointmentRequest> requests = appointmentRequestService.findAllSurgeries();

        for(AppointmentRequest request : requests)
        {
                List<Doctor> doctors = request.getDoctors();
                reserve(request, request.getDate());
        }
    }

    
    @Transactional
    public void reserve(AppointmentRequest request, Date start)
    {
        int hoursDelta = 1;

        if(request.getAppointmentType().equals(AppointmentType.Examination))
        {
            hoursDelta = 1;
        }
        else if (request.getAppointmentType().equals(AppointmentType.Surgery))
        {
            hoursDelta = 3;
        }
    	 //Pomeraj termina ukoliko ne moze da se zakaze(1 sat)
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
    @Transactional
    public Doctor findAvailableDoctor(AppointmentRequest request, Date start, Date end)
    {
    	DateUtil util = DateUtil.getInstance();

    	Hibernate.initialize(request.getDoctors());
    	List<Doctor> doctors = request.getDoctors();
    	
    	for(Doctor d : doctors)
    	{
    		DateInterval di = new DateInterval(util.transformToDay(start, d.getShiftStart()), util.transformToDay(end, d.getShiftEnd()));

    		if(d.IsFreeOn(start) //Vacations
    		   && checkAppointments(d, start, end) //Appointments
    		   && util.insideInterval(start,di)//Shift start
    		   && util.insideInterval(end, di)){//Shift end

    			return d;
    		}
    	}


    	doctors = doctorService.findAllByClinicAndType(request.getClinic(), request.getPriceslist().getTypeOfExamination());

    	for(Doctor d : doctors)
    	{
    		if(d.IsFreeOn(start) && checkAppointments(d, start, end))
    		{
    			return d;
    		}
    	}

    	return null;
    }

    @Transactional
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
    @Transactional
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
