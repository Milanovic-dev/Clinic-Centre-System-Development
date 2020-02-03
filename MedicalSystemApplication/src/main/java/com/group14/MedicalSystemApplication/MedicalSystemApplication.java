package com.group14.MedicalSystemApplication;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import dto.LoginDTO;
import helpers.SecurePasswordHasher;
import model.CentreAdmin;
import model.Patient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import service.AuthService;
import service.UserService;

@EntityScan("model") 
@EnableJpaRepositories(basePackages="repository") 
@SpringBootApplication(scanBasePackages = {"model","service","repository","controller"})
public class MedicalSystemApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(MedicalSystemApplication.class, args);			
	}

}
