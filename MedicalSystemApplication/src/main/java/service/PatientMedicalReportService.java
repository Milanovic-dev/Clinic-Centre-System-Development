package service;

import dto.PatientMedicalReportDTO;
import model.*;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import repository.DiagnosisRepository;
import repository.DrugRepository;
import repository.PatientMedicalReportRepository;
import repository.PrescriptionRepository;

import javax.validation.ValidationException;

@Service
public class PatientMedicalReportService {

    @Autowired
    private PatientMedicalReportRepository patientMedicalReportRepository;

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public void save(PatientMedicalReport patientMedicalReport)
    {
        patientMedicalReportRepository.save(patientMedicalReport);
    }

    public void delete(PatientMedicalReport patientMedicalReport)
    {
        patientMedicalReportRepository.delete(patientMedicalReport);
    }


    public PatientMedicalReport findByPatient(Patient patient) {
        return patientMedicalReportRepository.findByPatient(patient);
    }

    public PatientMedicalReport findById(long id) {
        return patientMedicalReportRepository.findById(id);
    }

    @Transactional(isolation =  Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public void updatePatientMedicalReport(PatientMedicalReportDTO dto) throws ObjectOptimisticLockingFailureException {

        PatientMedicalReport report = patientMedicalReportRepository.findById(dto.getId());
        if(report == null)
        {
            throw new ValidationException("Report not found: " + report.getId());
        }

        report.getDiagnosis().clear();
        for(String d : dto.getDiagnosis()){
            Diagnosis diagnosis = diagnosisRepository.findByName(d);
            report.getDiagnosis().add(diagnosis);
        }
        report.setDescription(dto.getDescription());

        long idPres = report.getPrescription().getId();
        Prescription prescription = prescriptionRepository.findById(idPres);

        prescription.setDescription(dto.getPrescription().getDescription());
        report.getPrescription().getDrugs().clear();
        for(String d : dto.getPrescription().getDrugs()){
            Drug drug = drugRepository.findByName(d);
            report.getPrescription().getDrugs().add(drug);
        }

        prescription.setValid(false);
        prescription.setNurse(null);
        prescription.setValidationDate(null);


        if(report != null)
        {
            if(dto.getVersion() != report.getVersion())
            {
                throw new ObjectOptimisticLockingFailureException("Resource Locked.", report);
            }
        }

        prescriptionRepository.save(prescription);

        report.setPrescription(prescription);
        patientMedicalReportRepository.save(report);

    }
}
