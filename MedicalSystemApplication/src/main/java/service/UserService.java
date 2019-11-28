package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import helpers.SecurePasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import model.*;
import model.User.UserRole;
import repository.UserRepository;

import javax.annotation.PostConstruct;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Component
	class predefinedAdminCreate {

		@Autowired
		private UserRepository userRepository;

		@PostConstruct
		public void init() {

			String token = "admin1234";

			try {
				String hash = SecurePasswordHasher.encode(token);
				userRepository.save(new CentreAdmin("username", hash, "klinickicentartest@gmail.com", "Admin", "Adminic","Novi Sad","Trg Dositeja Obradovica 6", "Srbija", "011100100", true));
			} catch (Exception e) {
				e.printStackTrace();
			}


			}
	}



	public List<User> getAll(UserRole role)
	{
		return userRepository.findAllByRole(role);
		
	}
	
	public List<User> getAll()
	{
		return userRepository.findAll();
	}
	
	public User findById(Long id)
	{
		Optional<User> user = userRepository.findById(id);
		
		if(user.isPresent())
		{
			return user.get();
		}
		
		return null;
		
	}
	
	public User findByEmail(String email)
	{
		return userRepository.findByEmail(email);
	}
	
	public User findByUsername(String username)
	{
		return userRepository.findByUsername(username);
	}
	
	public void save(User user)
	{
		userRepository.save(user);
	}
}
