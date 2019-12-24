package repository;

import dto.PrescriptionDTO;
import model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {
    Prescription findById(long id);
}
