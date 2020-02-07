package com.group14.MedicalSystemApplication.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import dto.AppointmentDTO;
import helpers.DateInterval;
import helpers.DateUtil;
import model.Appointment;
import model.Clinic;
import model.Doctor;
import model.Hall;
import model.Patient;
import model.Priceslist;
import model.Appointment.AppointmentType;
import service.AppointmentRequestService;
import service.AppointmentService;
import service.AuthService;
import service.ClinicService;
import service.HallService;
import service.NotificationService;
import service.PriceListService;
import service.UserService;
import service.VacationRequestService;

@SpringBootTest
public class AppointmentControllerTest {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AppointmentService appointmentService;
				
	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private HallService hallService;
	
	@Autowired
	private PriceListService priceslistService;
	
	
	@Test
	void whenSelectByDoctor_ReturnAppointments()
	{
		Doctor d = (Doctor)userService.findByEmailAndDeleted("doktor1@gmail.com",false);
		
		List<Appointment> list = appointmentService.findAllByDoctor(d.getId());

		assertNotEquals(list, null);	
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	void when_make_predefined_Return_isPredefined()
	{
		AppointmentDTO dto = new AppointmentDTO();
		dto.setClinicName("KlinikaA");
		dto.setDate("22-02-2020 10:00");
		dto.setEndDate("22-02-2020 12:00");
		dto.setDoctors(new ArrayList<String>() {{add("doktor1@gmail.com");}});
		dto.setHallNumber(1);
		dto.setType(AppointmentType.Examination);
		dto.setTypeOfExamination("Opsti pregled");
		
		Clinic clinic = clinicService.findByName(dto.getClinicName());
		
		assertFalse(clinic == null);
		
		
		Date date = DateUtil.getInstance().getDate(dto.getDate(), "dd-MM-yyyy HH:mm");
		Date endDate = DateUtil.getInstance().getDate(dto.getEndDate(), "dd-MM-yyyy HH:mm");
		
		
		Hall hall = hallService.findByNumber(dto.getHallNumber());
		
		assertFalse(hall == null);
		
		List<Appointment> appointments = appointmentService.findAllByHallAndClinic(hall, clinic);
		
		
		for(Appointment app : appointments)
		{
			DateInterval d1 = new DateInterval(app.getDate(),app.getEndDate());
			DateInterval d2 = new DateInterval(date, endDate);
			assertFalse(DateUtil.getInstance().overlappingInterval(d1, d2));
		}
		
		
		Priceslist p = priceslistService.findByTypeOfExamination(dto.getTypeOfExamination());
		
		assertFalse(p == null);
		
		ArrayList<Doctor> doctors = new ArrayList<Doctor>();
		
		for(String email : dto.getDoctors())
		{
			Doctor d = (Doctor) userService.findByEmailAndDeleted(email, false);
			Hibernate.initialize(d.getAppointments());
			for(Appointment app : d.getAppointments())
			{
				DateInterval d1 = new DateInterval(app.getDate(),app.getEndDate());
				DateInterval d2 = new DateInterval(date, endDate);
				
				assertFalse(DateUtil.getInstance().overlappingInterval(d1, d2));
			}
			
			doctors.add(d);
		}
		
		
		
		Appointment a = appointmentService.findAppointment(date, hall, clinic);
		
		assertFalse(a != null);
		
		Appointment app = new Appointment.Builder(date)
				.withEndingDate(endDate)
				.withClinic(clinic)
				.withHall(hall)
				.withType(dto.getType())
				.withPriceslist(p)
				.withDoctors(doctors)				
				.build();
		
		app.setPredefined(true);		
		appointmentService.save(app);
		
		for(Doctor d : doctors)
		{
			d.getAppointments().add(app);
			userService.save(d);
		}
		
		Appointment appTest = appointmentService.findAppointment(app.getDate(), app.getHall(), app.getClinic());
		
		assertTrue(appTest.getPredefined());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void find_all_predefined_appointments()
	{
		List<Appointment> appointments = appointmentService.findAllByPredefined();
		
		assertNotEquals(appointments.size(),0);
		
		List<AppointmentDTO> dtos = new ArrayList<AppointmentDTO>();
		
		for(Appointment app : appointments)
		{
			if(app.getPatient() == null)
			{
				dtos.add(new AppointmentDTO(app));						
			}
		}
		
		assertNotEquals(dtos.size(),0);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void reserve_predefined_examination()
	{
		HttpHeaders headers = new HttpHeaders();
		
		Patient p = (Patient) userService.findByEmailAndDeleted("nikolamilanovic21@gmail.com", false);
		
		assertFalse(p == null);
		
		AppointmentDTO dto = new AppointmentDTO();
		dto.setClinicName("KlinikaA");
		dto.setDate("21-01-2020 07:30");
		dto.setEndDate("21-01-2020 09:00");
		dto.setDoctors(new ArrayList<String>() {{add("doktor1@gmail.com");}});
		dto.setHallNumber(1);
		dto.setType(AppointmentType.Examination);
		dto.setTypeOfExamination("Opsti pregled");
		
		Appointment app = appointmentService.findAppointment(dto.getDate(), dto.getHallNumber(), dto.getClinicName());
		
		assertFalse(app == null);
		
		List<Appointment> appointments = appointmentService.findAllByPatient(p);
		
		for(Appointment appointment : appointments)
		{
			DateInterval interval1 = new DateInterval(appointment.getDate(), appointment.getEndDate());
			DateInterval interval2 = new DateInterval(app.getDate(), app.getEndDate());
			
			assertFalse(DateUtil.getInstance().overlappingInterval(interval1, interval2));
		}
		
	
		app.setPatient(p);
			
		try
		{
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("Uspesno ste zakazali pregled(");
			strBuilder.append(app.getPriceslist().getTypeOfExamination());
			strBuilder.append(") za datum ");
			strBuilder.append(app.getDate());
			strBuilder.append(" na klinici ");
			strBuilder.append(app.getClinic().getName());
			strBuilder.append(" u sali Br. ");
			strBuilder.append(app.getHall().getNumber());
			strBuilder.append(". Vas doktor je ");
			strBuilder.append(app.getDoctors().get(0).getName() + " " + app.getDoctors().get(0).getSurname());
			strBuilder.append(". Cena pregleda je ");
			strBuilder.append(app.getPriceslist().getPrice());
			strBuilder.append("rsd.");
			appointmentService.save(app);			
		}
		catch(ObjectOptimisticLockingFailureException e)
		{
			fail();
		}
		
		assertEquals(appointmentService.findAppointment(app.getDate(), app.getHall(), app.getClinic()),app);
	}
	
	
}
