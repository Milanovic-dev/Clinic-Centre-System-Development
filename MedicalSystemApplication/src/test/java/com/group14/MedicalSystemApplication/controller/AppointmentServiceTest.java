package com.group14.MedicalSystemApplication.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.glassfish.jersey.jaxb.internal.XmlCollectionJaxbProvider.App;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import dto.AppointmentDTO;
import helpers.DateUtil;
import model.Appointment;
import model.Clinic;
import model.Doctor;
import model.Hall;
import model.Patient;
import model.Priceslist;
import model.Appointment.AppointmentType;
import service.AppointmentService;
import service.ClinicService;
import service.HallService;
import service.PriceListService;
import service.UserService;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppointmentServiceTest 
{
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private HallService hallService;
	
	@Autowired
	private PriceListService priceListService;
	
	@Autowired 
	private UserService userService;
	
	@Test
	@Transactional
	@Rollback(true)
	void find_appointment()
	{
		Clinic clinic = clinicService.findByName("KlinikaA");
		Hall hall = hallService.findByNumberAndClinic(1, clinic);
		Appointment app = appointmentService.findAppointment(DateUtil.getInstance().getDate("21-03-2020 07:30","dd-MM-yyyy HH:mm"), hall, clinic);
		
		assertNotNull(app);
	}
	
	@Test 
	@Transactional
	@Rollback(true)
	void test_find_all_by_hall_and_clinic()
	{
		Clinic clinic = clinicService.findByName("KlinikaA");
		Hall hall = hallService.findByNumberAndClinic(1, clinic);
		List<Appointment> app = appointmentService.findAllByHallAndClinic(hall, clinic);
		
		assertTrue(app.size() !=0);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void test_find_all()
	{
		List<Appointment> apps = appointmentService.findAll();
		assertFalse(apps.size() == 0);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void test_find_all_predefined()
	{
		List<Appointment> app = appointmentService.findAllByPredefined();
		assertTrue(app.size() != 0);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void test_find_all_by_pricelist()
	{
		Priceslist pl = priceListService.findByTypeOfExaminationAndClinic("Opsti pregled", "KlinikaA");
		List<Appointment> app = appointmentService.findAllByPricesList(pl);
		
		assertTrue(app.size() != 0 );
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void test_find_appointment()
	{
		Appointment app = appointmentService.findAppointment("21-03-2020 07:30", 1, "KlinikaA");
		assertFalse(app == null);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void test_save()
	{
		Clinic clinic = clinicService.findByName("KlinikaA");
		Hall hall = hallService.findByNumberAndClinic(1, clinic);
		Priceslist pl = priceListService.findByTypeOfExaminationAndClinic("Opsti pregled","KlinikaA");
		
		Appointment app4 = new Appointment.Builder(DateUtil.getInstance().getDate("05-02-2020 12:00","dd-MM-yyyy HH:mm"))
				.withEndingDate(DateUtil.getInstance().getDate("05-02-2020 13:00","dd-MM-yyyy HH:mm"))
				.withType(AppointmentType.Examination)
				.withHall(hall)
				.withClinic(clinic)
				.withDuration(1)
				.withPriceslist(pl)
				.build();
		appointmentService.save(app4);
		
		Appointment test = appointmentService.findAppointment("05-02-2020 12:00", 1, "KlinikaA");
		
		assertTrue(test !=null);
	}
	
		
	@Test
	@Transactional
	@Rollback(true)
	void test_find_all_by_doctor()
	{
		Doctor doctor = (Doctor) userService.findByEmailAndDeleted("doktor1@gmail.com", false);
		
		List<Appointment> apps = appointmentService.findAllByDoctor(doctor);
		
		assertFalse(apps.size() == 0);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void test_find_all_by_doctor_and_patient()
	{
		Doctor doctor = (Doctor) userService.findByEmailAndDeleted("doktor1@gmail.com", false);
		Patient patient = (Patient) userService.findByEmailAndDeleted("patient@gmail.com", false);
		
		List<Appointment> apps = appointmentService.findAllByDoctorAndPatient(doctor, patient);
		assertTrue(apps.size() != 0);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void test_find_all_by_hall()
	{
		Clinic clinic = clinicService.findByName("KlinikaA");
		Hall hall = hallService.findByNumberAndClinic(1, clinic);
		List<Appointment> apps = appointmentService.findAllByHall(hall);
		
		assertTrue(apps.size() != 0);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void test_find_all_by_date()
	{
		Date date = DateUtil.getInstance().getDate("21-03-2020 07:30","dd-MM-yyyy HH:mm");
		List<Appointment> apps = appointmentService.findAllByDate(date);
		
		assertTrue(apps.size() != 0);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void test_find_all_by_clinic()
	{
		Clinic clinic = clinicService.findByName("KlinikaA");
		List<Appointment> apps = appointmentService.findAllByClinic(clinic);

		assertTrue(apps.size() != 0);

	}
	
}
