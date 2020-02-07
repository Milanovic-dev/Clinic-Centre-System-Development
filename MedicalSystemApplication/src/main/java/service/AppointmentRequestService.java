package service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import model.Appointment;
import model.AppointmentRequest;
import model.Clinic;
import model.Hall;
import model.Patient;
import repository.AppointmentRequestRepository;
import repository.ClinicRepository;
import repository.HallRepository;
import repository.UserRepository;

@Service
public class AppointmentRequestService {

	@Autowired
	private AppointmentRequestRepository appointmentRequestRepository;
	
	@Autowired 
	private HallRepository hallRepository;
	
	@Autowired
	private ClinicRepository clinicRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<AppointmentRequest> getAllByClinic(String clinic)
	{
		Clinic c = clinicRepository.findByName(clinic);
		
		return appointmentRequestRepository.findAllByClinic(c);
	}
	
	public List<AppointmentRequest> getAllByPatient(Patient p)
	{
		return appointmentRequestRepository.findAllByPatient(p);
	}
	
	public AppointmentRequest findAppointmentRequest(Date date, Patient patient, Clinic clinic) 
	{
		return appointmentRequestRepository.findByDateAndPatientAndClinic(date, patient, clinic);
	}
	
	public AppointmentRequest findAppointmentRequest(Date date, Hall hall,Clinic clinic)
	{
		return appointmentRequestRepository.findByDateAndHallAndClinic(date, hall,clinic);
	}
	
	public AppointmentRequest findAppointmentRequest(String date, String patientEmail, String clinic)
	{
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
		try {
			Date d = df.parse(date);
			
			Patient p = (Patient) userRepository.findByEmailAndDeleted(patientEmail, false);
			
			Clinic c = clinicRepository.findByName(clinic);
			
			return findAppointmentRequest(d, p, c);
			
		} catch (Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public AppointmentRequest findAppointmentRequest(String date, int hallNumber,String clinic)
	{
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
		try {
			Date d = df.parse(date);
			
			Hall h = hallRepository.findByNumber(hallNumber);
			
			Clinic c = clinicRepository.findByName(clinic);
			
			return findAppointmentRequest(d,h,c);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void delete(AppointmentRequest request)
	{
		appointmentRequestRepository.delete(request);
	}
	
	public void save(AppointmentRequest request)
	{
		appointmentRequestRepository.save(request);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void saveLock(AppointmentRequest request) throws ConcurrentModificationException
	{
		AppointmentRequest req = findAppointmentRequest(request.getDate(), request.getHall(), request.getClinic());
		
		if(req != null)
		{
			throw new ConcurrentModificationException("Already made");
		}
		
		appointmentRequestRepository.save(request);
	}

	public List<AppointmentRequest> findAll(){
		return appointmentRequestRepository.findAll();
	}

	public List<AppointmentRequest> findAllSurgeries(){

		List<AppointmentRequest> apps = appointmentRequestRepository.findAll();
		List<AppointmentRequest> ret = new ArrayList<>();
		for(AppointmentRequest app : apps)
		{
			if(app.getAppointmentType() == Appointment.AppointmentType.Surgery){
				ret.add(app);
			}
		}

		return ret;
	}
}
