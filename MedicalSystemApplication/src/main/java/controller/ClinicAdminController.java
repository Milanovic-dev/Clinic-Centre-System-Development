package controller;

import dto.ClinicAdminDTO;
import helpers.SecurePasswordHasher;
import model.Clinic;
import model.ClinicAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import service.ClinicAdminService;

import java.security.NoSuchAlgorithmException;

@RequestMapping(value = "api/cAdmins")
public class ClinicAdminController {

    @Autowired
    private ClinicAdminService clinicAdminService;

    @PutMapping(value = "/registerClinicAdmin")
    public ResponseEntity<Void> registerClinicAdmin(@RequestBody ClinicAdminDTO dto)
    {
        ClinicAdmin ca = clinicAdminService.findByEmail(dto.getEmail());

        if(ca == null) {
            ClinicAdmin clinicAdmin = new ClinicAdmin();
            clinicAdmin.setName(dto.getName());
            clinicAdmin.setAddress((dto.getAddress()));
            clinicAdmin.setCity(dto.getCity());
            clinicAdmin.setSurname(dto.getSurname());
            clinicAdmin.setState(dto.getState());
            clinicAdmin.setPhone(dto.getPhone());
            clinicAdmin.setEmail(dto.getEmail());

            String token = dto.getPassword();

            try {
                String hash = SecurePasswordHasher.encode(token);
                clinicAdmin.setPassword(hash);
                clinicAdminService.save(clinicAdmin);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }

    }

}
