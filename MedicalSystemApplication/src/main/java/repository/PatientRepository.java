package repository;
import org.springframework.data.jpa.repository.JpaRepository;

import model.Patient;

public interface PatientRepository extends JpaRepository<Patient,Long>{

}
