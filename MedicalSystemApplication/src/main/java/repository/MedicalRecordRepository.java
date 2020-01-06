package repository;

import model.MedicalRecord;
import model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord,Long> {
    public MedicalRecord findByPatient(Patient patient);
}
