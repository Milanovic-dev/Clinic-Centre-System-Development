package repository;

import model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic,Long> {

    public Clinic findByName(String name);

}

