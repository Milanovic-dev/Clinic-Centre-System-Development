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
import service.ProfileService;

@RestController
@RequestMapping(value = "api/profiles")
public class MedicalStaffController 
{
	@Autowired
	private ProfileService service;
	
	@PostMapping(value = "/update/{email}")
	public ResponseEntity<Void> login(@RequestBody UserDTO dto,@PathVariable("email")String email)
	{	
		if(service.updateUser(email,dto))
		{
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
