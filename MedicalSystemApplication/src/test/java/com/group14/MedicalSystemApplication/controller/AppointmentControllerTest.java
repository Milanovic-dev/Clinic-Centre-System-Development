package com.group14.MedicalSystemApplication.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.group14.MedicalSystemApplication.TestConstants;

import dto.AppointmentDTO;
import model.Appointment;
import model.Appointment.AppointmentType;
import model.Doctor;
import service.AppointmentService;
import service.ClinicService;
import service.HallService;
import service.PriceListService;
import service.UserService;

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
		assertEquals(TestConstants.FOUND_PRE_CLINIC, apps[0].getClinicName());
		assertEquals(TestConstants.FOUND_PRE_HALL, apps[0].getHallNumber());
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
