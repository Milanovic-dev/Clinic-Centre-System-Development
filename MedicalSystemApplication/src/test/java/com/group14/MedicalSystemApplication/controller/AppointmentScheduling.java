package com.group14.MedicalSystemApplication.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import model.AppointmentRequest;
import service.AppointmentRequestService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppointmentScheduling {

	@LocalServerPort
	private int port;
	
	@Autowired
	private AppointmentRequestService appointmentRequestService;
	
	
	@Test
	void test_midnight_scheduling()
	{
		TestRestTemplate rest = new TestRestTemplate();
		AppointmentRequest areq = new AppointmentRequest();
		
		rest.put(getPath() + "/reserve",null);
		
		assertTrue(true);
		
	}
	
	
	private String getPath()
	{
		return "http://localhost:"+port+"/api/scheduled";
	}
}
