package com.group14.MedicalSystemApplication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import helpers.SecurePasswordHasher;
import model.RegistrationRequest;
import model.User;
import service.AuthService;
import service.UserService;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthControllerTest {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserService userService;
	
	@Test
	@Transactional
	@Rollback(true)
	void when_register_request_Return_Equals_request_phone() throws NoSuchAlgorithmException
	{
		RegistrationRequest request = new RegistrationRequest();
		request.setEmail("nikolamilanovic@gmail.com");
		request.setAddress("Karadjordjeva 8");
		request.setCity("Novi Sad");
		request.setState("Srbija");
		request.setInsuranceId("123422432423");
		request.setName("Nikola");
		request.setSurname("Milanovic");
		request.setPhone("06549643");
		request.setPassword(SecurePasswordHasher.getInstance().encode("123"));
		
		RegistrationRequest req = authService.
				findByEmail(request.getEmail());
		
		User u = userService.findByEmailAndDeleted(request.getEmail(),false);
			
		assertFalse(req != null || u != null);
		
		authService.save(new RegistrationRequest(request));
		
		RegistrationRequest requestTest = authService.findByEmail(request.getEmail());
		
		assertEquals(requestTest.getPhone(), request.getPhone());
	}
	
}
