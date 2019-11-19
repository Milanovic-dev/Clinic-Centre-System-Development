package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.UserDTO;
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
}
