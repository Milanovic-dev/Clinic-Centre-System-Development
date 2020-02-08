package controller;

import dto.PatientMedicalReportDTO;
import dto.PrescriptionDTO;
import helpers.DateUtil;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;
import service.*;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/reports")
public class PatientMedicalReportController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private PatientMedicalReportService patientMedicalReportService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private DrugService drugService;

    @Autowired
    private MedicalRecordService medicalRecordService;


    @GetMapping(value = "/getAllReports/{email}")
    public ResponseEntity<List<PatientMedicalReportDTO>> getAllReports(@PathVariable("email")String email)
    {
        HttpHeaders header = new HttpHeaders();

        Patient patient = (Patient)userService.findByEmailAndDeleted(email,false);
        if(patient == null)
        {
            header.set("responseText","patient not found: " + email);
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        List<PatientMedicalReport> ret = patient.getMedicalRecord().getReports();

        List<PatientMedicalReportDTO> dtos = new ArrayList<>();
        for(PatientMedicalReport p : ret)
        {
            dtos.add(new PatientMedicalReportDTO(p));
        }

        System.out.println(dtos);

        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }


    @PutMapping(value = "/updateReport/{id}")
    public ResponseEntity<Void> updateReport(@PathVariable("id")long id, @RequestBody PatientMedicalReportDTO dto)
    {

        dto.setId(id);

        try
        {
            patientMedicalReportService.updatePatientMedicalReport(dto);

        } catch (ObjectOptimisticLockingFailureException e) {

            return new ResponseEntity<>(HttpStatus.CONFLICT);

        } catch (ValidationException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/getReportPrescription/{reportId}")
    public ResponseEntity<PrescriptionDTO> getReport(@PathVariable("reportId")long reportId)
    {
        HttpHeaders header = new HttpHeaders();

        PatientMedicalReport report = patientMedicalReportService.findById(reportId);
        if(report == null)
        {
            header.set("responseText","report not found: " + reportId);
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        Prescription ret = report.getPrescription();
        PrescriptionDTO dto = new PrescriptionDTO(ret);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PostMapping(value="/addPatientMedicalReport/{email}")
    public ResponseEntity<Void> addPatientMedicalReport(@PathVariable("email") String email, @RequestBody PatientMedicalReportDTO dto)
    {
        HttpHeaders header = new HttpHeaders();

        Patient patient = (Patient)userService.findByEmailAndDeleted(email,false);
        if(patient == null)
        {
            header.set("responseText","patient not found: " + email);
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }

        Doctor doctor = (Doctor)userService.findByEmailAndDeleted(dto.getDoctorEmail(),false);
        if(doctor == null)
        {
            header.set("responseText","doctor not found: " + dto.getDoctorEmail());
            return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
        }

        Clinic clinic = clinicService.findByName(dto.getClinicName());
        if(clinic == null)
        {
            header.set("responseText","clinic not found: " + dto.getClinicName());
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }



        Prescription pr = new Prescription();
        pr.setDescription(dto.getPrescription().getDescription());
        for(String name : dto.getPrescription().getDrugs())
        {
            Drug drug = drugService.findByName(name);

            if(drug == null)
            {
                header.set("responseText","Drug not found: " + name);
                return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
            }

            pr.getDrugs().add(drug);

        }
        pr.setValid(false);
        prescriptionService.save(pr);

        PatientMedicalReport report = new PatientMedicalReport();
        report.setClinic(clinic);
        report.setDescription(dto.getDescription());
        report.setDoctor(doctor);
        report.setPrescription(pr);
        report.setDateAndTime(DateUtil.getInstance().getDate(dto.getDateAndTime(),"dd-MM-yyyy"));
        report.setPatient(patient);

        for(String name : dto.getDiagnosis())
        {
            Diagnosis diagnosis = diagnosisService.findByName(name);

            if(diagnosis == null)
            {
                header.set("responseText","Diagnosis not found: " + name);
                return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
            }


            report.getDiagnosis().add(diagnosis);
        }

        patientMedicalReportService.save(report);

        MedicalRecord mr = medicalRecordService.findByPatient(patient);
        mr.getReports().add(report);
        medicalRecordService.save(mr);

        userService.save(patient);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
