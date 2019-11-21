package com.group14.MedicalSystemApplication;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import helpers.SecurePasswordHasher;
import model.CentreAdmin;
import service.UserService;

@Component
public class StartupApplicationListener implements ApplicationListener<ApplicationReadyEvent>
{
	@Autowired
	private UserService userService;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		// TODO Auto-generated method stub
		try {
			CentreAdmin admin = new CentreAdmin();
			admin.setEmail("admin@centar.com");
			admin.setPassword(SecurePasswordHasher.encode("1234"));
			userService.save(admin);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
	

}
