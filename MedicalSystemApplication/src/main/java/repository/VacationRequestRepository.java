package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Clinic;
import model.User;
import model.VacationRequest;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long>
{
	public List<VacationRequest> findAllByUser(User user);
	public List<VacationRequest> findAllByClinic(Clinic clinic);
}
