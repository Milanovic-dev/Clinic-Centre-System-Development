package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.UserDTO;
import model.User;
import service.UserService;

@RestController
@RequestMapping(value = "api/users")
public class UserController 
{
	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/update/{email}")
	public ResponseEntity<Void> updateUser(@RequestBody UserDTO dto,@PathVariable("email")String email)
	{	
		User user = userService.findByEmail(email);
		if(user != null) {
			user.setName(dto.getName());
			user.setSurname(dto.getSurname());
			user.setEmail(dto.getEmail());
			user.setUsername(dto.getUsername());
			user.setAddress(dto.getAddress());
			user.setCity(dto.getCity());
			user.setState(dto.getState());
			user.setPassword(dto.getPassword());
			user.setPhone(dto.getPhone());
			userService.save(user);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
