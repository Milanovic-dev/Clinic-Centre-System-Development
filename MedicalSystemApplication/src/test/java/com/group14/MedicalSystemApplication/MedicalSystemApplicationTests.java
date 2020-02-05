package com.group14.MedicalSystemApplication;
import static com.sun.tools.javac.util.Assert.check;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import dto.AppointmentDTO;
import helpers.DateInterval;
import helpers.DateUtil;
import helpers.SecurePasswordHasher;
import model.Appointment;
import model.Clinic;
import model.Doctor;
import model.Hall;
import model.Priceslist;
import model.RegistrationRequest;
import model.User;
import model.Appointment.AppointmentType;
import service.AppointmentRequestService;
import service.AppointmentService;
import service.AuthService;
import service.ClinicService;
import service.HallService;
import service.NotificationService;
import service.PriceListService;
import service.UserService;


@SpringBootTest
class MedicalSystemApplicationTests {
	
	@Autowired
	private AppointmentService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AppointmentService appointmentService;
		
	@Autowired
	private AppointmentRequestService appointmentRequestService;
		
	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private HallService hallService;
	
	@Autowired
	private PriceListService priceslistService;
	
	@Autowired
	private AuthService authService;
	
	@Test
	void contextLoads() {
		
		
	}
	
	@Test
	void whenSelectByDoctor_ReturnAppointments()
	{
		Doctor d = (Doctor)userService.findByEmailAndDeleted("doktor1@gmail.com",false);
		
		List<Appointment> list = service.findAllByDoctor(d.getId());

		assertNotEquals(list, null);	
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
	@Transactional
	@Rollback(true)
	void when_make_predefined_Return_isPredefined()
	{
		AppointmentDTO dto = new AppointmentDTO();
		dto.setClinicName("KlinikaA");
		dto.setDate("22-02-2020 10:00");
		dto.setEndDate("22-02-2020 12:00");
		dto.setDoctors(new ArrayList<String>() {{add("doktor1@gmail.com");}});
		dto.setHallNumber(1);
		dto.setType(AppointmentType.Examination);
		dto.setTypeOfExamination("Opsti pregled");
		
		Clinic clinic = clinicService.findByName(dto.getClinicName());
		
		assertFalse(clinic == null);
		
		
		Date date = DateUtil.getInstance().getDate(dto.getDate(), "dd-MM-yyyy HH:mm");
		Date endDate = DateUtil.getInstance().getDate(dto.getEndDate(), "dd-MM-yyyy HH:mm");
		
		
		Hall hall = hallService.findByNumber(dto.getHallNumber());
		
		assertFalse(hall == null);
		
		List<Appointment> appointments = appointmentService.findAllByHallAndClinic(hall, clinic);
		
		
		for(Appointment app : appointments)
		{
			DateInterval d1 = new DateInterval(app.getDate(),app.getEndDate());
			DateInterval d2 = new DateInterval(date, endDate);
			assertFalse(DateUtil.getInstance().overlappingInterval(d1, d2));
		}
		
		
		Priceslist p = priceslistService.findByTypeOfExamination(dto.getTypeOfExamination());
		
		assertFalse(p == null);
		
		ArrayList<Doctor> doctors = new ArrayList<Doctor>();
		
		for(String email : dto.getDoctors())
		{
			Doctor d = (Doctor) userService.findByEmailAndDeleted(email, false);
			Hibernate.initialize(d.getAppointments());
			for(Appointment app : d.getAppointments())
			{
				DateInterval d1 = new DateInterval(app.getDate(),app.getEndDate());
				DateInterval d2 = new DateInterval(date, endDate);
				
				assertFalse(DateUtil.getInstance().overlappingInterval(d1, d2));
			}
			
			doctors.add(d);
		}
		
		
		
		Appointment a = appointmentService.findAppointment(date, hall, clinic);
		
		assertFalse(a != null);
		
		Appointment app = new Appointment.Builder(date)
				.withEndingDate(endDate)
				.withClinic(clinic)
				.withHall(hall)
				.withType(dto.getType())
				.withPriceslist(p)
				.withDoctors(doctors)				
				.build();
		
		app.setPredefined(true);		
		appointmentService.save(app);
		
		for(Doctor d : doctors)
		{
			d.getAppointments().add(app);
			userService.save(d);
		}
		
		Appointment appTest = appointmentService.findAppointment(app.getDate(), app.getHall(), app.getClinic());
		
		assertTrue(appTest.getPredefined());
	}
	
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
