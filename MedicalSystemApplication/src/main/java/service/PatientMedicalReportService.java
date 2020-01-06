package service;

import model.Patient;
import model.PatientMedicalReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PatientMedicalReportRepository;

@Service
public class PatientMedicalReportService {

    @Autowired
    private PatientMedicalReportRepository patientMedicalReportRepository;

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
}
