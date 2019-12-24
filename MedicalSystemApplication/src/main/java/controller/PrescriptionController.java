package controller;

import dto.PrescriptionDTO;
import model.Nurse;
import model.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.PrescriptionService;
import service.UserService;

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
        Prescription prescription = prescriptionService.findById(dto.getId());

        if(prescription == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Nurse nurse = (Nurse)userService.findByEmailAndDeleted(email, false);
        if(nurse == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Date date = new Date();

        prescription.setValid(true);
        prescription.setNurse(nurse);
        prescription.setValidationDate(date);
        prescriptionService.save(prescription);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
