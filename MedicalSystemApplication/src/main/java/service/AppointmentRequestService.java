package service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Appointment;
import model.AppointmentRequest;
import model.Clinic;
import model.Hall;
import repository.AppointmentRequestRepository;
import repository.ClinicRepository;
import repository.HallRepository;

@Service
public class AppointmentRequestService {

	@Autowired
	private AppointmentRequestRepository appointmentRequestRepository;
	
	@Autowired 
	private HallRepository hallRepository;
	
	@Autowired
	private ClinicRepository clinicRepository;
	
	public AppointmentRequest findAppointmentRequest(Date date, Hall hall,Clinic clinic)
	{
		return appointmentRequestRepository.findByDateAndHallAndClinic(date, hall,clinic);
	}
	
	public AppointmentRequest findAppointmentRequest(String date, int hallNumber,String clinic)
	{
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
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
	
	
	public void save(AppointmentRequest request)
	{
		appointmentRequestRepository.save(request);
	}
}
