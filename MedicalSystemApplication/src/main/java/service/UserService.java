package service;

import java.util.*;

import helpers.SecurePasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import model.*;
import model.MedicalRecord.BloodType;
import model.User.UserRole;
import repository.ClinicRepository;
import repository.HallRepository;
import repository.UserRepository;

import javax.annotation.PostConstruct;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;


	public void delete(User user)
	{
		userRepository.delete(user);
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
	
	public User findByEmailAndDeleted(String email,Boolean deleted)
	{
		return userRepository.findByEmailAndDeleted(email,deleted);
	}
	
	public void save(User user)
	{
		userRepository.save(user);
	}
}
