package com.group14.MedicalSystemApplication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import dto.HallDTO;
import helpers.DateUtil;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HallControllerTest 
{
	@LocalServerPort
	private int port;
	
	/*
	@Test
	@Transactional
	@Rollback(true)
	void test_get_all_by_filter()
	{
		HallDTO dto = new HallDTO();
		dto.setClinicName("KlinikaA");
		dto.setNumber(1);
		dto.setName("Druga sala");
		dto.setDate("23-02-2020");
		
		
		TestRestTemplate rest = new TestRestTemplate();
		ResponseEntity<HallDTO[]> response = rest.postForEntity(getPath() + "/getAllByFilter", dto, HallDTO[].class);

		HallDTO[] halls = response.getBody();
		
		assertEquals(HttpStatus.OK,response.getStatusCode());
		assertTrue(halls.length > 0 );
		
	}
	*/
	
	public String getPath()
	{
		return "http://localhost:"+port+"/api/clinic";
	}
}
