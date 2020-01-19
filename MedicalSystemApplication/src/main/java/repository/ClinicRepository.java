package repository;

import model.Clinic;
import model.Doctor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic,Long> {

    public Clinic findByName(String name);
    
    public Clinic findByDoctors(Doctor d);
    

}

