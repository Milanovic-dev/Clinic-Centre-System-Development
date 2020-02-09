package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.ValidationException;

import model.*;

import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.VacationDTO;
import helpers.DateUtil;
import service.*;

@RestController
@RequestMapping(value = "api/vacation")
public class VacationController {


	@Autowired
	private UserService userService;

	@Autowired
	private ClinicService clinicService;

	@Autowired
	private VacationRequestService vacationRequestService;

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private VacationService vacationService;

	@PostMapping(value ="/checkAvailability/{clinicName}")
	public ResponseEntity<Boolean> checkAvailabillity(@RequestBody VacationDTO vdto, @PathVariable("clinicName") String clinicName)
	{

		if(vdto.getUser() == null)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		User user = userService.findByEmailAndDeleted(vdto.getUser().getEmail(), false);

		if(user == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Date vacationStart = DateUtil.getInstance().getDate(vdto.getStartDate(), "dd-MM-yyyy");
		Date vacationEnd = DateUtil.getInstance().getDate(vdto.getEndDate(), "dd-MM-yyyy");

		List<VacationRequest> requests  = vacationRequestService.findAllByUser(user);

		if(requests != null)
		{
			for(VacationRequest request : requests)
			{

				if(vacationStart.before(request.getEndDate()) && vacationEnd.after(request.getStartDate()))
				{
					return new ResponseEntity<>(false, HttpStatus.OK);
				}

			}
		}


		if(user instanceof Doctor)
		{
			Doctor doctor = (Doctor)user;

			List<Appointment> appointments = doctor.getAppointments();

			for(Appointment app : appointments)
			{
				Date date = app.getDate();

				if(date.after(vacationStart) && date.before(vacationEnd))
				{
					return new ResponseEntity<>(false, HttpStatus.OK);
				}
			}
		}
		else if(user instanceof Nurse)
		{
			Nurse nurse = (Nurse)user;

//			List<Appointment> appointments = nurse.getAppointments();
//
//			for(Appointment app : appointments)
//			{
//				Date date = app.getDate();
//
//				if(date.after(vacationStart) && date.before(vacationEnd))
//				{
//					return new ResponseEntity<>(false, HttpStatus.OK);
//				}
//			}
		}

		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@PostMapping(value="/makeVacationRequest/{clinicName}")
	public ResponseEntity<Void> makeVacationRequest(@RequestBody VacationDTO vdto,@PathVariable ("clinicName") String clinicName)
	{
		if(vdto.getUser() == null)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		User user = userService.findByEmailAndDeleted(vdto.getUser().getEmail(), false);

		if(user == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

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
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping(value="/getAllVacationRequestsByClinic/{clinicName}")
	public ResponseEntity<List<VacationDTO>> getAllVacationRequestsByClinic(@PathVariable ("clinicName") String clinicName)
	{
		Clinic c = clinicService.findByName(clinicName);

		if(c == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<VacationRequest> vacationRequests = vacationRequestService.findAllByClinic(c);
		List<VacationDTO> vdto = new ArrayList<>();

		for(VacationRequest vrq : vacationRequests)
		{
			vdto.add(new VacationDTO(vrq));
		}

		return new ResponseEntity<>(vdto,HttpStatus.OK);

	}


	@PostMapping(value = "/confirmVacationRequest")
	public ResponseEntity<Void> confirmVacationRequest(@RequestBody VacationDTO dto)
	{
		User user = userService.findByEmailAndDeleted(dto.getUser().getEmail(), false);
		List<VacationRequest> vrq = vacationRequestService.findAllByUser(user);
		
		try
		{
			Vacation req = vacationRequestService.resolveVacationRequestLock(vrq, dto, true);			
			notificationService.sendNotification(req.getUser().getEmail(), "Zahtev za godišnji odmor ili odsustvo ",
					"Poštovani,"
							+ "Vaš zahtev za godišnji odmor ili odsustvo u periodu od " + DateUtil.getInstance().getString(req.getStartDate(), "dd-MM-yyyy") + " do " + DateUtil.getInstance().getString(req.getEndDate(), "dd-MM-yyyy") + " je odobren.");
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.LOCKED);
		}

	}

	@DeleteMapping(value="/denyVacationRequest/{denyText}")
	public ResponseEntity<Void> denyVacationRequest (@RequestBody VacationDTO dto,@PathVariable("denyText") String denyText)
	{
		User user = userService.findByEmailAndDeleted(dto.getUser().getEmail(), false);
		List<VacationRequest> vrq = vacationRequestService.findAllByUser(user);

		try
		{
			Vacation req = vacationRequestService.resolveVacationRequestLock(vrq, dto, true);			
			notificationService.sendNotification(req.getUser().getEmail(), "Zahtev za godišnji odmor ili odsustvo ",
					"Poštovani,"
							+ "Vaš zahtev za godišnji odmor ili odsustvo u periodu od " + DateUtil.getInstance().getString(req.getStartDate(), "dd-MM-yyyy") + " do " + DateUtil.getInstance().getString(req.getEndDate(), "dd-MM-yyyy") + " je odbijen.Razlog odbijanja zahteva je sledeći: " + denyText);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.LOCKED);
		}

	}


	@GetMapping(value="/getAllVacationsByUser/{email}")
	public ResponseEntity<List<VacationDTO>> getAllVacationsByUser(@PathVariable ("email") String email)
	{
		User u = userService.findByEmailAndDeleted(email, false);

		if(u == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<Vacation> vacations = vacationService.findAllByUser(u);
		List<VacationDTO> vdto = new ArrayList<>();

		for(Vacation vac : vacations)
		{
			VacationDTO dt = new VacationDTO(vac);
			dt.setStartDate(vac.getStartDate().toString());
			dt.setEndDate(vac.getEndDate().toString());
			vdto.add(dt);
		}

		return new ResponseEntity<>(vdto,HttpStatus.OK);

	}

}