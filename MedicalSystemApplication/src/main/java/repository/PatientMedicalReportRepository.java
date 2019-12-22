package repository;

import model.Patient;
import model.PatientMedicalReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientMedicalReportRepository extends JpaRepository<PatientMedicalReport, Long> {

    PatientMedicalReport findByPatient(Patient patient);

}
