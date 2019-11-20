package controller;

import dto.ClinicAdminDTO;
import model.ClinicAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import service.ClinicAdminService;

@RequestMapping(value = "api/cAdmins")
public class ClinicAdminController {

    @Autowired
    private ClinicAdminService clinicAdminService;

    @PutMapping(value = "/registerClinicAdmin")
    public ResponseEntity<Void> registerClinicAdmin(@RequestBody ClinicAdminDTO dto)
    {
        ClinicAdmin clinicAdmin = clinicAdminService.findByEmail(dto.getEmail());
        if(clinicAdmin == null) {
            clinicAdmin.setName(dto.getName());
            clinicAdmin.setSurname(dto.getSurname());
            clinicAdmin.setUsername(dto.getUsername());
            clinicAdmin.setAddress(dto.getAddress());
            clinicAdmin.setCity(dto.getCity());
            clinicAdmin.setState(dto.getState());
            clinicAdmin.setPhone(dto.getPhone());
            clinicAdminService.save(clinicAdmin);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
    }

}
