package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.AppointmentRequestDTO;
import model.*;
import model.Appointment.AppointmentType;
import service.AppointmentRequestService;
import service.AppointmentService;
import service.ClinicService;
import service.HallService;
import service.UserService;

@RestController
@RequestMapping(value = "api/appointments")
public class AppointmentController 
{
	
	@Autowired
	private AppointmentService appointmentService;
		
	@Autowired
	private AppointmentRequestService appointmentRequestService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ClinicService clinicService;
	
	@RequestMapping(value="/get/{date}/{hallNumber}")
	public ResponseEntity<Appointment> getAppointment(@PathVariable("date") String date, @PathVariable("hallNumber") int hallNumber)
	{
		Appointment appointment = appointmentService.findByDateAndHall(date, hallNumber);
		
		HttpHeaders header = new HttpHeaders();
		if(appointment == null)
		{
			header.set("responseText","Appointment not found for: ("+date+","+hallNumber+")");
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}
		
		
		return new ResponseEntity<>(appointment,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="patient/getAll/{email}")
	public ResponseEntity<List<Appointment>> getAppointments(@PathVariable("email") String email)
	{
		Patient  p = null;
		
		try {
			p = (Patient)userService.findByEmail(email);		
		}
		catch(ClassCastException e)
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		HttpHeaders header = new HttpHeaders();
		
		if(p == null)
		{
			header.set("responseText","User not found : ("+email+")");
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}
		
		List<Appointment> appointments = appointmentService.findAllByPatient(p);	
		
		if(appointments == null)
		{
			header.set("responseText","Appointments not found : ("+email+")");
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(appointments,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/sendRequest")
	public ResponseEntity<Void> addAppointmentRequest(@RequestBody AppointmentRequestDTO dto)
	{
		HttpHeaders header = new HttpHeaders();
		AppointmentRequest request = new AppointmentRequest();
		
		Clinic clinic = clinicService.findByName(dto.getClinicName());
		
		if(clinic == null)
		{
			header.set("responseText","Clinic not found: " + dto.getClinicName());
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}		
		request.setClinic(clinic);
	
		
		Patient patient = (Patient) userService.findByEmail(dto.getPatientEmail());
		
		if(patient == null)
		{
			header.set("responseText","Patient not found: " + dto.getPatientEmail());
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}
		request.setPatient(patient);
		
		try {
			Date date = new SimpleDateFormat().parse(dto.getDate());
			request.setDate(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		request.setAppointmentType(dto.getType());
		
				
		for(String email : dto.getDoctors())
		{
			Doctor doctor = (Doctor) userService.findByEmail(email);
				
				
			if(doctor == null)
			{
				header.set("responseText","Doctor not found: " + email);
				return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
			}
				
			request.getDoctors().add(doctor);	
		}
		
		request.setAppointmentDescription(dto.getAppointmentDescription());
		
		//TODO : Send Mail
		
		appointmentRequestService.save(request);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
