package com.group14.MedicalSystemApplication;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import dto.LoginDTO;
import helpers.DateUtil;
import helpers.InvokeControl;
import helpers.InvokeFunction;
import helpers.SecurePasswordHasher;
import model.CentreAdmin;
import model.Patient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import service.AppointmentRequestService;
import service.AuthService;
import service.UserService;
import java.util.*;

@EntityScan("model") 
@EnableJpaRepositories(basePackages="repository") 

@SpringBootApplication(scanBasePackages = {"model","service","repository","controller"})
@EnableScheduling
public class MedicalSystemApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(MedicalSystemApplication.class, args);		
	}

}
