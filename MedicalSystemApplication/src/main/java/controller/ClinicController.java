package controller;


import dto.ClinicDTO;
import dto.ClinicFilterDTO;
import dto.DoctorDTO;
import dto.HallDTO;
import dto.ReviewDTO;
import dto.UserDTO;
import filters.ClinicFilter;
import filters.DoctorFilter;
import filters.Filter;
import filters.FilterFactory;
import filters.HallFilter;
import filters.PatientFilter;
import model.Appointment;
import model.Clinic;
import model.ClinicReview;
import model.Doctor;
import model.Nurse;
import model.Patient;
import model.RegistrationRequest;
import helpers.DateUtil;
import helpers.ListUtil;
import helpers.Scheduler;
import helpers.UserSortingComparator;

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

import service.AppointmentService;
import service.ClinicService;
import service.NotificationService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "api/clinic")
public class ClinicController {

    @Autowired
    private ClinicService clinicService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
    private NotificationService notificationService;

    @PostMapping(value ="/registerClinic", consumes = "application/json")
    public ResponseEntity<Void> registerClinic(@RequestBody ClinicDTO dto, HttpServletRequest request)
    {

        Clinic c = clinicService.findByName(dto.getName());

        if(c == null) {
            Clinic clinic = new Clinic();
            clinic.setName(dto.getName());
            clinic.setAddress((dto.getAddress()));
            clinic.setCity(dto.getCity());
            clinic.setDescription(dto.getDescription());
            clinic.setState(dto.getState());
            clinic.setDoctors(new ArrayList<>());
            clinic.setHalls(new ArrayList<>());
            clinic.setReviews(new ArrayList<>());
            clinicService.save(clinic);
        } else {
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping(value = "/getNurse/{emailNurse}")
    public ResponseEntity<ClinicDTO> getClinicFromNurse(@PathVariable("emailNurse") String emailNurse)
    {
    	Nurse n = (Nurse) userService.findByEmailAndDeleted(emailNurse,false);
		
		if(n == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
		
		
		ClinicDTO dto = new ClinicDTO(n.getClinic());
		return new ResponseEntity<>(dto,HttpStatus.OK);

    }
    
    @GetMapping(value = "/getAll")
    public ResponseEntity<ClinicDTO[]> getClinics()
    {
    	List<Clinic> clinics = clinicService.findAllSafe();
    	List<ClinicDTO> clinicsDTO = new ArrayList<ClinicDTO>();
    	if(clinics == null)
    	{
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	
    	for(Clinic c: clinics)
    	{
    		ClinicDTO dto = new ClinicDTO(c);
    		clinicsDTO.add(dto);
    	}
    	
    	
    	
    	return new ResponseEntity<>(clinicsDTO.toArray(new ClinicDTO[clinicsDTO.size()]),HttpStatus.OK);
    }
    
    @PostMapping(value="/getAll/{date}/{type}")
    public ResponseEntity<ClinicDTO[]> getClinicsWithFilter(@RequestBody ClinicFilterDTO dto, @PathVariable("date") String date,@PathVariable("type") String typeOfExamination)
    {
    	List<Clinic> clinics = clinicService.findAllSafe();
    	List<ClinicDTO> clinicsDTO = new ArrayList<ClinicDTO>();
    	
    	if(clinics == null)
    	{
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
  
    	Date realDate = DateUtil.getInstance().getDate(date, "dd-MM-yyyy");
						
		Filter filter = FilterFactory.getInstance().get("clinic");
			
		for(Clinic c: clinics)
	    {
	    	List<Doctor> doctors = c.getDoctors();
	    		
	    	for(Doctor d: doctors)
	    	{
	    		int freeTime = Scheduler.getFreeIntervals(d, realDate).size();
	    		
	    		if(d.IsFreeOn(realDate) && d.getType().equalsIgnoreCase(typeOfExamination) && freeTime > 0)
	    		{
	    			if(filter.test(c, dto))
	    			{
	    				clinicsDTO.add(new ClinicDTO(c));
	    				break;				
	    			}
	    		}
	    	}
	    }
		ClinicDTO[] ret = clinicsDTO.toArray(new ClinicDTO[clinicsDTO.size()]);
			
		return new ResponseEntity<>(ret,HttpStatus.OK);		
    }
    
    
    
    @GetMapping(value="/getPatients/{clinicName}")
    public ResponseEntity<List<UserDTO>> getClinicPatients(@PathVariable("clinicName") String clinicName)
    {
    	Clinic c = clinicService.findByName(clinicName);
    	
    	if(c == null)
      {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
  
    	List<Appointment> appointments = new ArrayList<Appointment>();
    	ArrayList<UserDTO> patients = new ArrayList<UserDTO>();

    	appointments = appointmentService.findAllByClinic(c);
    	
    	if(appointments.isEmpty())
    	{
    		System.out.println("Nema pregleda u toj klinici");
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    	}
    	
    	
    	for(Appointment app: appointments)
    	{   		
    		if(app.getPatient() == null) continue;
    		
    		if(!ListUtil.getInstance().containsWithEmail(patients,app.getPatient().getEmail()))
    		{
    			patients.add(new UserDTO(app.getPatient()));    			
    		}
    	}
    	
    	patients.sort(new UserSortingComparator());
      	
		System.out.println(patients.size());

    	return new ResponseEntity<>(patients,HttpStatus.OK);
    	
    }
    
    @PostMapping(value="/getPatientsByFilter/{clinicName}",consumes = "application/json")
    public ResponseEntity<List<UserDTO>> getClinicPatientsByFilter(@PathVariable("clinicName") String clinicName,@RequestBody UserDTO dto)
    {
		HttpHeaders header = new HttpHeaders();
    	Clinic c = clinicService.findByName(clinicName);
    	
    	if(c == null)
    	{
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	
    	ArrayList<UserDTO> ret = new ArrayList<UserDTO>();
    	List<Appointment> appointments = new ArrayList<Appointment>();

		PatientFilter filter = (PatientFilter) FilterFactory.getInstance().get("patient");

    	appointments = appointmentService.findAllByClinic(c);
    	
    	if(appointments.isEmpty())
    	{
    		System.out.println("Nema pregleda u toj klinici");
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	
    	
    	for(Appointment app: appointments)
    	{   	
    		Patient p = app.getPatient();
    		
    		if(p == null)
    		{
    			continue;
    		}
    		
    		if(!ListUtil.getInstance().containsWithEmail(ret, p.getEmail()))
    		{
    			if(filter.test(app.getPatient(), dto))
    			{
    				ret.add(new UserDTO(app.getPatient()));
    			}			
    		}
    	}


    	return new ResponseEntity<>(ret,HttpStatus.OK);
    	
    }
    
    @GetMapping(value="/getDoctorsByType/{clinicName}/{typeOfExamination}")
    public ResponseEntity<List<DoctorDTO>> getClinicDoctorsByType(@PathVariable("clinicName") String clinicName,@PathVariable("typeOfExamination") String typeOfExamination)
    {
    	Clinic clinic = clinicService.findByName(clinicName);
    	if(clinic == null)
    	{
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	
    	List<Doctor> doctors = clinic.getDoctors();
    	List<DoctorDTO> dtos = new ArrayList<DoctorDTO>();
    	
    	for(Doctor doc : doctors)
    	{
    		if(doc.getDeleted() == false && doc.getType().equalsIgnoreCase(typeOfExamination))
    		{
    			DoctorDTO dto = new DoctorDTO(doc);
    			dtos.add(dto);	
    		}
    	}
    	
    	return new ResponseEntity<>(dtos,HttpStatus.OK);

    }
    
    @GetMapping(value="/getDoctorsByTypeAndVacation/{clinicName}/{typeOfExamination}/{date}")
    public ResponseEntity<List<DoctorDTO>> getClinicDoctorsByTypeAndVacation(@PathVariable("clinicName") String clinicName,@PathVariable("typeOfExamination") String typeOfExamination
    																		,@PathVariable("date") String date)
    {
    	Clinic clinic = clinicService.findByName(clinicName);
    	if(clinic == null)
    	{
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	
    	List<Doctor> doctors = clinic.getDoctors();
    	List<DoctorDTO> dtos = new ArrayList<DoctorDTO>();
    	
    	for(Doctor doc : doctors)
    	{
    		if(doc.getDeleted() == false && doc.getType().equalsIgnoreCase(typeOfExamination)
    		   && doc.IsFreeOn(DateUtil.getInstance().getDate(date, "dd-MM-yyyy")))
    		{
    			DoctorDTO dto = new DoctorDTO(doc);
    			dtos.add(dto);	
    		}
    	}
    	
    	return new ResponseEntity<>(dtos,HttpStatus.OK);

    }

    @GetMapping(value="/getDoctors/{name}")
    public ResponseEntity<List<DoctorDTO>> getClinicsDoctors(@PathVariable("name") String name)
    {
    	Clinic clinic = clinicService.findByName(name);
    	
    	if(clinic == null)
    	{
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	
    	List<Doctor> doctors = clinic.getDoctors();
    	List<DoctorDTO> dtos = new ArrayList<DoctorDTO>();
    	
    	for(Doctor doc : doctors)
    	{
    		if(doc.getDeleted() == false)
    		{
    			DoctorDTO dto = new DoctorDTO(doc);
    			dtos.add(dto);	
    		}
    	}
    	
    	return new ResponseEntity<>(dtos,HttpStatus.OK);
    }
    
    @PostMapping(value="/getDoctorsByFilter/{clinicName}")
    public ResponseEntity<List<DoctorDTO>> getDoctorsByFilter(@RequestBody DoctorDTO dto, @PathVariable("clinicName") String clinicName)
    {
    	Clinic clinic = clinicService.findByName(clinicName);

    	if(clinic == null)
    	{
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	
    	List<Doctor> doctors = clinic.getDoctors();
    	List<DoctorDTO> ret = new ArrayList<DoctorDTO>();
    	
    	DoctorFilter filter = (DoctorFilter) FilterFactory.getInstance().get("doctor");
    	
    	for(Doctor doctor : doctors)
    	{
    		if(filter.test(doctor, dto))
    		{
    			ret.add(new DoctorDTO(doctor));
    		}
    	}
    	
    	return new ResponseEntity<>(ret, HttpStatus.OK);
    }
    
    @PostMapping(value="/addReview")
    public ResponseEntity<Void> addReview(@RequestBody ReviewDTO dto)
    {
    	Clinic clinic = clinicService.findByName(dto.getClinicName());
    	Patient patient = (Patient) userService.findByEmailAndDeleted(dto.getPatientEmail(), false);
    	
    	if(clinic == null || patient == null)
    	{
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	
    	List<ClinicReview> reviews = clinic.getReviews();
    	
    	for(ClinicReview review : reviews)
    	{
    		if(review.getPatient().getEmail().equals(dto.getPatientEmail()))
    		{
    			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    		}
    	}
    	
    	ClinicReview cr = new ClinicReview(dto.getRating(), DateUtil.getInstance().now("dd-MM-yyyy"), patient);
    	clinic.getReviews().add(cr);
    	
    	clinicService.save(clinic);
    	
    	notificationService.sendNotification(patient.getEmail(), "Ocenili ste kliniku!", "Vasa ocena od " + dto.getRating() + " zvezdice za kliniku "+dto.getClinicName()+" je uspesno zabelezena! Hvala vam na recenziji!");
    	
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PutMapping(value="/update/{name}")
    public ResponseEntity<Void> updateClinic(@PathVariable("name") String name,@RequestBody ClinicDTO dto)
    {
    	Clinic clinic = clinicService.findByName(name);
    	
    	if(clinic == null)
    	{
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	
    	if(dto.getName() != "")
    		clinic.setName(dto.getName());
    	
    	if(dto.getAddress() != "")
    		clinic.setAddress(dto.getAddress());
    	
    	if(dto.getCity() != "")
    		clinic.setCity(dto.getCity());
    	
    	if(dto.getDescription() != "")
    		clinic.setDescription(dto.getDescription());
    	
    	if(dto.getState() != "")
    		clinic.setState(dto.getState());
    	clinicService.save(clinic);
    	return new ResponseEntity<>(HttpStatus.OK);
    		
    }
    
  
    @GetMapping(value="/{name}")
    public ResponseEntity<ClinicDTO> getClinicByName(@PathVariable("name") String name)
    {
    	Clinic clinic = clinicService.findByName(name);
    	
    	if(clinic == null)
    	{
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	
    	ClinicDTO clinicDTO = new ClinicDTO(clinic);
    	
    	
    	return new ResponseEntity<>(clinicDTO,HttpStatus.OK);
    }

}
