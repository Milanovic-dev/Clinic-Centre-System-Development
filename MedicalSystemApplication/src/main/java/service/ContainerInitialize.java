package service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.mail.Session;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import helpers.DateInterval;
import helpers.DateUtil;
import helpers.InvokeControl;
import helpers.InvokeFunction;
import helpers.Scheduler;
import helpers.SecurePasswordHasher;
import model.Appointment.AppointmentType;
import model.MedicalRecord.BloodType;
import model.User.UserRole;
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
	
	@Autowired
	private VacationRequestRepository vacationRequestRepository;

	@Autowired
	private VacationRepository vacationRepository;

	@Autowired
	private AppointmentRequestRepository appointmentRequestRepository;

	
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
					.withShiftStart(DateUtil.getInstance().getDate("08:00","HH:mm"))
					.withShiftEnd(DateUtil.getInstance().getDate("14:00","HH:mm"))
					.build();
			
			doctor1.setIsFirstLog(false);
			doctor1.setAvarageRating(3.98f);
			DateUtil dateInstance = DateUtil.getInstance();

			userRepository.save(doctor1);

			Vacation vac = new Vacation(dateInstance.getDate("04-05-2020", "dd-MM-yyyy"),dateInstance.getDate("15-05-2020", "dd-MM-yyyy"),doctor1);
			Vacation vac2 = new Vacation(dateInstance.getDate("05-07-2020", "dd-MM-yyyy"),dateInstance.getDate("25-07-2020", "dd-MM-yyyy"),doctor1);
			vacationRepository.save(vac);
			vacationRepository.save(vac2);
			doctor1.getVacations().add(vac);
			doctor1.getVacations().add(vac2);




			
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
					.withShiftStart(DateUtil.getInstance().getDate("09:00","HH:mm"))
					.withShiftEnd(DateUtil.getInstance().getDate("18:00","HH:mm"))
					.build();

			doctor2.setAvarageRating(4.4f);
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
					.withShiftStart(DateUtil.getInstance().getDate("08:00","HH:mm"))
					.withShiftEnd(DateUtil.getInstance().getDate("18:00","HH:mm"))
					.build();

			doctor3.setAvarageRating(2.0f);
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
			p1.setPrice(1000L);
			
			Priceslist p2 = new Priceslist();
			p2.setClinic(clinic);
			p2.setTypeOfExamination("Stomatoloski");
			p2.setPrice(1500L);
			
			pricelistRepository.save(p2);
			
			pricelistRepository.save(p1);
			Appointment app1 = new Appointment.Builder(DateUtil.getInstance().getDate("21-01-2020 07:30","dd-MM-yyyy HH:mm"))
					.withEndingDate(DateUtil.getInstance().getDate("21-01-2020 09:00","dd-MM-yyyy HH:mm"))
					.withType(AppointmentType.Examination)
					.withHall(hall1)
					.withClinic(clinic)
					.withDuration(2)
					.withPriceslist(p1)
					.build();
			
			app1.setPredefined(true);
			app1.getDoctors().add(doctor1);
			appointmentRepository.save(app1);


			Appointment app2 = new Appointment.Builder(DateUtil.getInstance().getDate("21-01-2020 10:30", "dd-MM-yyyy HH:mm"))
					.withEndingDate(DateUtil.getInstance().getDate("21-01-2020 13:00", "dd-MM-yyyy HH:mm"))
					.withPatient(patient1)
					.withType(AppointmentType.Surgery)
					.withHall(hall2)
					.withClinic(clinic)
					.withDuration(1)
					.withPriceslist(p2)
					.build();
			
			app2.getDoctors().add(doctor1);

			Appointment app3 = new Appointment.Builder(DateUtil.getInstance().getDate("18-03-2020 19:30", "dd-MM-yyyy HH:mm"))
					.withEndingDate(DateUtil.getInstance().getDate("18-03-2020 20:30", "dd-MM-yyyy HH:mm"))
					.withPatient(patient1)
					.withType(AppointmentType.Surgery)
					.withHall(hall2)
					.withClinic(clinic)
					.withDuration(1)
					.withPriceslist(p1)
					.build();
			

			Appointment app4 = new Appointment.Builder(DateUtil.getInstance().getDate("04-02-2020 12:00","dd-MM-yyyy HH:mm"))
					.withEndingDate(DateUtil.getInstance().getDate("04-02-2020 13:00","dd-MM-yyyy HH:mm"))
					.withType(AppointmentType.Examination)
					.withHall(hall1)
					.withClinic(clinic)
					.withDuration(1)
					.withPriceslist(p1)
					.build();
			
			app4.setPredefined(true);
			app4.getDoctors().add(doctor2);
			appointmentRepository.save(app4);
			
			app3.getDoctors().add(doctor3);
			appointmentRepository.save(app2);
			appointmentRepository.save(app3);
			
			
			doctor1.getAppointments().add(app1);
			doctor1.getAppointments().add(app2);
			doctor1.getAppointments().add(app3);
			doctor1.getAppointments().add(app4);
			//doctor2.getAppointments().add(app3);

			

			
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
			nurse.setClinic(clinic);
			userRepository.save(nurse);

			Vacation vac3 = new Vacation(dateInstance.getDate("04-05-2020", "dd-MM-yyyy"),dateInstance.getDate("15-05-2020", "dd-MM-yyyy"),nurse);
			Vacation vac4 = new Vacation(dateInstance.getDate("05-07-2020", "dd-MM-yyyy"),dateInstance.getDate("25-07-2020", "dd-MM-yyyy"),nurse);
			vacationRepository.save(vac3);
			vacationRepository.save(vac4);

			nurse.getVacations().add(vac3);
			nurse.getVacations().add(vac4);


			userRepository.save(doctor1);
			userRepository.save(doctor2);
			
			
		
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


			PatientMedicalReport report = new PatientMedicalReport();
			report.setDescription("Izvestaj lekara");
			report.setPatient(patient);
			report.setDoctor(doctor1);
			report.setPrescription(prescription);
			report.setClinic(clinic);
			report.getDiagnosis().add(diagnosis);

			report.setDateAndTime(dateInstance.getDate("05-01-2020 07:00","dd-MM-yyyy HH:mm"));
			patientMedicalReportRepository.save(report);
			patient.getMedicalRecord().getReports().add(report);

			PatientMedicalReport report1 = new PatientMedicalReport();
			report1.setDescription("Izvestaj drugog lekara");
			report1.setPatient(patient);
			report1.setDoctor(doctor2);
			report1.setPrescription(prescription2);
			report1.setClinic(clinic);
			report1.getDiagnosis().add(diagnosis3);
			report1.setDateAndTime(dateInstance.getDate("08-01-2020 08:00","dd-MM-yyyy HH:mm"));
			patientMedicalReportRepository.save(report1);
			patient.getMedicalRecord().getReports().add(report1);
			userRepository.save(patient);



			Diagnosis d1 = new Diagnosis("123","tag","name");
			List<Diagnosis> listDiag = new ArrayList<>();
			listDiag.add(d1);

			VacationRequest vrq = new VacationRequest(dateInstance.getDate("21-02-2020", "dd-MM-yyyy"),dateInstance.getDate("08-03-2020", "dd-MM-yyyy"),clinic,doctor3);
			vacationRequestRepository.save(vrq);
			
			VacationRequest vrq2 = new VacationRequest(dateInstance.getDate("26-05-2020", "dd-MM-yyyy"),dateInstance.getDate("15-06-2020", "dd-MM-yyyy"),clinic,doctor1);
			vacationRequestRepository.save(vrq2);



			AppointmentRequest appReq = new AppointmentRequest(dateInstance.getDate("26-02-2020 10:00", "dd-MM-yyyy HH:mm"), null, patient, clinic,p1, AppointmentType.Surgery);
			AppointmentRequest appReq1 = new AppointmentRequest(dateInstance.getDate("24-02-2020 11:00", "dd-MM-yyyy HH:mm"), null, patient, clinic,p1, AppointmentType.Examination);
			AppointmentRequest appReq2 = new AppointmentRequest(dateInstance.getDate("25-02-2020 11:00", "dd-MM-yyyy HH:mm"), null, patient, clinic,p1, AppointmentType.Surgery);
			AppointmentRequest appReq3 = new AppointmentRequest(dateInstance.getDate("26-02-2020 11:00", "dd-MM-yyyy HH:mm"), null, patient, clinic,p1, AppointmentType.Surgery);
			AppointmentRequest appReq4 = new AppointmentRequest(dateInstance.getDate("27-02-2020 11:00", "dd-MM-yyyy HH:mm"), null, patient, clinic,p1, AppointmentType.Surgery);
			AppointmentRequest appReq5 = new AppointmentRequest(dateInstance.getDate("28-02-2020 11:00", "dd-MM-yyyy HH:mm"), null, patient, clinic,p1, AppointmentType.Examination);

			List<Doctor> list = new ArrayList<Doctor>();
			list.add(doctor2);
			appReq5.setDoctors(list);
			appointmentRequestRepository.save(appReq);
			appointmentRequestRepository.save(appReq1);
			appointmentRequestRepository.save(appReq2);
			appointmentRequestRepository.save(appReq3);
			appointmentRequestRepository.save(appReq4);
			appointmentRequestRepository.save(appReq5);
			
			
			
			InvokeFunction func = ()-> 
			{ 
				
				DateUtil util = DateUtil.getInstance();
				Date currentDate = util.now("dd-MM-yyyy");
				
				List<AppointmentRequest> requests = appointmentRequestRepository.findAll();
				
				try {
					HashMap<Hall, DateInterval> map = getPossibleHallsAndTimes(requests.get(0));
					
					for(Hall h: map.keySet())
					{
						System.out.println(map.get(h));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*
				for(AppointmentRequest request : requests)
				{
					Date startTimestamp = request.getTimestamp();
					
					if(!util.isSameDay(startTimestamp, currentDate))
					{
						
					}
				}
				*/
			};
			
			
			//InvokeControl.InvokeRepeating(func, 10, TimeUnit.SECONDS);
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private HashMap<Hall,DateInterval> getPossibleHallsAndTimes(AppointmentRequest req) throws Exception
	{
		HashMap<Hall, DateInterval> ret = new HashMap<Hall, DateInterval>();
		Clinic clinic = req.getClinic();
		List<Hall> halls = req.getClinic().getHalls();
		
		if(halls.size() == 0) return null;

		LocalDateTime date = convertToLocalDate(req.getDate());
		Date temp = convertToDate(date);
		
		long twoDaysInMilisec = 1728 * 100000; 
		
		//while(DateUtil.getInstance().getTimeBetween(req.getDate(), temp) < twoDaysInMilisec)
		//{
			temp = convertToDate(date);
			
			for(Hall hall : halls)
			{
				List<Appointment> hallAppsThisDay = appointmentRepository.findAllByHallAndClinic(hall, clinic);		
				List<DateInterval> hallFreeIntervals = Scheduler.getFreeIntervals(hallAppsThisDay, temp);
				
				List<Doctor> doctors = getDoctorsByClinicAndType(clinic, req.getPriceslist().getTypeOfExamination());
							
				for(Doctor doc : doctors)
				{
					List<DateInterval> doctorBusyIntervals = Scheduler.getBusyIntervals(doc, temp);
					
					
					for(DateInterval di1 : hallFreeIntervals)
					{
						for(DateInterval di2 : doctorBusyIntervals)
						{
							if(!DateUtil.getInstance().overlappingInterval(di1, di2))
							{
								ret.put(hall, di1);
							}
						}
					}
				}
			}
			//date.plusDays(1);
		//}
		
		return ret;
	}
	
	private List<Doctor> getDoctorsByClinicAndType(Clinic clinic, String type)
	{
		List<User> userDoctors = userRepository.findAllByRole(UserRole.Doctor);
		List<Doctor> ret = new ArrayList<Doctor>();
		
		for(User u : userDoctors)
		{
			if(u instanceof Doctor)
			{
				Doctor doc = (Doctor) u;
				
				if(doc.getClinic().getName().equals(clinic.getName()))
				{
					if(doc.getType().equals(type))
					ret.add(doc);
				}
			}
		}
		
		return ret;
	}
	
	private LocalDateTime convertToLocalDate(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}
	
	private Date convertToDate(LocalDateTime dateToConvert) {
	    return java.util.Date
	      .from(dateToConvert.atZone(ZoneId.systemDefault())
	      .toInstant());
	}
	
}
