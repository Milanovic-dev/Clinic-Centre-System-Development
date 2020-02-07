package com.group14.MedicalSystemApplication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import dto.DoctorDTO;
import dto.UserDTO;
import helpers.SecurePasswordHasher;
import model.Clinic;
import model.Doctor;
import service.ClinicService;
import service.UserService;

@SpringBootTest
public class DoctorControllerTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ClinicService clinicService;
	
	@Test
	@Transactional
	@Rollback(true)
	void make_new_doctor()
	{
		UserDTO udto = new UserDTO();
		udto.setAddress("Kisacka 11");
		udto.setCity("Novi Sad");
		udto.setEmail("doktor5@gmail.com");
		
		udto.setAddress("Karadjordjeva 8");
		udto.setCity("Novi Sad");
		udto.setState("Srbija");
		udto.setInsuranceId("123422432423");
		udto.setName("Nikola");
		udto.setSurname("Milanovic");
		udto.setPhone("06549643");
		
		
		DoctorDTO dto = new DoctorDTO();
		dto.setClinicName("KlinikaA");
		dto.setUser(udto);
		dto.setShiftStart("14:00");
		dto.setShiftEnd("20:00");
		
		Doctor d = (Doctor) userService.findByEmailAndDeleted(dto.getUser().getEmail(),false);
	
		Clinic c = clinicService.findByName(dto.getClinicName());
		
		assertFalse(d != null);
		assertFalse(c == null);
		
		String pass = "";
		try {
			pass = SecurePasswordHasher.getInstance().encode("123");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		Doctor doctor = new Doctor(dto);
		doctor.setPassword(pass);
		doctor.setClinic(c);
		userService.save(doctor);
		
		c.getDoctors().add(doctor);
		clinicService.save(c);
		
		Doctor doc = (Doctor) userService.findByEmailAndDeleted(doctor.getEmail(), false);
		assertEquals(doc.getEmail(),doctor.getEmail());
	}
	
}
