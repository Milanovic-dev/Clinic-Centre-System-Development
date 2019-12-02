package controller;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.MedicalRecordDTO;
import dto.PasswordDTO;
import dto.UserDTO;
import helpers.SecurePasswordHasher;
import model.*;
import model.User.UserRole;
import service.UserService;

@RestController
@RequestMapping(value = "api/users")
public class UserController 
{
	@Autowired
	private UserService userService;
	
	@PutMapping(value = "/update/{email}")
	public ResponseEntity<Void> updateUser(@RequestBody UserDTO dto,@PathVariable("email")String email)
	{	
		User user = userService.findByEmail(email);
		if(user != null) {
			user.setName(dto.getName());
			user.setSurname(dto.getSurname());
			user.setUsername(dto.getUsername());
			user.setAddress(dto.getAddress());
			user.setCity(dto.getCity());
			user.setState(dto.getState());
			user.setPhone(dto.getPhone());
			userService.save(user);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping(value="/update/password/{email}",consumes ="application/json")
	public ResponseEntity<Void> updatePassword(@PathVariable("email") String email,@RequestBody PasswordDTO dto)
	{
		System.out.println(email);
		User user = userService.findByEmail(email);
		
		if(user != null)
		{		
			try {
				String oldPassword = dto.getOldPassword();
				String oldHash = SecurePasswordHasher.encode(oldPassword);
				
				if(user.getPassword().equals(oldHash))
				{
					String newPassword = dto.getNewPassword();
					String newHash = SecurePasswordHasher.encode(newPassword);
					user.setPassword(newHash);
					userService.save(user);
					return new ResponseEntity<>(HttpStatus.OK);
				}
				
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		
		
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping(value = "/update/firstPassword/{email}" ,consumes = "application/json")
	public ResponseEntity<Void> updateFirstPassword(@PathVariable("email") String email,@RequestBody PasswordDTO dto)
	{
		User user = userService.findByEmail(email);
		if(user != null)
		{
			String newPassword = dto.getNewPassword();
			try {
				String hashNewPassword = SecurePasswordHasher.encode(newPassword);
				user.setPassword(hashNewPassword);
				user.setIsFirstLog(false);
				userService.save(user);
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/getUser/{email}")
	public ResponseEntity<User> getUser(@PathVariable("email") String email)
	{
		User ret = userService.findByEmail(email);
		
		if(ret == null)
		{
			return new ResponseEntity<>(ret,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(ret,HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAll/{role}")
	public ResponseEntity<List<User>> getAllUsersByRole(@PathVariable("role") UserRole role)
	{
		List<User> ret = userService.getAll(role);
		
		if(ret == null)
		{
			return new ResponseEntity<>(ret,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(ret,HttpStatus.OK);
		
		
	}
	
	@GetMapping(value = "/getAll")
	public ResponseEntity<List<User>> getAllUsers()
	{
		List<User> ret = userService.getAll();
		
		if(ret == null)
		{
			return new ResponseEntity<>(ret,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(ret,HttpStatus.OK);
	}
	
	@GetMapping(value="/patient/getMedicalRecord/{email}")
	public ResponseEntity<MedicalRecordDTO> getMedicalRecord(@PathVariable("email")String email)
	{
		Patient patient = (Patient)userService.findByEmail(email);
		
		if(patient == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		MedicalRecord mr = patient.getMedicalRecord();
		
		MedicalRecordDTO dto = new MedicalRecordDTO(mr);
		
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
	
	
	@PutMapping(value="/patient/updateMedicalReport/{email}")
	public ResponseEntity<Void> updateMedicalRecord(@PathVariable("email") String email,@RequestBody MedicalRecordDTO dto)
	{
		Patient patient = (Patient)userService.findByEmail(email);
		
		if(patient == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		patient.getMedicalRecord().setAlergies(dto.getAlergies());
		patient.getMedicalRecord().setHeight(dto.getHeight());
		patient.getMedicalRecord().setWeight(dto.getWeight());
		
		userService.save(patient);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
