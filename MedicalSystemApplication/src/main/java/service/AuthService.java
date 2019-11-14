package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.LoginDTO;
import model.Patient;
import model.User;
import repository.UserRepository;

@Service
public class AuthService 
{
	@Autowired
	private UserRepository userRepository;
	
	
	public Boolean Authenticate(LoginDTO loginRequest)
	{		
		User u = userRepository.
				findByUsernameAndPassword(loginRequest.getUsername(), 
										  loginRequest.getPassword());
		return u != null;
	}
	
}
