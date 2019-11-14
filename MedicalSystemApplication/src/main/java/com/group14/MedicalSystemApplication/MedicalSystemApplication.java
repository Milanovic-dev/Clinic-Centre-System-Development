package com.group14.MedicalSystemApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import dto.LoginDTO;
import model.Patient;
import service.AuthService;

@EntityScan("model") 
@EnableJpaRepositories(basePackages="repository") 
@SpringBootApplication(scanBasePackages = {"model","service","repository","controller"})
public class MedicalSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalSystemApplication.class, args);	
	
	}

}
