package controller;


import dto.UserDTO;
import helpers.SecurePasswordHasher;
import model.CentreAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.UserService;


@RestController
@RequestMapping(value = "api/admins/centre")
public class CentreAdminController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/registerCentreAdmin")
    public ResponseEntity<Void> registerCentreAdmin(@RequestBody UserDTO dto)
    {
        CentreAdmin ca = (CentreAdmin) userService.findByEmail(dto.getEmail());
        HttpHeaders header = new HttpHeaders();

        if(ca == null) {
            CentreAdmin centreAdmin = new CentreAdmin();
            centreAdmin.setName(dto.getName());
            centreAdmin.setAddress((dto.getAddress()));
            centreAdmin.setCity(dto.getCity());
            centreAdmin.setSurname(dto.getSurname());
            centreAdmin.setState(dto.getState());
            centreAdmin.setPhone(dto.getPhone());
            centreAdmin.setEmail(dto.getEmail());
            centreAdmin.setPredefined(false);
            String token = "admin1234";

            try {
                String hash = SecurePasswordHasher.getInstance().encode(token);
                centreAdmin.setPassword(hash);
                userService.save(centreAdmin);
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
