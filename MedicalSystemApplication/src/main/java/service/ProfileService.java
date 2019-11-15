package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.UserDTO;
import model.Doctor;
import model.User;
import repository.UserRepository;

@Service
public class ProfileService 
{
	
	@Autowired
	private UserRepository userRepository;
	
	public Boolean updateUser(String email,UserDTO u)
	{
		User user = userRepository.findByEmail(email);
		if(user != null) {
			user.setName(u.getName());
			user.setSurname(u.getSurname());
			user.setEmail(u.getEmail());
			user.setUsername(u.getUsername());
			user.setAddress(u.getAddress());
			user.setCity(u.getCity());
			user.setState(u.getState());
			user.setPassword(u.getPassword());
			user.setPhone(u.getPhone());
			userRepository.save(user);
			return true;
		}
		
		
		return false;
	}
}
