package service;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import helpers.SecurePasswordHasher;
import model.Appointment;
import model.CentreAdmin;
import model.Clinic;
import model.ClinicAdmin;
import model.Doctor;
import model.Hall;
import model.Nurse;
import model.Patient;
import model.Appointment.AppointmentType;
import model.MedicalRecord.BloodType;
import repository.AppointmentRepository;
import repository.ClinicRepository;
import repository.HallRepository;
import repository.UserRepository;

@Component
public class ContainerInitialize {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ClinicRepository clinicRepository;
	
	@Autowired
	private HallRepository hallRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@PostConstruct
	public void init()
	{
		String token = "admin1234";
		String hash;
		try {
			hash = SecurePasswordHasher.getInstance().encode(token);
			userRepository.save(new CentreAdmin(hash, "klinickicentartest@gmail.com", "Admin", "Adminic","Novi Sad","Trg Dositeja Obradovica 6", "Srbija", "011100100", true));
			
			hash = SecurePasswordHasher.getInstance().encode("123");
						
			Patient patient = new Patient.Builder("nikola@gmail.com")
					.withPassword(hash)
					.withName("Nikola")
					.withSurname("Milanovic")
					.withCity("Novi Sad")
					.withAddress("Karadjordjeva 8")
					.withState("Srbija")
					.withPhone("34023423")	
					.withInsuranceID("13858342343")
					.build();
			
			patient.getMedicalRecord().setBloodType(BloodType.AB);
			patient.getMedicalRecord().setAlergies(Arrays.asList("Polen","Secer"));
			patient.getMedicalRecord().setHeight("195cm");
			patient.getMedicalRecord().setWeight("90kg");

			Patient patient2 = new Patient.Builder("patient1@gmail.com")
					.withPassword(hash)
					.withName("Pera")
					.withSurname("Peric")
					.withCity("Beograd")
					.withAddress("Bulevar Oslobodjenja 8")
					.withState("Srbija")
					.withPhone("123456")
					.withInsuranceID("78945612364")
					.build();

			Patient patient1 = new Patient.Builder("patient@gmail.com")
					.withPassword(hash)
					.withName("Sima")
					.withSurname("Simic")
					.withCity("Zagreb")
					.withAddress("Ulcia 8")
					.withState("Hrvatska")
					.withPhone("44555656")
					.withInsuranceID("35654645")
					.build();
			
			userRepository.save(patient);
			userRepository.save(patient1);
			userRepository.save(patient2);
			
			Clinic clinic = new Clinic("KlinikaTest","Karajdorjdeva 8","Novi Sad","Srbija","Opis");
						
			clinicRepository.save(clinic);
					
			ClinicAdmin clinicAdmin = new ClinicAdmin.Builder("clinicAdmin@gmail.com")
					.withPassword(hash)
					.withName("ClinicAdmin1")
					.withSurname("ClinicAdmin")
					.withCity("Novi Sad")
					.withAddress("Karadjordjeva 8")
					.withState("Srbija")
					.withPhone("42332423")
					.withClinic(clinic)
					.build();
			
			userRepository.save(clinicAdmin);


			Doctor doctor1 = new Doctor.Builder("doktor1@gmail.com")
					.withPassword(hash)
					.withName("Doktor1")
					.withSurname("Doktor")
					.withCity("Novi Sad")
					.withAddress("Kisacka")
					.withState("Srbija")
					.withPhone("5435435")
					.withInsuranceID("")
					.withType("Zubar")
					.withClinic(clinic)
					.withShiftStart(new Date())
					.withShiftEnd(new Date())
					.build();

			userRepository.save(doctor1);
			
			Doctor doctor2 = new Doctor.Builder("doktor2@gmail.com")
					.withPassword(hash)
					.withName("Doktor2")
					.withSurname("Doktor")
					.withCity("Novi Sad")
					.withAddress("Kisacka")
					.withState("Srbija")
					.withPhone("5435435")
					.withInsuranceID("")
					.withType("Zubar")
					.withClinic(clinic)
					.withShiftStart(new Date())
					.withShiftEnd(new Date())
					.build();

			userRepository.save(doctor2);
			
			Doctor doctor3 = new Doctor.Builder("doktor3@gmail.com")
					.withPassword(hash)
					.withName("Doktor3")
					.withSurname("Doktor")
					.withCity("Novi Sad")
					.withAddress("Kisacka")
					.withState("Srbija")
					.withPhone("5435435")
					.withInsuranceID("")
					.withType("Zubar")
					.withClinic(clinic)
					.withShiftStart(new Date())
					.withShiftEnd(new Date())
					.build();

			userRepository.save(doctor3);
			
			Hall hall1 = new Hall(clinic,1);
			Hall hall2 = new Hall(clinic,2);			
			hallRepository.save(hall1);
			hallRepository.save(hall2);
			
			clinic.getHalls().add(hall1);
			clinic.getHalls().add(hall2);
			clinic.getDoctors().add(doctor1);
			clinic.getDoctors().add(doctor2);
			clinic.getDoctors().add(doctor3);
			clinicRepository.save(clinic);
			
			Date date = new Date();

			Appointment app1 = new Appointment.Builder(date)
					.withPatient(patient)
					.withType(AppointmentType.Surgery)
					.withHall(hall1)
					.withClinic(clinic)
					.withPrice(12345)
					.withDescription("Opis operacije1")
					.build();

			appointmentRepository.save(app1);


			Appointment app2 = new Appointment.Builder(date)
					.withPatient(patient)
					.withType(AppointmentType.Surgery)
					.withHall(hall2)
					.withClinic(clinic)
					.withPrice(13445)
					.withDescription("Opis operacije2")
					.build();
			appointmentRepository.save(app2);

			doctor1.getAppointments().add(app1);
			doctor1.getAppointments().add(app2);
			userRepository.save(doctor1);

			Nurse nurse = new Nurse.Builder("nurse@gmail.com")
					.withPassword(hash)
					.withName("Sestra1")
					.withSurname("Sestra")
					.withCity("Novi Sad")
					.withAddress("Bulevar")
					.withState("Srbija")
					.withPhone("3432423")
					.withInsuranceID("")
					.withShiftStart(new Date())
					.withShiftEnd(new Date())
					.build();
			
			userRepository.save(nurse);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
