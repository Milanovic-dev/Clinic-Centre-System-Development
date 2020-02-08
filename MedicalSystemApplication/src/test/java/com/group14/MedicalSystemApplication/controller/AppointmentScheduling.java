package com.group14.MedicalSystemApplication.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppointmentScheduling {

	@LocalServerPort
	private int port;
	
	
	@Test
	void test_midnight_scheduling()
	{
		
	}
	
	
	private String getPath()
	{
		return "http://localhost:"+port+"/api/scheduled";
	}
}
