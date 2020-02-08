package com.group14.MedicalSystemApplication.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import dto.UserDTO;
import dto.VacationDTO;
import helpers.DateUtil;
import model.Appointment;
import model.Clinic;
import model.Doctor;
import model.Nurse;
import model.User;
import model.VacationRequest;
import service.UserService;
import service.VacationRequestService;
import service.VacationService;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class VacationControllerTest {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VacationService vacationService;
	
	@Autowired
	private VacationRequestService vacationRequestService;
	
	@Test
	@Transactional
	@Rollback(true)
	void check_vacation_available()
	{
		UserDTO udto = new UserDTO();
		udto.setAddress("Kisacka 11");
		udto.setCity("Novi Sad");
		udto.setEmail("doktor2@gmail.com");
		
		VacationDTO vdto = new VacationDTO();
		vdto.setStartDate("12-02-2020");
		vdto.setEndDate("18-02-2020");
		vdto.setUser(udto);
		
		assertFalse(vdto.getUser() == null);
		
		User user = userService.findByEmailAndDeleted(vdto.getUser().getEmail(), false);

		assertFalse(user == null);
    	
    	Date vacationStart = DateUtil.getInstance().getDate(vdto.getStartDate(), "dd-MM-yyyy");
		Date vacationEnd = DateUtil.getInstance().getDate(vdto.getEndDate(), "dd-MM-yyyy");
    	
    	List<VacationRequest> requests  = vacationRequestService.findAllByUser(user);
  	 	    
    	for(VacationRequest request : requests)
    	{
    		assertFalse(vacationStart.before(request.getEndDate()) && vacationEnd.after(request.getStartDate()));	
    	}

    	
    	
    	if(user instanceof Doctor)
    	{
    		Doctor doctor = (Doctor)user;
    		
    		List<Appointment> appointments = doctor.getAppointments();
    		   		
    		for(Appointment app : appointments)
    		{
    			Date date = app.getDate();
    			assertFalse(date.after(vacationStart) && date.before(vacationEnd));
    		}
    	}
    	
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void make_vacation_request()
	{
		UserDTO udto = new UserDTO();
		udto.setAddress("Kisacka 11");
		udto.setCity("Novi Sad");
		udto.setEmail("doktor2@gmail.com");
		
		VacationDTO vdto = new VacationDTO();
		vdto.setStartDate("12-02-2020");
		vdto.setEndDate("18-02-2020");
		vdto.setUser(udto);
		
		assertFalse(vdto.getUser() == null);

    	User user = userService.findByEmailAndDeleted(vdto.getUser().getEmail(), false);
		assertFalse(user == null);

    	Clinic clinic = null;
    	
    	if(user instanceof Doctor)
    	{
    		Doctor doctor = (Doctor)user;
    		clinic = doctor.getClinic();
    	}
    	else if(user instanceof Nurse)
    	{
    		Nurse nurse = (Nurse)user;
    		clinic = nurse.getClinic();
    	}
    	    	
    	VacationRequest vr = new VacationRequest();
    	vr.setStartDate(DateUtil.getInstance().getDate(vdto.getStartDate(), "dd-MM-yyyy"));
    	vr.setEndDate(DateUtil.getInstance().getDate(vdto.getEndDate(), "dd-MM-yyyy"));
    	vr.setClinic(clinic);
    	vr.setUser(user);
    	   	
    	vacationRequestService.save(vr);
    	
    	List<VacationRequest> vacations = vacationRequestService.findAllByUser(user);
    	
    	assertNotEquals(vacations.size(),0);
	}
	
}
