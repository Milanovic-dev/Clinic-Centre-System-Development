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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.AppointmentDTO;
import model.*;
import model.Appointment.AppointmentType;
import model.User.UserRole;
import service.AppointmentRequestService;
import service.AppointmentService;
import service.ClinicService;
import service.HallService;
import service.NotificationService;
import service.UserService;

@RestController
@RequestMapping(value = "api/doctors")
public class DoctorController 
{
	@Autowired
	private UserService userService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@PostMapping(value="/makeDoctor/{email}/{startShift}/{endShift}")
	public ResponseEntity<Void> addDoctor(@PathVariable("email") String email,
										  @PathVariable("startShift") String start,
										  @PathVariable("endShift") String end)
	{
		User user = userService.findByEmail(email);
		
		if(user == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		Date dateStart;
		Date dateEnd;
		try {
			dateStart = df.parse(start);
			dateEnd = df.parse(end);
			Doctor doctor = new Doctor(user);
			doctor.setShiftStart(dateStart);
			doctor.setShiftEnd(dateEnd);
			userService.delete(user);//TODO:SetDeleted
			userService.save(doctor);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping(value="/removeDoctor/{email}")
	public ResponseEntity<Void> removeDoctor(@PathVariable("email") String email)
	{
		HttpHeaders header = new HttpHeaders();
		Doctor doc = (Doctor)userService.findByEmail(email);
		
		if(doc == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<Appointment> appointments = appointmentService.findAllByDoctors(doc);
		
		
		if(appointments != null)
		{
			if(appointments.size() > 0)
			{
				header.set("responseText","Doktor("+email+") ne moze biti obrisan jer ima "+appointments.size()+" zakazanih pregleda");
				return new ResponseEntity<Void>(header,HttpStatus.CONFLICT);			
			}
		}
		else
		{
			return new ResponseEntity<Void>(header,HttpStatus.CONFLICT);	
		}
		doc.setDeleted(true);
		userService.save(doc);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
