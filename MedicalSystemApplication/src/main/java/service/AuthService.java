package service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.LoginDTO;
import helpers.SecurePasswordHasher;
import model.Patient;
import model.RegistrationRequest;
import model.User;
import repository.RegistrationRequestRepository;
import repository.UserRepository;

@Service
public class AuthService 
{

	@Autowired
	private RegistrationRequestRepository requestRepository;

	public List<RegistrationRequest> getAll()
	{
		return requestRepository.findAll();
	}
	
	public RegistrationRequest findByEmail(String email)
	{
		return requestRepository.findByEmail(email);
	}

	public void save(RegistrationRequest request)
	{
		requestRepository.save(request);
	}
			
	public void delete(RegistrationRequest request)
	{
		requestRepository.delete(request);
	}
}
