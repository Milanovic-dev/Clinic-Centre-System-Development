package service;

import model.MedicalRecord;
import model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.MedicalRecordRepository;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public MedicalRecord findByPatient(Patient patient) {
        return medicalRecordRepository.findByPatient(patient);
    }

    public void save(MedicalRecord record) {
        medicalRecordRepository.save(record);
    }
}
