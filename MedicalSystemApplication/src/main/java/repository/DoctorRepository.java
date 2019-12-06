package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Clinic;
import model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor,Long>{

    public Doctor findByEmail(String email);

}
