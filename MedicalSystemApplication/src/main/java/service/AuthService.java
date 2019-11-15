package service;

import java.security.NoSuchAlgorithmException;

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
	private UserRepository userRepository;
	
	@Autowired
	private RegistrationRequestRepository requestRepository;
	
	public Boolean authenticate(LoginDTO loginRequest)
	{		
		User u = userRepository.
				findByEmail(loginRequest.getEmail());
		
		if(u == null)
		{
			return false;
		}
		
		String token = loginRequest.getPassword();
		
		try {
			String hash = SecurePasswordHasher.encode(token);
			
			if(hash.equals(u.getPassword()))
			{
				return true;
			}
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return false;
	}
	
	public Boolean handleRegistrationRequest(RegistrationRequest request)
	{
		RegistrationRequest req = requestRepository.
				findByEmail(request.getUsername());
		
		//Odbij request ukoliko postoji u bazi
		if(req != null)
		{
			return false;
		}
		
		requestRepository.save(new RegistrationRequest(request));
		return true;
	}
	
	public void registerNewUser(RegistrationRequest request) throws NoSuchAlgorithmException
	{
		Patient patient = new Patient(request);
		String token = patient.getPassword();
		patient.setPassword(SecurePasswordHasher.encode(token));				
		userRepository.save(patient);
	}
	
	public void registerNewUser(String email) throws NoSuchAlgorithmException
	{
		RegistrationRequest request = requestRepository.findByEmail(email);
		Patient patient = new Patient(request);
		String token = patient.getPassword();
		patient.setPassword(SecurePasswordHasher.encode(token));				
		userRepository.save(patient);
	}
	
}
