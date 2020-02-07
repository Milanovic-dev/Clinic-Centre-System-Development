package controller;

import helpers.DateInterval;
import helpers.DateUtil;
import helpers.Scheduler;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.*;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "api/scheduled")
public class ScheduledAppointmentsController {

    @Autowired
    AppointmentRequestService appointmentRequestService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    HallService hallService;

    @Scheduled(cron = "0 0 0 * * *")
    public void onSchedule() {
        doWork();
    }

    public void doWork() {

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
        requests = appointmentRequestService.findAll();
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
}
