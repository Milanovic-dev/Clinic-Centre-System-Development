package controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.LoginDTO;
import dto.SessionUserDTO;
import dto.UserDTO;
import helpers.SecurePasswordHasher;
import model.Patient;
import model.RegistrationRequest;
import model.User;
import service.AuthService;
import service.NotificationService;
import service.UserService;

@RestController
@RequestMapping(value = "api/auth")
public class AuthController 
{
	@Autowired
	private AuthService authService;

	@Autowired
	private NotificationService notificationService;

	
	@Autowired
	private UserService userService;
	
	@PostMapping(value ="/login", consumes = "application/json")
	public ResponseEntity<SessionUserDTO> login(@RequestBody LoginDTO dto,HttpServletResponse response)
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
				response.addCookie(new Cookie("email",u.getEmail()));
				return new ResponseEntity<>(new SessionUserDTO(u),HttpStatus.OK);
			}
			
		} catch (Exception e) {
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
		
		if(req != null || u != null)
		{
			return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
		}
		
		authService.save(new RegistrationRequest(request));
		return new ResponseEntity<>(HttpStatus.OK);
	}	
	
	@PostMapping(value = "/confirmRegister/{email}")
	public ResponseEntity<Void> confirmRegister(@PathVariable("email") String email)
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
			notificationService.sendNotification(req.getEmail(), "Registracija Klinicki centar",
					"Postovani, Vas zahtev za registraciju naloga za Klinicki centar je prihvacen. Mozete se ulogovati u svoj nalog.");
			authService.delete(req);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}



		
	}
	
	@DeleteMapping(value ="/denyRegister/{reply}")
	public ResponseEntity<Void> denyRegistration(@PathVariable("reply") String reply)
	{

		String parts[]=reply.split(",", 2);

		String email=parts[0];
		String text = parts[1];

		RegistrationRequest req = authService.
				findByEmail(email);
		
		if(req == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		try{
			notificationService.sendNotification(req.getEmail(), "Registracija Klinicki centar",
					"Postovani, Vas zahtev za registraciju naloga za Klinicki centar je odbijen. Razlog odbijanja zahteva je sledeci: "+text);
			authService.delete(req);
		} catch (MailException e){

		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
		
	@GetMapping(value = "/sessionUser")
	public ResponseEntity<SessionUserDTO> getSessionUser(@CookieValue(value = "email", defaultValue = "none") String email)
	{
		if(email == null || email == "none")
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		
		User user = userService.findByEmail(email);
		
		if(user == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		SessionUserDTO dto = new SessionUserDTO(user);
		
		return new ResponseEntity<SessionUserDTO>(dto,HttpStatus.OK);
	}
		
	@PostMapping(value = "/logout")
	public ResponseEntity<Void> logout(HttpServletResponse response)
	{
		Cookie cookie = new Cookie("email",null);
		cookie.setMaxAge(0);
		
		response.addCookie(cookie);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllRegRequest")
	public ResponseEntity<List<RegistrationRequest>> getRegRequests()
	{
		List<RegistrationRequest> ret = authService.getAll();
		
		return new ResponseEntity<>(ret,HttpStatus.OK);
	}
	
}
