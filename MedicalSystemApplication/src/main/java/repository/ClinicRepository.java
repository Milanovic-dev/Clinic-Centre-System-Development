package repository;

import model.Clinic;
import model.Doctor;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface ClinicRepository extends JpaRepository<Clinic,Long> {

    public Clinic findByName(String name);
    
    public Clinic findByDoctors(Doctor d);
    
    @Lock(value = LockModeType.PESSIMISTIC_READ)
    public List<Clinic> findAll();
    
}

