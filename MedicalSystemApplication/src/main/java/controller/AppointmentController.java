package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.AppointmentDTO;
import helpers.DateInterval;
import helpers.DateUtil;
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
	//Get By doctor and patient
	@GetMapping(value="/getAppointments/{doctorEmail}/{patientEmail}")
	public ResponseEntity<List<AppointmentDTO>> getAppointmentsByPatient(@PathVariable("doctorEmail") String doctorEmail,@PathVariable("patientEmail") String patientEmail )
	{
		Patient p = (Patient) userService.findByEmailAndDeleted(patientEmail, false);
		Doctor d = (Doctor) userService.findByEmailAndDeleted(doctorEmail, false);
		
		
		if(p == null )
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		}
		
		if(d == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		}
		
		List<Appointment> app = appointmentService.findAllByDoctorAndPatient(d, p);
		List<AppointmentDTO> appDTO = new ArrayList<AppointmentDTO>();
		
		if(app == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		}
		
		for(Appointment a : app)
		{
			appDTO.add(new AppointmentDTO(a));
		}
		
		return new ResponseEntity<List<AppointmentDTO>>(appDTO,HttpStatus.OK);
		
	}

	@GetMapping(value="/getAppointment/{clinicName}/{date}/{hallNumber}")
	public ResponseEntity<AppointmentDTO> getApp(@PathVariable("clinicName") String clinic, @PathVariable("date") String date,
												 @PathVariable("hallNumber") int hallNumber)
	{
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
	
	@GetMapping(value="/clinic/getAllAppointments/{clinicName}")
	public ResponseEntity<List<AppointmentDTO>> getAppointmentsClinic(@PathVariable("clinicName") String clinicName)
	{
		
		Clinic clinic = clinicService.findByName(clinicName);
		
		if(clinic == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<Appointment> list = appointmentService.findAllByClinic(clinic);
		
		if(list == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<AppointmentDTO> dtos = new ArrayList<AppointmentDTO>();
		
		for(Appointment app : list)
		{
			dtos.add(new AppointmentDTO(app));
		}
		
		return new ResponseEntity<>(dtos,HttpStatus.OK);
	}
	
	@GetMapping(value="/clinic/getAllAppointmentsToday/{clinicName}")
	public ResponseEntity<List<AppointmentDTO>> getAppointmentsClinicToday(@PathVariable("clinicName") String clinicName)
	{
		
		Date today = new Date();
		
		Clinic clinic = clinicService.findByName(clinicName);
		
		if(clinic == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<Appointment> list = appointmentService.findAllByClinic(clinic);
		
		if(list == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<AppointmentDTO> dtos = new ArrayList<AppointmentDTO>();
		
		for(Appointment app : list)
		{
			if(DateUtil.getInstance().isSameDay(today, app.getDate()))
			{
				dtos.add(new AppointmentDTO(app));
			}
		}
		
		return new ResponseEntity<>(dtos,HttpStatus.OK);
	}
	
	
	@GetMapping(value="/clinic/getAllAppointmentsWeek/{clinicName}")
	public ResponseEntity<List<AppointmentDTO>> getAppointmentsClinicWeekly(@PathVariable("clinicName") String clinicName)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		Date weekStart = cal.getTime();
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		Date weekEnd = cal.getTime();
		
		
		Clinic clinic = clinicService.findByName(clinicName);
		
		if(clinic == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<Appointment> list = appointmentService.findAllByClinic(clinic);
		
		if(list == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<AppointmentDTO> dtos = new ArrayList<AppointmentDTO>();
		
		for(Appointment app : list)
		{
			if(app.getDate().after(weekStart) && app.getDate().before(weekEnd))
			{
				dtos.add(new AppointmentDTO(app));
			}
		} 
		
		
		return new ResponseEntity<>(dtos,HttpStatus.OK);
	}
	
	
	@GetMapping(value="/clinic/getAllAppointmentsMonth/{clinicName}")
	public ResponseEntity<List<AppointmentDTO>> getAppointmentsClinicMonth(@PathVariable("clinicName") String clinicName)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date monthStart = cal.getTime();
		cal.add(Calendar.MONTH, 1);
		Date monthEnd = cal.getTime();
		
		Clinic clinic = clinicService.findByName(clinicName);
		
		if(clinic == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<Appointment> list = appointmentService.findAllByClinic(clinic);
		
		if(list == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<AppointmentDTO> dtos = new ArrayList<AppointmentDTO>();
		
		for(Appointment app : list)
		{
			if(app.getDate().after(monthStart) && app.getDate().before(monthEnd))
			{
				dtos.add(new AppointmentDTO(app));
			}
		} 
		
		return new ResponseEntity<>(dtos,HttpStatus.OK);
	}
	
	@GetMapping(value="/hall/getAll/{clinicName}/{hallNumber}")
	public ResponseEntity<List<AppointmentDTO>> getAllByHall(@PathVariable("clinicName") String clinicName, @PathVariable("hallNumber") int hallNumber)
	{
		Clinic clinic = clinicService.findByName(clinicName);
		
		if(clinic == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Hall hall = hallService.findByNumber(hallNumber);
		
		if(hall == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<Appointment> apps = appointmentService.findAllByHallAndClinic(hall, clinic);
		List<AppointmentDTO> dtos = new ArrayList<AppointmentDTO>();
		
		for(Appointment app : apps)
		{
			AppointmentDTO dto = new AppointmentDTO(app);
			dto.setDate(app.getDate().toString());
			dto.setEndDate(app.getEndDate().toString());
			dtos.add(dto);		
		}
		
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
	
	@GetMapping(value="/patient/getAllRequests/{email}")
	public ResponseEntity<List<AppointmentDTO>> getPatientRequests(@PathVariable("email") String email)
	{
		Patient p = (Patient) userService.findByEmailAndDeleted(email, false);
		
		if(p == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<AppointmentRequest> list = appointmentRequestService.getAllByPatient(p);
		
		List<AppointmentDTO> dtos = new ArrayList<AppointmentDTO>();
		
		for(AppointmentRequest req : list)
		{
			dtos.add(new AppointmentDTO(req));
		}
		
		return new ResponseEntity<>(dtos,HttpStatus.OK);
	}
	
	@GetMapping(value="/patient/getAll/{email}")
	public ResponseEntity<List<AppointmentDTO>> getAppointments(@PathVariable("email") String email)
	{
		Patient  p = null;
		
		try {
			p = (Patient)userService.findByEmailAndDeleted(email,false);		
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
		List<AppointmentDTO> dto = new ArrayList<AppointmentDTO>();
		
		for(Appointment app : appointments)
		{
			dto.add(new AppointmentDTO(app));
		}
		
		if(appointments == null)
		{
			header.set("responseText","Appointments not found : ("+email+")");
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(dto,HttpStatus.OK);
		
	}
	
	
	@GetMapping(value="/getAllPredefined")
	public ResponseEntity<List<AppointmentDTO>> getPredefined()
	{
		List<Appointment> appointments = appointmentService.findAllByPredefined();
		
		if(appointments.size() == 0)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<AppointmentDTO> dtos = new ArrayList<AppointmentDTO>();
		
		for(Appointment app : appointments)
		{
			if(app.getPatient() == null)
			{
				dtos.add(new AppointmentDTO(app));						
			}
		}
		
		return new ResponseEntity<>(dtos,HttpStatus.OK);
	}

	@GetMapping(value="/doctor/getAllAppointments/{email}")
	public ResponseEntity<List<AppointmentDTO>> getAppointmentsDoctor(@PathVariable("email") String email)
	{
		Doctor  d = null;

		try {
			d = (Doctor)userService.findByEmailAndDeleted(email,false);
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
	
		List<Appointment> appointments = d.getAppointments();
		List<AppointmentDTO> dto = new ArrayList<AppointmentDTO>();
		
		for(Appointment app : appointments)
		{
			dto.add(new AppointmentDTO(app));
		}

		if(appointments == null)
		{
			header.set("responseText","Appointments not found : ("+email+")");
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(dto,HttpStatus.OK);

	}
	
	@GetMapping(value="/doctor/getAllAppointmentsByDate/{email}/{date}")
	public ResponseEntity<List<AppointmentDTO>> getAppointmentsDoctorByDate(@PathVariable("email") String email, @PathVariable("date") String date)
	{
		Doctor  d = null;

		try {
			d = (Doctor)userService.findByEmailAndDeleted(email,false);
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
	
		List<Appointment> appointments = d.getAppointments();
		List<AppointmentDTO> dto = new ArrayList<AppointmentDTO>();
		
		for(Appointment app : appointments)
		{
			Boolean sameDay = DateUtil.getInstance().isSameDay(app.getDate(), DateUtil.getInstance().getDate(date, "dd-MM-yyyy"));
			if(sameDay)
			{
				dto.add(new AppointmentDTO(app));				
			}
		}

		if(appointments == null)
		{
			header.set("responseText","Appointments not found : ("+email+")");
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(dto,HttpStatus.OK);

	}

	
	@GetMapping(value="/doctor/getAllAppointmentsCalendar/{email}")
	public ResponseEntity<List<AppointmentDTO>> getAppointmentsDoctorCalendar(@PathVariable("email") String email)
	{
		Doctor  d = null;

		try {
			d = (Doctor)userService.findByEmailAndDeleted(email,false);
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
	
		List<Appointment> appointments = d.getAppointments();
		List<AppointmentDTO> dto = new ArrayList<AppointmentDTO>();
		
		for(Appointment app : appointments)
		{
			if(app.getPatient() != null && !app.getPredefined())
			{
				AppointmentDTO dt = new AppointmentDTO(app);
				dt.setDate(app.getDate().toString());
				dto.add(dt);			
			}
		}

		if(appointments == null)
		{
			header.set("responseText","Appointments not found : ("+email+")");
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(dto,HttpStatus.OK);

	}
	
	
	@PostMapping(value="/makePredefined")
	public ResponseEntity<Void> makePredefined(@RequestBody AppointmentDTO dto)
	{		
		HttpHeaders header = new HttpHeaders();
		Clinic clinic = clinicService.findByName(dto.getClinicName());
		
		if(clinic == null)
		{
			header.set("responseText","Clinic " + dto.getClinicName() +" is not found");
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}
		
		Date date = DateUtil.getInstance().getDate(dto.getDate(), "dd-MM-yyyy HH:mm");
		Date endDate = DateUtil.getInstance().getDate(dto.getEndDate(), "dd-MM-yyyy HH:mm");
		
		
		Hall hall = hallService.findByNumber(dto.getHallNumber());
		
		if(hall == null)
		{
			header.set("responseText","Hall " + dto.getHallNumber() +" is not found");

			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}
		
		List<Appointment> appointments = appointmentService.findAllByHallAndClinic(hall, clinic);
		
		
		for(Appointment app : appointments)
		{
			DateInterval d1 = new DateInterval(app.getDate(),app.getEndDate());
			DateInterval d2 = new DateInterval(date, endDate);
			
			if(DateUtil.getInstance().overlappingInterval(d1, d2))
			{
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		}
		
		
		Priceslist p = priceslistService.findByTypeOfExamination(dto.getTypeOfExamination());
		
		if(p == null)
		{
			header.set("responseText","Priceslist " + dto.getTypeOfExamination() +" is not found");
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}
		
		ArrayList<Doctor> doctors = new ArrayList<Doctor>();
		
		for(String email : dto.getDoctors())
		{
			Doctor d = (Doctor) userService.findByEmailAndDeleted(email, false);
			
			for(Appointment app : d.getAppointments())
			{
				DateInterval d1 = new DateInterval(app.getDate(),app.getEndDate());
				DateInterval d2 = new DateInterval(date, endDate);
				
				if(DateUtil.getInstance().overlappingInterval(d1, d2))
				{
					return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
				}
			}
			
			doctors.add(d);
		}
		
		
		
		Appointment a = appointmentService.findAppointment(date, hall, clinic);
		
		if(a != null)
		{
			return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
		}
		
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
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	
	@PostMapping(value="/confirmRequest")
	public ResponseEntity<Void> confirmAppointmentRequest(@RequestBody AppointmentDTO dto)
	{
		HttpHeaders header = new HttpHeaders();
		AppointmentRequest request = appointmentRequestService.findAppointmentRequest(dto.getDate(), 0, dto.getClinicName());


		Clinic clinic = clinicService.findByName(dto.getClinicName());

		if(request == null)
		{
			header.set("responseText","Request not found: " + dto.getDate() +" ,"+ dto.getHallNumber() +", "+ dto.getClinicName());
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}
		
		Hall hall = hallService.findByNumber(dto.getHallNumber());
		
		if(hall == null)
		{
			header.set("responseText","Hall not found " + dto.getHallNumber());
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}
		
		List<Appointment> apps = appointmentService.findAllByHall(hall);
		
		DateUtil util = DateUtil.getInstance();
		Date desiredStartTime = util.getDate(dto.getDate(), "dd-MM-yyyy HH:mm");
		Date desiredEndTime = util.getDate(dto.getEndDate(), "dd-MM-yyyy HH:mm");
		DateInterval di1 = new DateInterval(desiredStartTime, desiredEndTime);
		
		for(Appointment app : apps)
		{
			DateInterval di2 = new DateInterval(app.getDate(), app.getEndDate());
			
			if(util.overlappingInterval(di1, di2))
			{
				header.set("responseText", "hall");
				return new ResponseEntity<>(header, HttpStatus.CONFLICT);
			}
		}

		for(String email : dto.getDoctors()){
			Doctor d = (Doctor) userService.findByEmailAndDeleted(email, false);
			List<Appointment> appsByDoc = appointmentService.findAllByDoctor(d);

			for(Appointment app : appsByDoc) {
				DateInterval di2 = new DateInterval(app.getDate(), app.getEndDate());

				if (util.overlappingInterval(di1, di2)) {
					header.set("responseText", "doctor,"+d.getName() + " " + d.getSurname());
					return new ResponseEntity<>(header, HttpStatus.CONFLICT);
				}
			}
		}
		
		Appointment appointment = new Appointment.Builder(desiredStartTime)
				.withClinic(request.getClinic())
				.withHall(hall)//TODO: Admin treba da bira salu
				.withPatient(request.getPatient())
				.withType(request.getAppointmentType())
				.withPriceslist(request.getPriceslist())
				.withEndingDate(desiredEndTime)
				.build();

		for(Doctor doc : request.getDoctors())
		{
			appointment.getDoctors().add(doc);
		}

		if(appointment.getAppointmentType() == Appointment.AppointmentType.Surgery){
			for(String email : dto.getDoctors())
			{
				Doctor d = (Doctor) userService.findByEmailAndDeleted(email, false);
				appointment.getDoctors().add(d);
				notificationService.sendNotification(email, "Nova operacija je zakazana",  "Zakazana je nova operacija u Vasem radnom kalendaru. Datum operacije je "+ dto.getDate() +
						", u klinici  " + appointment.getClinic().getName() + ", u sali " + appointment.getHall().getName() + ", broj " + appointment.getHall().getNumber() + ".");

			}
//
//			if(util.isSameDay(request.getDate(), desiredStartTime)){
//				notificationService.sendNotification("miaknezevic5@gmail.com", "Vasa operacija je zakazana", "Zahtev za operaciju je prihvacen. Datum operacije je "+ dto.getDate() +
//						", u klinici  " + appointment.getClinic().getName() + ", u sali " + appointment.getHall().getName() + ", broj " + appointment.getHall().getNumber() + "." );
//			} else {
//				notificationService.sendNotification("miaknezevic5@gmail.com", "Datum operacije je promenjen", "Datum operacije koja je bila zakazana " + dto.getDate() +  ", promenjen je na  "+ dto.getDate() +
//						". Operacija je zakazana u klinici  " + appointment.getClinic().getName() + ", u sali " + appointment.getHall().getName() + ", broj " + appointment.getHall().getNumber() + "." );
//			}

		}




		clinic.getAppointments().add(appointment);
		
		//TODO:Send mail Pacijentu (Prihavti ili odbije)

		//TODO:Send mail Doktoru


		appointmentService.save(appointment);
		
		appointmentRequestService.delete(request);
		
		for(Doctor doc : request.getDoctors())
		{
			doc.getAppointments().add(appointment);
			userService.save(doc);
		}		
		
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
		request.setTimestamp(new Date());
		Clinic clinic = clinicService.findByName(dto.getClinicName());
		
		if(clinic == null)
		{
			header.set("responseText","Clinic not found: " + dto.getClinicName());
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}		
		request.setClinic(clinic);
	
		
		Patient patient = (Patient) userService.findByEmailAndDeleted(dto.getPatientEmail(),false);
		
		if(patient == null)
		{
			header.set("responseText","Patient not found: " + dto.getPatientEmail());
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}
		request.setPatient(patient);
		
		Date date = DateUtil.getInstance().getDate(dto.getDate(), "dd-MM-yyyy HH:mm");
		
		request.setDate(date);
		request.setAppointmentType(dto.getType());
		
				
		for(String email : dto.getDoctors())
		{
			Doctor doctor = (Doctor) userService.findByEmailAndDeleted(email,false);
				
				
			if(doctor == null)
			{
				header.set("responseText","Doctor not found: " + email);
				return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
			}
				
			request.getDoctors().add(doctor);	
		}
		
		Priceslist pl = priceslistService.findByTypeOfExamination(dto.getTypeOfExamination());
		
		if(pl == null)
		{
			header.set("responseText","Priceslist not found: " + dto.getTypeOfExamination());
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}
		
		request.setPriceslist(pl);
		
		
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
	
	
	@DeleteMapping(value="/cancelRequest/{role}")
	public ResponseEntity<Void> cancelAppointmentRequest(@RequestBody AppointmentDTO dto, @PathVariable("role")UserRole role)
	{
		AppointmentRequest req = appointmentRequestService.findAppointmentRequest(dto.getDate(), dto.getPatientEmail(), dto.getClinicName());
				
		if(req == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Date tm = req.getTimestamp();
		
		Date date = new Date();
		
		if(date.getTime() > tm.getTime() + 24 * DateUtil.HOUR_MILLIS)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<User> admins = userService.getAll(UserRole.ClinicAdmin);	
		Patient p = req.getPatient();
		
		
		
		if(role == UserRole.Patient)
		{
			for(User user : admins)
			{
				ClinicAdmin admin = (ClinicAdmin)user;
				
				if(admin.getClinic().getName().equals(dto.getClinicName()))
				{
					//TODO: Napisati lepo mail
					notificationService.sendNotification(admin.getEmail(), "Pacijent je otkazao pregled", "Zahtev za pregled zakazan za " + dto.getDate() + " je otkazan od strane pacijenta: " + p.getEmail());
				}
			}
			
			
			notificationService.sendNotification(p.getEmail(), "Vas zahtev za pregled je otkazan.", "Zahtev za pregled zakazan za " + dto.getDate() + " je otkazan na vas zahtev.");
		}
		else
		{
			for(User user : admins)
			{
				ClinicAdmin admin = (ClinicAdmin)user;
				
				if(admin.getClinic().getName().equals(dto.getClinicName()))
				{
					//TODO: Napisati lepo mail
					notificationService.sendNotification(admin.getEmail(), "Pregled je otkazan", "Zahtev za pregled zakazan za " + dto.getDate() + " je otkazan od strane admina klinike");
				}
			}
			
			
			notificationService.sendNotification(p.getEmail(), "Vas zahtev za pregled je otkazan.", "Zahtev za pregled zakazan za " + dto.getDate() + " je otkazan od strane admina.");
		}
		
		
		appointmentRequestService.delete(req);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@PutMapping(value="/reservePredefined/{email}")
	public ResponseEntity<Void> reservePredfined(@RequestBody AppointmentDTO dto, @PathVariable("email") String email)
	{
		HttpHeaders headers = new HttpHeaders();
		
		Patient p = (Patient) userService.findByEmailAndDeleted(email, false);


		if(p == null)
		{
			headers.set("responseText","Patient with email " + email + " not found");
			return new ResponseEntity<>(headers,HttpStatus.NOT_FOUND);
		}
		
		Appointment app = appointmentService.findAppointment(dto.getDate(), dto.getHallNumber(), dto.getClinicName());
		
		if(app == null)
		{
			headers.set("responseText","App not found for: " + dto.getDate() + " " + dto.getHallNumber() + " " + dto.getClinicName());
			return new ResponseEntity<>(headers,HttpStatus.NOT_FOUND);
		}
		
		List<Appointment> appointments = appointmentService.findAllByPatient(p);
		
		for(Appointment appointment : appointments)
		{
			DateInterval interval1 = new DateInterval(appointment.getDate(), appointment.getEndDate());
			DateInterval interval2 = new DateInterval(app.getDate(), app.getEndDate());
			
			if(DateUtil.getInstance().overlappingInterval(interval1, interval2))
			{
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		}
		
		if(app.getVersion() != dto.getVersion())
		{
			return new ResponseEntity<>(HttpStatus.LOCKED);
		}
		
		app.setPatient(p);
			
		try
		{
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("Uspesno ste zakazali pregled(");
			strBuilder.append(app.getPriceslist().getTypeOfExamination());
			strBuilder.append(") za datum ");
			strBuilder.append(app.getDate());
			strBuilder.append(" na klinici ");
			strBuilder.append(app.getClinic().getName());
			strBuilder.append(" u sali Br. ");
			strBuilder.append(app.getHall().getNumber());
			strBuilder.append(". Vas doktor je ");
			strBuilder.append(app.getDoctors().get(0).getName() + " " + app.getDoctors().get(0).getSurname());
			strBuilder.append(". Cena pregleda je ");
			strBuilder.append(app.getPriceslist().getPrice());
			strBuilder.append("rsd.");
			System.out.println(strBuilder.toString());
			notificationService.sendNotification(p.getEmail(), "Zakazali ste pregled!", strBuilder.toString());
			appointmentService.save(app);			
		}
		catch(ObjectOptimisticLockingFailureException e)
		{
			return new ResponseEntity<>(HttpStatus.LOCKED);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}


	@PutMapping(value="/appointmentIsDone")
	public ResponseEntity<Void> appointmentIsDone(@RequestBody AppointmentDTO dto)
	{
		HttpHeaders headers = new HttpHeaders();

		Appointment app = appointmentService.findAppointment(dto.getDate(), dto.getHallNumber(), dto.getClinicName());

		if(app == null)
		{
			headers.set("responseText","App not found for: " + dto.getDate() + " " + dto.getHallNumber() + " " + dto.getClinicName());
			return new ResponseEntity<>(headers,HttpStatus.NOT_FOUND);
		}

		app.setDone(true);
		appointmentService.save(app);

		return new ResponseEntity<>(HttpStatus.OK);
	}



}
