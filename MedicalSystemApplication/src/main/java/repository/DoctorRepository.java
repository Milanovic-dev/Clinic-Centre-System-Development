package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Clinic;
import model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor,Long>{

    public Doctor findByEmail(String email);
    public List<Doctor> findByType(String type);
    public List<Doctor> findAllByClinicAndType(Clinic clinic, String type);
}
