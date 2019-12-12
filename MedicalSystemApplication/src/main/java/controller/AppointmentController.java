package controller;

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
import model.User.UserRole;
import service.AppointmentRequestService;
import service.AppointmentService;
import service.ClinicService;
import service.HallService;
import service.NotificationService;
import service.PriceListService;
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
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private HallService hallService;
	
	@Autowired
	private PriceListService priceslistService;
	
	@GetMapping(value="/get")
	public ResponseEntity<AppointmentDTO> getAppointment(@RequestBody AppointmentDTO dto)
	{
		String clinic = dto.getClinicName();
		String date = dto.getDate();
		int hallNumber = dto.getHallNumber();
		
		Appointment appointment = appointmentService.findAppointment(date, hallNumber,clinic);
		
		HttpHeaders header = new HttpHeaders();
		if(appointment == null)
		{
			header.set("responseText","Appointment not found for: ("+date+","+hallNumber+")");
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}
		
		
		return new ResponseEntity<>(new AppointmentDTO(appointment),HttpStatus.OK);
	}
	
	@GetMapping(value="/getAll")
	public ResponseEntity<List<AppointmentDTO>> getAllAppointments()
	{
		List<Appointment> app = appointmentService.findAll();
		List<AppointmentDTO> appDTO = new ArrayList<AppointmentDTO>();
		
		if(app == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 

		}
		
		for(Appointment a : app)
		{
			appDTO.add(new AppointmentDTO(a));
		}
			
		return new ResponseEntity<>(appDTO,HttpStatus.OK); 
	}
	
	@GetMapping(value="/getRequest")
	public ResponseEntity<AppointmentDTO> getAppointmentRequest(@RequestBody AppointmentDTO dto)
	{
		String clinic = dto.getClinicName();
		String date = dto.getDate();
		int hallNumber = dto.getHallNumber();
		AppointmentRequest appointmentReq = appointmentRequestService.findAppointmentRequest(date, hallNumber,clinic);
		
		HttpHeaders header = new HttpHeaders();
		if(appointmentReq == null)
		{
			header.set("responseText","Appointment not found for: ("+date+","+hallNumber+")");
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new AppointmentDTO(appointmentReq),HttpStatus.OK);
	}
	
	@GetMapping(value="/clinic/getAllRequests/{clinicName}")
	public ResponseEntity<List<AppointmentDTO>> getAppointmentRequests(@PathVariable("clinicName") String clinic)
	{
		List<AppointmentRequest> list = appointmentRequestService.getAllByClinic(clinic);
		
		if(list == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<AppointmentDTO> dtos = new ArrayList<AppointmentDTO>();
		
		for(AppointmentRequest req : list)
		{
			dtos.add(new AppointmentDTO(req));
		}
		
		return new ResponseEntity<>(dtos,HttpStatus.OK);
	}
	
	@GetMapping(value="/patient/getAll/{email}")
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

	@GetMapping(value="/doctor/getAllAppointments/{email}")
	public ResponseEntity<List<Appointment>> getAppointmentsDoctor(@PathVariable("email") String email)
	{
		Doctor  d = null;

		try {
			d = (Doctor)userService.findByEmail(email);
		}
		catch(ClassCastException e)
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		HttpHeaders header = new HttpHeaders();

		if(d == null)
		{
			header.set("responseText","User not found : ("+email+")");
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}

		List<Appointment> appointments = appointmentService.findAllByDoctors(d);

		if(appointments == null)
		{
			header.set("responseText","Appointments not found : ("+email+")");
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(appointments,HttpStatus.OK);

	}


	
	@PostMapping(value="/confirmRequest")
	public ResponseEntity<Void> confirmAppointmentRequest(@RequestBody AppointmentDTO dto)
	{
		HttpHeaders header = new HttpHeaders();
		AppointmentRequest request = appointmentRequestService.findAppointmentRequest(dto.getDate(), dto.getHallNumber(), dto.getClinicName());
		Clinic clinic = clinicService.findByName(dto.getClinicName());
		if(request == null)
		{
			header.set("responseText","Request not found: " + dto.getDate() +" ,"+ dto.getHallNumber() +", "+ dto.getClinicName());
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}
		
		Appointment appointment = new Appointment.Builder(request.getDate())
				.withClinic(request.getClinic())
				.withHall(request.getHall())//TODO: Admin treba da bira salu
				.withPatient(request.getPatient())
				.withType(request.getAppointmentType())
				.build();

		for(Doctor doc : request.getDoctors())
		{
			appointment.getDoctors().add(doc);
		}		
		clinic.getAppointments().add(appointment);
		//Send mail
		appointmentRequestService.delete(request);
		appointmentService.save(appointment);	
		clinicService.save(clinic);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(value="/denyRequest")
	public ResponseEntity<Void> denyAppoinmtnetRequest(@RequestBody AppointmentDTO dto)
	{
		HttpHeaders header = new HttpHeaders();
		AppointmentRequest request = appointmentRequestService.findAppointmentRequest(dto.getDate(), dto.getHallNumber(), dto.getClinicName());
		
		if(request == null)
		{
			header.set("responseText","Request not found: " + dto.getDate() +" ,"+ dto.getHallNumber() +", "+ dto.getClinicName());
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}
		
		//Send mail
		appointmentRequestService.delete(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value="/sendRequest")
	public ResponseEntity<Void> addAppointmentRequest(@RequestBody AppointmentDTO dto)
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
			Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(dto.getDate());
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
		
		Priceslist pl = priceslistService.findByTypeOfExamination(dto.getTypeOfExamination());
		
		request.setPriceslist(pl);
		
		Hall hall = hallService.findByNumber(dto.getHallNumber());
		
		if(hall == null)
		{
			header.set("responseText","Hall not found: " + dto.getHallNumber());
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}
		
		request.setHall(hall);
	
		
		List<User> admins = userService.getAll(UserRole.ClinicAdmin);
		
		for(User user : admins)
		{
			ClinicAdmin admin = (ClinicAdmin)user;
			
			if(admin.getClinic().getName().equals(clinic.getName()))
			{
				//TODO: Napisati lepo mail
				notificationService.sendNotification(admin.getEmail(), "Novi zahtev za pregled", "Imate novi zahtev za pregled..");
			}
		}
		
		appointmentRequestService.save(request);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	
}
