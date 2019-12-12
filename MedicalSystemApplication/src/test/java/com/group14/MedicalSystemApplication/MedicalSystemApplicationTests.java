package com.group14.MedicalSystemApplication;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import junit.framework.Assert;
import model.Appointment;
import model.Doctor;
import service.AppointmentService;
import service.UserService;


@SpringBootTest
class MedicalSystemApplicationTests {
	
	@Autowired
	private AppointmentService service;
	
	@Autowired
	private UserService userService;
	@Test
	void contextLoads() {
		
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void whenSelectByDoctor_ReturnAppointments()
	{
		Doctor d = (Doctor)userService.findByEmail("doktor1@gmail.com");
		
		List<Appointment> list = service.findAllByDoctor(d.getId());
		
		Assert.assertEquals(2, list.size());
	}
}
