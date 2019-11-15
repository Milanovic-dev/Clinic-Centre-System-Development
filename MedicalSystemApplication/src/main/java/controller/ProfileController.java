package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dto.UserDTO;
import service.ProfileService;

@RestController
@RequestMapping(value = "api/profile")
public class ProfileController 
{
	@Autowired
	private ProfileService service;
	
	@PostMapping(value = "/update")
	public ResponseEntity<Void> login(@RequestBody UserDTO dto)
	{	
		if(service.updateUser(dto))
		{
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
