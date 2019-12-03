package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

	@Component
	class predefinedAdminCreate {

		@Autowired
		private UserRepository userRepository;
		
		@Autowired
		private ClinicRepository clinicRepository;
		
		@Autowired
		private HallRepository hallRepository;

		@PostConstruct
		public void init() {

			String token = "admin1234";

			try {
				String hash = SecurePasswordHasher.encode(token);
				userRepository.save(new CentreAdmin("username", hash, "klinickicentartest@gmail.com", "Admin", "Adminic","Novi Sad","Trg Dositeja Obradovica 6", "Srbija", "011100100", true));
			
				hash = SecurePasswordHasher.encode("123");
				
			
				Doctor doctor = new Doctor("username",hash,"doktor1@gmail.com","Doktor","Doktor","Novi Sad","Karadjordjeva 8","Srbija","42423423");
				Doctor doctor2 = new Doctor("username",hash,"doktor2@gmail.com","Doktor","Doktor","Novi Sad","Karadjordjeva 8","Srbija","42423423");
				Doctor doctor3 = new Doctor("username",hash,"doktor3@gmail.com","Doktor","Doktor","Novi Sad","Karadjordjeva 8","Srbija","42423423");
				
				Patient patient = new Patient("username",hash,"nikola@gmail.com","Nikola","Milanovic","Novi Sad","Karadjorjdeva 8","Srbija","4123432");
				patient.getMedicalRecord().setBloodType(BloodType.AB);
				patient.getMedicalRecord().setAlergies(Arrays.asList("Kikiriki","Secer"));
				patient.getMedicalRecord().setHeight("195cm");
				patient.getMedicalRecord().setWeight("90kg");
				Clinic clinic = new Clinic("KlinikaTest","Karajdorjdeva 8","Novi Sad","Srbija","Opis");
				Hall hall1 = new Hall(clinic,1);
				Hall hall2 = new Hall(clinic,2);
				clinic.getHalls().add(hall1);
				clinic.getHalls().add(hall2);
				
				Nurse nurse = new Nurse("username",hash,"nurse@gmail.com","Sestra","Sestra","Novi Sad","Karadjordjeva 8","Srbija","42423423","");
				
				userRepository.save(nurse);
				userRepository.save(doctor);
				userRepository.save(doctor2);
				userRepository.save(doctor3);
				userRepository.save(patient);
				
				hallRepository.save(hall1);
				hallRepository.save(hall2);
				clinicRepository.save(clinic);
				
				userRepository.save(new ClinicAdmin("username",hash,"clinicAdmin@gmail.com","Admin","Admin","Novi Sad","Karadjordjeva 8","Srbija","2423423432",clinic));
				
			} catch (Exception e) {
				e.printStackTrace();
			}


			}
	}


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
