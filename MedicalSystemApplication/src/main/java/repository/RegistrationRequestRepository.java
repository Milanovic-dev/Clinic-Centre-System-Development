package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.RegistrationRequest;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest,Long>{

	public RegistrationRequest findByEmail(String email);
	
}
