package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Clinic;
import model.Hall;

public interface HallRepository extends JpaRepository<Hall,Long> {
	public Hall findByNumber(int number);
	public List<Hall> findByClinic(Clinic c);
}
