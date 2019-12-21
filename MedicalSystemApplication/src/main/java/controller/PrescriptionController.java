package controller;


import dto.PrescriptionDTO;
import model.Drug;
import model.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.DrugService;
import service.PrescriptionService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/prescriptions")
public class PrescriptionController {

    @Autowired
    PrescriptionService prescriptionService;

    @Autowired
    DrugService drugService;

    @PostMapping(value ="/addPrescription")
    public ResponseEntity<Void> addPrescription(@RequestBody PrescriptionDTO prescriptionDTO)
    {
        HttpHeaders header = new HttpHeaders();

        Prescription prescription = new Prescription();
        prescription.setDescription(prescriptionDTO.getDescription());

        for(String name : prescriptionDTO.getDrugs())
        {
            Drug drug = drugService.findByName(name);

            if(drug == null)
            {
                header.set("responseText","Drug not found: " + name);
                return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
            }

            prescription.getDrugs().add(drug);
        }

        System.out.println(prescription.getDrugs());
        prescriptionService.save(prescription);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
