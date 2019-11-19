package controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.LoginDTO;
import helpers.SecurePasswordHasher;
import model.Patient;
import model.RegistrationRequest;
import model.User;
import service.AuthService;
import service.UserService;

@RestController
@RequestMapping(value = "api/auth")
public class AuthController 
{
	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value ="/login", consumes = "application/json")
	public ResponseEntity<Void> login(@RequestBody LoginDTO dto,HttpServletRequest request)
	{	
		HttpHeaders header = new HttpHeaders();
		
		User u = userService.
				findByEmail(dto.getEmail());
		
		if(u == null)
		{
			header.set("Response","User with that email doesn't exist!");
			return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
		}
		
		String token = dto.getPassword();
		
		try {
			String hash = SecurePasswordHasher.encode(token);
			
			if(hash.equals(u.getPassword()))
			{
				request.getSession(true).setAttribute("SESSION_USER", u);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		header.set("Response","Password incorrect!");
		return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(value = "/registerRequest",consumes = "application/json")
	public ResponseEntity<Void> requestRegistration(@RequestBody RegistrationRequest request)
	{
		RegistrationRequest req = authService.
				findByEmail(request.getEmail());
		
		User u = userService.findByEmail(request.getEmail());
		
		//Odbij request ukoliko postoji u bazi
		if(req != null || u != null)
		{
			return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
		}
		
		authService.save(new RegistrationRequest(request));
		return new ResponseEntity<>(HttpStatus.OK);
	}	
	
	@PostMapping(value = "/confirmRegister/{email}")
	public ResponseEntity<Void> register(@PathVariable("email") String email)
	{
		RegistrationRequest req = authService.
				findByEmail(email);
		
		if(req == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Patient patient = new Patient(req);
		String token = patient.getPassword();
		
		try {
			String hash = SecurePasswordHasher.encode(token);
			
			patient.setPassword(hash);
			userService.save(patient);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
		
	@GetMapping(value = "/sessionUser")
	public ResponseEntity<User> getSessionUser(HttpServletRequest request)
	{
		User user = (User) request.getSession().getAttribute("SESSION_USER");
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllRegRequest")
	public ResponseEntity<List<RegistrationRequest>> getRegRequests()
	{
		List<RegistrationRequest> ret = authService.getAll();
		
		return new ResponseEntity<>(ret,HttpStatus.OK);
	}
	
}
