package controller;

import helpers.SecurePasswordHasher;
import model.Clinic;
import model.ClinicAdmin;
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

import dto.ClinicDTO;
import dto.UserDTO;
import service.ClinicService;
import service.UserService;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "api/admins/clinic")
public class ClinicAdminController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private ClinicService clinicService;
    
    @GetMapping(value = "/getClinicFromAdmin/{email}")
    public ResponseEntity<ClinicDTO>getClinicFromAdmin(@PathVariable("email") String email)
    {
    	ClinicAdmin ca = (ClinicAdmin) userService.findByEmail(email);
    	if(ca == null)
    	{
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	
    	ClinicDTO dto = new ClinicDTO(ca.getClinic());
		return new ResponseEntity<ClinicDTO>(dto , HttpStatus.OK);
    	
    }

    @PostMapping(value = "/registerClinicAdmin/{clinicName}")
    public ResponseEntity<Void> registerClinicAdmin(@RequestBody UserDTO dto,@PathVariable("clinicName") String clinicName)
    {
        ClinicAdmin ca = (ClinicAdmin) userService.findByEmail(dto.getEmail());
        
        Clinic clinic = clinicService.findByName(clinicName);
        HttpHeaders header = new HttpHeaders();
        
        if(clinic == null)
        {
        	header.set("Response", "Clinic not found");
        	return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
        }

        if(ca == null) {
            ClinicAdmin clinicAdmin = new ClinicAdmin();
            clinicAdmin.setName(dto.getName());
            clinicAdmin.setAddress((dto.getAddress()));
            clinicAdmin.setCity(dto.getCity());
            clinicAdmin.setSurname(dto.getSurname());
            clinicAdmin.setState(dto.getState());
            clinicAdmin.setPhone(dto.getPhone());
            clinicAdmin.setEmail(dto.getEmail());
            clinicAdmin.setVacationRequests(new ArrayList<>());
            clinicAdmin.setAppointmentRequests(new ArrayList<>());
            clinicAdmin.setClinic(clinic);
            String token = "admin1234";

            try {
                String hash = SecurePasswordHasher.getInstance().encode(token);
                clinicAdmin.setPassword(hash);
                userService.save(clinicAdmin);
                clinicAdmin.setClinic(clinic);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                header.set("Response", "Saving failed");
                return new ResponseEntity<>(header,HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
        	header.set("Response", "Admin with that email already exists.");
            return new ResponseEntity<>(header,HttpStatus.ALREADY_REPORTED);
        }

    }

}
