package controller;

import dto.PrescriptionDTO;
import model.Nurse;
import model.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;
import service.PrescriptionService;
import service.UserService;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/getAllPrescriptions")
    public ResponseEntity<List<PrescriptionDTO>> getDrugs()
    {
        List<Prescription> prescriptions = prescriptionService.findAll();
        List<PrescriptionDTO> prescriptionsDTO = new ArrayList<PrescriptionDTO>();
        if(prescriptions == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for(Prescription p: prescriptions)
        {
            PrescriptionDTO dto = new PrescriptionDTO(p);
            if(p.getIsValid() == false){
                prescriptionsDTO.add(dto);
            }
        }

        return new ResponseEntity<>(prescriptionsDTO,HttpStatus.OK);
    }
    
   

    @PutMapping(value = "/validate/{email}")
    public ResponseEntity<Void> confirmRegister(@RequestBody PrescriptionDTO dto, @PathVariable("email") String email)
    {

        dto.setNurseEmail(email);

        try
        {
            prescriptionService.validate(dto);

        } catch (ObjectOptimisticLockingFailureException e) {

            return new ResponseEntity<>(HttpStatus.CONFLICT);

        } catch (ValidationException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
