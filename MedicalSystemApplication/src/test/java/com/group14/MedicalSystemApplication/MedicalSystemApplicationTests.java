//package com.group14.MedicalSystemApplication;
//
//import static org.junit.Assert.*;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import controller.AuthController;
//import model.User;
//import service.UserService;
//
//
//@SpringBootTest
//class MedicalSystemApplicationTests {
//
//	@Autowired
//	private UserService service;
//
//	@Test
//	void contextLoads() {
//
//	}
//
//	@Test
//	public void whenFindByEmail_thenReturnUser()
//	  throws Exception {
//
//	    User user = new User();
//	    user.setEmail("nikola@gmail.com");
//
//	    User found = service.findByEmail(user.getEmail());
//
//	    assertTrue(found != null);
//	    assertTrue(found.getEmail().equals(user.getEmail()));
//	}
//
//}
