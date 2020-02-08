package com.group14.MedicalSystemApplication;
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
import org.springframework.mail.MailException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import dto.AppointmentDTO;
import dto.ClinicDTO;
import dto.ClinicFilterDTO;
import dto.DoctorDTO;
import dto.HallDTO;
import dto.UserDTO;
import dto.VacationDTO;
import filters.Filter;
import filters.FilterFactory;
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
import model.Vacation;
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
	
	@Autowired 
	private NotificationService notificationService;
	
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
	
	@Test
	void test_if_doctor_is_free()
	{
		Doctor doctor = new Doctor();
		
		Vacation vacation = new Vacation();
		vacation.setUser(doctor);
		vacation.setStartDate(DateUtil.getInstance().getDate("29-02-2020","dd-MM-yyyy"));
		vacation.setEndDate(DateUtil.getInstance().getDate("15-03-2020","dd-MM-yyyy"));
		
		List<Vacation> vacations = new ArrayList<Vacation>();
		vacations.add(vacation);
		
		doctor.setVacations(vacations);
		
		assertFalse(doctor.IsFreeOn(DateUtil.getInstance().getDate("08-03-2020", "dd-MM-yyyy")));
		
	}
	
	@Test
	void test_if_notification_is_sent()
	{
		Patient patient = new Patient();
		patient.setEmail("nikola@gmail.com");
		
		try {
			notificationService.sendNotification(patient.getEmail(),"Test","Testiranje slanja mail notifikacije");
		}
		catch(MailException e)
		{
			assertTrue(false);
		}
		
	}
	
	@Test
	void test_if_clinic_filter_works()
	{
		Clinic clinic1 = new Clinic();
		clinic1.setName("KlinikaA");
		clinic1.setAddress("Karadjordjeva1");
		clinic1.setDescription("Nova klinika");
		clinic1.setState("Srbija");
		clinic1.setCity("Kragujevac");
		
		ClinicFilterDTO clinic2 = new ClinicFilterDTO();
		clinic2.setName("KlinikaB");
		clinic2.setAddress("Karadjordjeva 10");
		clinic2.setState("Srbija");
		clinic2.setCity("Kragujevac");
		
		Filter filter = FilterFactory.getInstance().get("clinic");
		assertFalse(filter.test(clinic1, clinic2));
	}
	
	@Test 
	void test_if_hall_filter_works()
	{
		Clinic clinic1 = new Clinic();
		clinic1.setName("KlinikaA");
		clinic1.setAddress("Karadjordjeva1");
		clinic1.setDescription("Nova klinika");
		clinic1.setState("Srbija");
		clinic1.setCity("Kragujevac");
	
		Hall hall1 = new Hall();
		hall1.setClinic(clinic1);
		hall1.setDeleted(false);
		hall1.setName("Prva sala");
		hall1.setNumber(1);
		
		HallDTO hall2 = new HallDTO();
		hall2.setClinicName(clinic1.getName());
		hall2.setName("Druga sala");
		hall2.setNumber(2);
		
		Filter filter = FilterFactory.getInstance().get("hall");

		assertFalse(filter.test(hall1, hall2));
	}
	
	@Test
	void test_if_same_day()
	{
		Date date1 = DateUtil.getInstance().getDate("22-02-2020 14:00", "dd-MM-yyyy HH:mm");
		Date date2 = DateUtil.getInstance().getDate("23-02-2020 15:00", "dd-MM-yyyy HH:mm");
		
		assertFalse(DateUtil.getInstance().isSameDay(date1, date2));
	}
	
	@Test 
	void test_get_time_between()
	{
		Date date1 = DateUtil.getInstance().getDate("22-02-2020 14:00", "dd-MM-yyyy HH:mm");
		Date date2 = DateUtil.getInstance().getDate("22-02-2020 14:00", "dd-MM-yyyy HH:mm");
		
		assertTrue(DateUtil.getInstance().getTimeBetween(date1, date2) == 0);
	}
	
	
	}
