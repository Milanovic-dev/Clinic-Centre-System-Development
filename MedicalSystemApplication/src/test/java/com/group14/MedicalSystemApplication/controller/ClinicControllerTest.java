package com.group14.MedicalSystemApplication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import dto.ClinicDTO;
import dto.ClinicFilterDTO;
import dto.DoctorDTO;
import dto.UserDTO;
import filters.ClinicFilter;
import filters.Filter;
import filters.FilterFactory;
import helpers.DateUtil;
import helpers.Scheduler;
import model.Clinic;
import model.Doctor;
import service.ClinicService;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClinicControllerTest{

	@LocalServerPort
	private int port;
	
	
	@Autowired
	private ClinicService clinicService;
	
	private MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
	
	@Test
	@Transactional
	@Rollback(true)
	void get_all_by_date_and_type()
	{		
		TestRestTemplate restTemplate = new TestRestTemplate();
		
		ClinicFilterDTO clinicFilter = new ClinicFilterDTO();

		ResponseEntity<ClinicDTO[]> responseEntity =
				restTemplate.postForEntity(getPath()+ "/getAll/{date}/{type}",clinicFilter, ClinicDTO[].class,"22-04-2020","Stomatoloski");
		
		ClinicDTO[] clinics = responseEntity.getBody();
		
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(clinics.length > 0);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void get_all_doctors_by_type_and_vacation()
	{
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<DoctorDTO[]> responseEntity =
				restTemplate.getForEntity(getPath()+ "/getDoctorsByTypeAndVacation/{clinicName}/{typeOfExamination}/{date}",DoctorDTO[].class,"KlinikaA","Stomatoloski","18-02-2023");
		
		DoctorDTO[] doctors = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(doctors.length > 0);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void test_get_doctor_by_filter()
	{
		TestRestTemplate restTemplate = new TestRestTemplate();
		
		UserDTO user = new UserDTO();
		user.setName("Steva");
		user.setSurname("Stevic");
		user.setEmail("doktor1@gmail.com");
		user.setAddress("Kisacka");
		user.setCity("Novi Sad");
		user.setState("Srbija");
		
		
		DoctorDTO dto = new DoctorDTO();
		dto.setType("Stomatoloski");
		dto.setClinicName("KlinikaA");
		dto.setUser(user);
		
		ResponseEntity<DoctorDTO[]> responseEntity =
				restTemplate.postForEntity(getPath()+ "/getDoctorsByFilter/{clinicName}", dto ,DoctorDTO[].class,"KlinikaA" );
		
		DoctorDTO[] doctors = responseEntity.getBody();

		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(doctors.length > 0);

		
	}

	@Test
	void get_all_doctors_by_type()
	{
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<DoctorDTO[]> responseEntity =
				restTemplate.getForEntity(getPath()+ "/getDoctorsByType/{clinicName}/{typeOfExamination}",DoctorDTO[].class,"KlinikaA","Stomatoloski");

		DoctorDTO[] doctors = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(doctors.length > 0);
	}
	
	
	
	public String getPath()
	{
		return "http://localhost:"+port+"/api/clinic";
	}

}
