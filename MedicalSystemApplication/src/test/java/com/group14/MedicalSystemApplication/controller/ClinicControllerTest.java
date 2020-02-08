package com.group14.MedicalSystemApplication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
import filters.ClinicFilter;
import filters.Filter;
import filters.FilterFactory;
import helpers.DateUtil;
import helpers.Scheduler;
import model.Clinic;
import model.Doctor;
import service.ClinicService;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ClinicControllerTest {

	private String URI_PREFIX = "http://localhost:8282/api/clinic";
	
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
		RestTemplate restTemplate = new RestTemplate();
		
		ClinicFilterDTO clinicFilter = new ClinicFilterDTO();

		
		ResponseEntity<ClinicDTO[]> responseEntity =
				restTemplate.postForEntity(URI_PREFIX + "/getAll/{date}/{type}",clinicFilter, ClinicDTO[].class,"22-04-2020","Stomatoloski");
		
		ClinicDTO[] clinics = responseEntity.getBody();
		
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(clinics.length > 0);
	}
	
}
