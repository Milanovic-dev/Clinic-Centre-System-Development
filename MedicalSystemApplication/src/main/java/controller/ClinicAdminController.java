package controller;

import helpers.SecurePasswordHasher;
import model.Clinic;
import model.ClinicAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import dto.UserDTO;
import service.ClinicAdminService;
import service.ClinicService;

import java.security.NoSuchAlgorithmException;

@RequestMapping(value = "api/cAdmins")
public class ClinicAdminController {

    @Autowired
    private ClinicAdminService clinicAdminService;
    
    @Autowired
    private ClinicService clinicService;

    @PostMapping(value = "/registerClinicAdmin/{clinicName}")
    public ResponseEntity<Void> registerClinicAdmin(@RequestBody UserDTO dto,@PathVariable("clinicName") String clinicName)
    {
        ClinicAdmin ca = clinicAdminService.findByEmail(dto.getEmail());
        
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

            String token = "admin1234";

            try {
                String hash = SecurePasswordHasher.encode(token);
                clinicAdmin.setPassword(hash);
                clinicAdminService.save(clinicAdmin);
                clinicAdmin.setClinic(clinic);
                return new ResponseEntity<>(HttpStatus.CREATED);
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
