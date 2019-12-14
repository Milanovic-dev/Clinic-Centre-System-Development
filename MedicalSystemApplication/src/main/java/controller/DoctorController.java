package controller;

import java.security.NoSuchAlgorithmException;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.AppointmentDTO;
import dto.ClinicDTO;
import dto.DoctorDTO;
import helpers.SecurePasswordHasher;
import model.*;
import model.Appointment.AppointmentType;
import model.User.UserRole;
import repository.DoctorRepository;
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
	
	@Autowired 
	private ClinicService clinicService;
	
	@PostMapping(value="/makeNewDoctor/{clinicName}", consumes = "application/json")
	public ResponseEntity<Void> addNewDoctor(@RequestBody DoctorDTO dto,@PathVariable("clinicName") String clinicName)
	{
		Doctor d = (Doctor) userService.findByEmailAndDeleted(dto.getUser().getEmail(),false);
		Clinic c = clinicService.findByName(clinicName);
		
		if(d != null)
		{
			return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
		}
		
		
		if(c == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}
		
		String pass = "";
		try {
			pass = SecurePasswordHasher.getInstance().encode("123");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		Doctor doctor = new Doctor(dto);
		doctor.setPassword(pass);
		doctor.setClinic(c);
		userService.save(doctor);
		
		c.getDoctors().add(doctor);
		clinicService.save(c);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
		
	@GetMapping(value="/getClinic/{email}")
	public ResponseEntity<ClinicDTO> getClinicByDoctor(@PathVariable("email") String email)
	{
		Doctor d = (Doctor) userService.findByEmailAndDeleted(email,false);
		
		if(d == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
		
		Clinic c = clinicService.findByDoctor(d);
		ClinicDTO dto = new ClinicDTO(c);
		return new ResponseEntity<>(dto,HttpStatus.OK);

	}
	
	@DeleteMapping(value="/removeDoctor/{email}")
	public ResponseEntity<Void> removeDoctor(@PathVariable("email") String email)
	{
		HttpHeaders header = new HttpHeaders();
		Doctor doc = (Doctor)userService.findByEmailAndDeleted(email,false);
		
		
		if(doc == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<Appointment> appointments = appointmentService.findAllByDoctor(doc);
		
		
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
