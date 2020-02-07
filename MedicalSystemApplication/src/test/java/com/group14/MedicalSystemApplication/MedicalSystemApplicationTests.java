package com.group14.MedicalSystemApplication;
import static com.sun.tools.javac.util.Assert.check;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import dto.AppointmentDTO;
import dto.DoctorDTO;
import dto.UserDTO;
import dto.VacationDTO;
import helpers.DateInterval;
import helpers.DateUtil;
import helpers.SecurePasswordHasher;
import model.Appointment;
import model.Clinic;
import model.Doctor;
import model.Hall;
import model.Nurse;
import model.Patient;
import model.Priceslist;
import model.RegistrationRequest;
import model.User;
import model.VacationRequest;
import model.Appointment.AppointmentType;
import service.AppointmentRequestService;
import service.AppointmentService;
import service.AuthService;
import service.ClinicService;
import service.HallService;
import service.NotificationService;
import service.PriceListService;
import service.UserService;
import service.VacationRequestService;


@SpringBootTest
class MedicalSystemApplicationTests {
	
	@Test
	void contextLoads() {
		
		
	}
	
		
	@Test
	void check_overlapDates()
	{
		Date date1 = DateUtil.getInstance().getDate("22-02-2020 14:00", "dd-MM-yyyy HH:mm");
		Date date2 = DateUtil.getInstance().getDate("23-02-2020 15:00", "dd-MM-yyyy HH:mm");
		Date date3 = DateUtil.getInstance().getDate("23-02-2020 14:50", "dd-MM-yyyy HH:mm");
		Date date4 = DateUtil.getInstance().getDate("24-02-2020 17:00", "dd-MM-yyyy HH:mm");
		
		DateInterval di1 = new DateInterval(date1, date2);
		DateInterval di2 = new DateInterval(date3, date4);
		
		assertTrue(DateUtil.getInstance().overlappingInterval(di1, di2));
	}
	
	@Test
	void check_overlapHours()
	{
		Date date1 = DateUtil.getInstance().getDate("14:00", "HH:mm");
		Date date2 = DateUtil.getInstance().getDate("15:00", "HH:mm");
		Date date3 = DateUtil.getInstance().getDate("14:50", "HH:mm");
		Date date4 = DateUtil.getInstance().getDate("17:00", "HH:mm");
		
		DateInterval di1 = new DateInterval(date1, date2);
		DateInterval di2 = new DateInterval(date3, date4);
		
		assertTrue(DateUtil.getInstance().overlappingInterval(di1, di2));
	}
			
}
