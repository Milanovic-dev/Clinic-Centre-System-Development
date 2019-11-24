package controller;


import dto.ClinicDTO;
import model.Clinic;
import model.RegistrationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.ClinicService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/clinic")
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @PostMapping(value ="/registerClinic", consumes = "application/json")
    public ResponseEntity<Void> login(@RequestBody ClinicDTO dto, HttpServletRequest request)
    {
        HttpHeaders header = new HttpHeaders();

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
            clinic.setClinicAdmins(new ArrayList<>());
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
    
    @GetMapping(value="/{name}")
    public ResponseEntity<ClinicDTO> getClinicByName(@PathVariable String name)
    {
    	Clinic clinic = clinicService.findByName(name);
    	
    	
    	ClinicDTO clinicDTO = new ClinicDTO(clinic);
    	
    	
    	return new ResponseEntity<>(clinicDTO,HttpStatus.OK);
    }

}
