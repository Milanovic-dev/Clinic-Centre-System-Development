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
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
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
import model.AppointmentRequest;
import service.AppointmentRequestService;
import service.AppointmentService;
import service.AuthService;
import service.ClinicService;
import service.HallService;
import service.NotificationService;
import service.PriceListService;
import service.UserService;
import service.VacationRequestService;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppointmentControllerTest {
	
	@LocalServerPort
	private int port;
	
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
	
	@Autowired
	private AppointmentRequestService appointmentRequestService;
	
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
		TestRestTemplate rest = new TestRestTemplate();
		
		
		ResponseEntity<AppointmentDTO[]> response = 
									rest.getForEntity(getPath() + "/getAllPredefined", AppointmentDTO[].class);
		
		AppointmentDTO[] apps = response.getBody();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(apps.length > 0);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void send_request_for_appointment()
	{
		AppointmentDTO dto = new AppointmentDTO();
		dto.setClinicName("KlinikaA");
		dto.setDate("21-01-2020 07:30");
		dto.setEndDate("21-01-2020 09:00");
		dto.setDoctors(new ArrayList<String>() {{add("doktor1@gmail.com");}});
		dto.setHallNumber(1);
		dto.setType(AppointmentType.Examination);
		dto.setTypeOfExamination("Opsti pregled");
		dto.setPatientEmail("nikolamilanovic21@gmail.com");
		
		TestRestTemplate rest = new TestRestTemplate();
		ResponseEntity<Void> response = rest.postForEntity(getPath() + "/sendRequest", dto, Void.class);
		
		assertEquals(HttpStatus.CREATED,response.getStatusCode());
	}

	@Test
	@Transactional
	@Rollback(true)
	void test_confirm_request()
	{
		AppointmentDTO dto = new AppointmentDTO();
		dto.setClinicName("KlinikaA");
		dto.setDate("24-02-2020 11:00");
		dto.setEndDate("24-02-2020 12:00");
		dto.setDoctors(new ArrayList<String>() {{add("doktor1@gmail.com");}});
		dto.setHallNumber(1);
		dto.setType(AppointmentType.Examination);
		dto.setTypeOfExamination("Opsti pregled");
		dto.setPatientEmail("nikolamilanovic21@gmail.com");
		dto.setNewDate("undefined 11:00");
		dto.setNewEndDate("undefined 12:52");
		
		AppointmentRequest request = appointmentRequestService.findAppointmentRequest(dto.getDate(), dto.getPatientEmail(), dto.getClinicName());
		appointmentRequestService.save(request);
		
		TestRestTemplate rest = new TestRestTemplate();
		ResponseEntity<Void> response = rest.postForEntity(getPath() + "/confirmRequest", dto, Void.class);
		
		assertEquals(HttpStatus.OK,response.getStatusCode());

	}
	
	
	
	@Test
	@Transactional
	@Rollback(true)
	void test_deny_appointment()
	{
		AppointmentDTO dto = new AppointmentDTO();
		dto.setClinicName("KlinikaA");
		dto.setDate("25-01-2020 07:30");
		dto.setHallNumber(1);
		
		TestRestTemplate rest = new TestRestTemplate();
		
		rest.delete(getPath() + "/denyAppointment", dto);
		
		assertTrue(appointmentRequestService.findAppointmentRequest(dto.getDate(), dto.getPatientEmail(), dto.getClinicName()) == null);

	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	void test_confirm_appointment()
	{
		AppointmentDTO dto = new AppointmentDTO();
		dto.setClinicName("KlinikaA");
		dto.setDate("21-01-2020 07:30");
		dto.setEndDate("21-01-2020 09:00");
		dto.setDoctors(new ArrayList<String>() {{add("doktor1@gmail.com");}});
		dto.setHallNumber(1);
		dto.setType(AppointmentType.Examination);
		dto.setTypeOfExamination("Opsti pregled");
		dto.setPatientEmail("nikolamilanovic21@gmail.com");
		
		TestRestTemplate rest = new TestRestTemplate();

		rest.put(getPath() + "/confirmAppointment", dto);
		
		assertFalse(appointmentService.findAppointment(dto.getDate(), dto.getHallNumber(), dto.getClinicName()) != null);
	
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void test_get_all_appointment_request_by_clinic()
	{
		TestRestTemplate rest = new TestRestTemplate();
		ResponseEntity<AppointmentDTO[]> response = rest.getForEntity(getPath() + "/clinic/getAllRequests/{clinicName}", AppointmentDTO[].class, "KlinikaA");
		
		AppointmentDTO[] apps = response.getBody();
		
		assertEquals(response.getStatusCode(),HttpStatus.OK);
		assertTrue(apps.length > 0);
	}
	
	
	private String getPath()
	{
		return "http://localhost:"+port+"/api/appointments";
	}
	
}
