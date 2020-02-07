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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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

	private String URI_PREFIX = "http://localhost:8282/api/appointments";
	
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
	void find_all_predefined_appointments()
	{
		RestTemplate rest = new RestTemplate();
		
		ResponseEntity<AppointmentDTO[]> response = 
									rest.getForEntity(URI_PREFIX + "/getAllPredefined", AppointmentDTO[].class);
		
		AppointmentDTO[] apps = response.getBody();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(apps.length > 0);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void reserve_predefined_examination()
	{		
		AppointmentDTO dto = new AppointmentDTO();
		dto.setClinicName("KlinikaA");
		dto.setDate("21-01-2020 07:30");
		dto.setEndDate("21-01-2020 09:00");
		dto.setDoctors(new ArrayList<String>() {{add("doktor1@gmail.com");}});
		dto.setHallNumber(1);
		dto.setType(AppointmentType.Examination);
		dto.setTypeOfExamination("Opsti pregled");
	}
	
	
}
