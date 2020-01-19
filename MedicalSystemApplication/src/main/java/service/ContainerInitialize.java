package service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import helpers.DateUtil;
import helpers.SecurePasswordHasher;
import model.Appointment.AppointmentType;
import model.MedicalRecord.BloodType;
import repository.*;

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
	
	@Autowired
	private PriceListRepository pricelistRepository;

	@Autowired
	private DrugRepository drugRepository;

	@Autowired
	private DiagnosisRepository diagnosisRepository;

	@Autowired
	private PrescriptionRepository prescriptionRepository;

	@Autowired
	private PatientMedicalReportRepository patientMedicalReportRepository;

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;
	
	@PostConstruct
	public void init()
	{	
		String token = "admin1234";
		String hash;
		try {
			hash = SecurePasswordHasher.getInstance().encode(token);
			userRepository.save(new CentreAdmin(hash, "adminCentra@gmail.com", "Steva", "Stevic","Novi Sad","Trg Dositeja Obradovica 6", "Srbija", "011100100", true));
			
			hash = SecurePasswordHasher.getInstance().encode("123");

			Drug drug = new Drug("paracetamol", "123");
			drugRepository.save(drug);
			Drug drug2 = new Drug("kafetin", "456");
			drugRepository.save(drug2);
			Drug drug3 = new Drug("analgin", "789");
			drugRepository.save(drug3);

			Diagnosis diagnosis = new Diagnosis("333", "1234", "prehlada");
			diagnosisRepository.save(diagnosis);
			Diagnosis diagnosis2 = new Diagnosis("111", "6565", "kasalj");
			diagnosisRepository.save(diagnosis2);
			Diagnosis diagnosis3 = new Diagnosis("222", "787", "grip");
			diagnosisRepository.save(diagnosis3);

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
			patient.getMedicalRecord().setWeight("85kg");
			patient.getMedicalRecord().setPatient(patient);
					

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


			patient1.getMedicalRecord().setPatient(patient1);

			userRepository.save(patient);
			userRepository.save(patient1);
			userRepository.save(patient2);
			
			Clinic clinic = new Clinic("KlinikaA","Bulevar Osl. 10","Novi Sad","Srbija","Opis");
			Clinic clinic2 = new Clinic("KlinikaB","Kisacka 5","Beogard","Srbija","Opis");
				
			clinicRepository.save(clinic);
			clinicRepository.save(clinic2);
			
			ClinicAdmin clinicAdmin = new ClinicAdmin.Builder("adminKlinike@gmail.com")
					.withPassword(hash)
					.withName("Marko")
					.withSurname("Markovic")
					.withCity("Novi Sad")
					.withAddress("Kisacka")
					.withState("Srbija")
					.withPhone("42332423")
					.withClinic(clinic)					
					.build();
			
			clinicAdmin.setIsFirstLog(false);
			userRepository.save(clinicAdmin);


			Doctor doctor1 = new Doctor.Builder("doktor1@gmail.com")
					.withPassword(hash)
					.withName("Steva")
					.withSurname("Stevic")
					.withCity("Novi Sad")
					.withAddress("Kisacka")
					.withState("Srbija")
					.withPhone("5435435")
					.withInsuranceID("123484654324")
					.withType("Stomatoloski")
					.withClinic(clinic)					
					.withShiftStart(DateUtil.getInstance().GetDate("08:00","HH:mm"))
					.withShiftEnd(DateUtil.getInstance().GetDate("14:00","HH:mm"))
					.build();
			
			doctor1.setIsFirstLog(false);
			doctor1.setAvarageRating(8.81f);
			userRepository.save(doctor1);
			
			Doctor doctor2 = new Doctor.Builder("doktor2@gmail.com")
					.withPassword(hash)
					.withName("Nikola")
					.withSurname("Nikolic")
					.withCity("Novi Sad")
					.withAddress("Kisacka")
					.withState("Srbija")
					.withPhone("5435435")
					.withInsuranceID("12345674864")
					.withType("Opsti pregled")
					.withClinic(clinic)					
					.withShiftStart(DateUtil.getInstance().GetDate("09:00","HH:mm"))
					.withShiftEnd(DateUtil.getInstance().GetDate("18:00","HH:mm"))
					.build();

			doctor2.setAvarageRating(6.4f);
			userRepository.save(doctor2);
			
			Doctor doctor3 = new Doctor.Builder("doktor3@gmail.com")
					.withPassword(hash)
					.withName("Petar")
					.withSurname("Pertrovic")
					.withCity("Novi Sad")
					.withAddress("Kisacka")
					.withState("Srbija")
					.withPhone("5435435")
					.withInsuranceID("1897654856456")
					.withType("Opsti pregled")
					.withClinic(clinic)
					.withShiftStart(DateUtil.getInstance().GetDate("08:00","HH:mm"))
					.withShiftEnd(DateUtil.getInstance().GetDate("18:00","HH:mm"))
					.build();

			doctor3.setAvarageRating(9.91f);
			userRepository.save(doctor3);
			
			Hall hall1 = new Hall(clinic,1,"Prva sala");
			Hall hall2 = new Hall(clinic,2,"Druga sala");			
			hallRepository.save(hall1);
			hallRepository.save(hall2);
			
			clinic.getHalls().add(hall1);
			clinic.getHalls().add(hall2);
			clinic.getDoctors().add(doctor1);
			clinic.getDoctors().add(doctor2);
			clinic.getDoctors().add(doctor3);
			clinicRepository.save(clinic);

			Priceslist p1 = new Priceslist();
			p1.setClinic(clinic);
			p1.setTypeOfExamination("Opsti pregled");
			p1.setPrice(500L);
			
			pricelistRepository.save(p1);

			Appointment app1 = new Appointment.Builder(DateUtil.getInstance().GetDate("15-01-2020 07:30","dd-mm-yyyy HH:mm"))
					.withType(AppointmentType.Examination)
					.withHall(hall1)
					.withClinic(clinic)
					.withDuration(2)
					.withPriceslist(p1)
					.build();
			
			app1.setPredefined(true);
			app1.getDoctors().add(doctor1);
			app1.getDoctors().add(doctor2);
			appointmentRepository.save(app1);

			Appointment app2 = new Appointment.Builder(DateUtil.getInstance().GetDate("01-01-2020 18:30", "dd-mm-yyyy HH:mm"))
					.withPatient(patient1)
					.withType(AppointmentType.Surgery)
					.withHall(hall2)
					.withClinic(clinic)
					.withDuration(1)
					.build();

			Appointment app3 = new Appointment.Builder(DateUtil.getInstance().GetDate("03-01-2020 19:30", "dd-mm-yyyy HH:mm"))
					.withPatient(patient1)
					.withType(AppointmentType.Surgery)
					.withHall(hall2)
					.withClinic(clinic)
					.withDuration(1)
					.build();
			
			app2.getDoctors().add(doctor1);
			app2.getDoctors().add(doctor3);
			appointmentRepository.save(app2);
			appointmentRepository.save(app3);
			
			
			doctor1.getAppointments().add(app1);
			doctor1.getAppointments().add(app2);
			doctor2.getAppointments().add(app3);
			

			
			Nurse nurse = new Nurse.Builder("nurse@gmail.com")
					.withPassword(hash)
					.withName("Sestra")
					.withSurname("Sestra")
					.withCity("Novi Sad")
					.withAddress("Bulevar")
					.withState("Srbija")
					.withPhone("3432423")
					.withInsuranceID("")
					.withShiftStart(new Date())
					.withShiftEnd(new Date())
					.build();
			
			nurse.setIsFirstLog(false);
			userRepository.save(nurse);

			userRepository.save(doctor1);
			userRepository.save(doctor2);
			
			
			Priceslist p2 = new Priceslist();
			p2.setClinic(clinic);
			p2.setTypeOfExamination("Stomatoloski");
			p2.setPrice(1000L);
			
			pricelistRepository.save(p2);

			Prescription prescription = new Prescription();
			prescription.getDrugs().add(drug);
			prescription.getDrugs().add(drug2);
			prescription.setDescription("terapija");

			prescriptionRepository.save(prescription);

			Prescription prescription2 = new Prescription();
			prescription2.getDrugs().add(drug3);
			prescription2.getDrugs().add(drug2);
			prescription2.setDescription("terapija 12345");

			prescriptionRepository.save(prescription2);

			Prescription prescription3 = new Prescription();
			prescription3.getDrugs().add(drug3);
			prescription3.getDrugs().add(drug);
			prescription3.setDescription("terapijaaaaa a a a a a ");

			prescriptionRepository.save(prescription3);
			
			Diagnosis d1 = new Diagnosis("123","tag","name");
			List<Diagnosis> listDiag = new ArrayList<>();
			listDiag.add(d1);
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
