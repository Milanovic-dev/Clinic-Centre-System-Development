package controller;


import dto.ClinicDTO;
import dto.DoctorDTO;
import model.Clinic;
import model.Doctor;
import model.RegistrationRequest;

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
import service.ClinicService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/clinic")
public class ClinicController {

    @Autowired
    private ClinicService clinicService;
    
    @Autowired
    private UserService userService;

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
            clinic.setAppointments(new ArrayList<>());
            clinic.setDoctors(new ArrayList<>());
            clinic.setHalls(new ArrayList<>());
            clinic.setReviews(new ArrayList<>());
            clinicService.save(clinic);
        } else {
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<ClinicDTO>> getClinics()
    {
    	List<Clinic> clinics = clinicService.findAll();
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
    	
    	
    	
    	return new ResponseEntity<>(clinicsDTO,HttpStatus.OK);
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
    		DoctorDTO dto = new DoctorDTO(doc);
    		dtos.add(dto);
    	}
    	
    	return new ResponseEntity<>(dtos,HttpStatus.OK);
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
    	
    	return new ResponseEntity<>(HttpStatus.OK);
    		
    }
    
    @PutMapping(value="/addDoctor/{clinic}/{email}")
    public ResponseEntity<Void> addDoctor(@PathVariable("email") String email,@PathVariable("clinic") String clinic)
    {
    	Doctor d= (Doctor)userService.findByEmail(email);
    	Clinic c = clinicService.findByName(clinic);
    	if(d== null || c==null)
    	{
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	
    	c.getDoctors().add(d);
    	clinicService.save(c);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @DeleteMapping(value="/removeDoctor/{clinic}/{email}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable("email") String email,@PathVariable("clinic") String clinic)
    {
    	Doctor d= (Doctor)userService.findByEmail(email);
    	Clinic c = clinicService.findByName(clinic);
    	if(d== null || c==null)
    	{
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	
    	c.getDoctors().remove(d);
    	clinicService.save(c);
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
