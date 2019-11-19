package controller;


import dto.ClinicDTO;
import model.Clinic;
import model.RegistrationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AuthService;
import service.ClinicService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/clinics")
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @PostMapping(value ="/registerClinic", consumes = "application/json")
    public ResponseEntity<Void> login(@RequestBody ClinicDTO dto, HttpServletRequest request)
    {
        HttpHeaders header = new HttpHeaders();

        Clinic c = clinicService.findByName(dto.getName());

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


        if(c == null)
        {
            clinicService.save(new Clinic(clinic));
        } else {
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }



}
