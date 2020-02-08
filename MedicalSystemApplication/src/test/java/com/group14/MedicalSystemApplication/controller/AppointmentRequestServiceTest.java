package com.group14.MedicalSystemApplication.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import helpers.DateUtil;
import model.AppointmentRequest;
import model.Clinic;
import model.Patient;
import service.AppointmentRequestService;
import service.AppointmentService;
import service.ClinicService;
import service.HallService;
import service.PriceListService;
import service.UserService;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppointmentRequestServiceTest 
{
	@Autowired 
	private AppointmentRequestService appointmentRequestService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private HallService hallService;
	
	@Autowired
	private PriceListService priceListService;
	
	@Autowired 
	private UserService userService;
	
	
	@Test
	@Transactional
	@Rollback(true)
	void test_get_all_by_clinic()
	{
		List<AppointmentRequest> appreq = appointmentRequestService.getAllByClinic("KlinikaB");
		
		assertTrue(appreq.size() == 0);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void find_all_by_patient()
	{
		Patient patient = (Patient) userService.findByEmailAndDeleted("random.user@gmail.com", false);
		
		List<AppointmentRequest> appreq = appointmentRequestService.getAllByPatient(patient);
		
		assertTrue(appreq.size() == 0);
	}
	
	/*
	@Test
	@Transactional
	@Rollback(true)
	void test_find_appointment_request()
	{
		Patient patient = (Patient) userService.findByEmailAndDeleted("nikolamilanovic21@gmail.com", false);
		Clinic clinic = clinicService.findByName("KlinikaA");
		Date date = DateUtil.getInstance().getDate("26-02-2020 10:00", "dd-MM-yyyy HH:mm");
		
		AppointmentRequest appreq = appointmentRequestService.findAppointmentRequest(date, patient, clinic);
		
		assertTrue(appreq != null);
	}
	*/
	
	/*
	@Test
	@Transactional
	@Rollback(true)
	void test_find_appointment_request_with_strings()
	{
		AppointmentRequest appreq = appointmentRequestService.findAppointmentRequest("26-02-2020 10:00", "nikolamilanovic21@gmail.com", "KlinikaA");
		
		if(appreq == null)
		{
			assertFalse(true);
		}
		
		assertTrue(true);
	}
	*/
	

}
