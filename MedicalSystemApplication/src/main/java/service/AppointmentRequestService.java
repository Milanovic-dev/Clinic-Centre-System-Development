package service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Appointment;
import model.AppointmentRequest;
import model.Hall;
import repository.AppointmentRequestRepository;
import repository.HallRepository;

@Service
public class AppointmentRequestService {

	@Autowired
	private AppointmentRequestRepository appointmentRequestRepository;
	
	@Autowired 
	private HallRepository hallRepository;
	
	
	public AppointmentRequest findByDateAndHall(Date date, Hall hall)
	{
		return appointmentRequestRepository.findByDateAndHall(date, hall);
	}
	
	public AppointmentRequest findByDateAndHall(String date, int hallNumber)
	{
		DateFormat df = new SimpleDateFormat();
		
		try {
			Date d = df.parse(date);
			
			Hall h = hallRepository.findByNumber(hallNumber);
			
			return findByDateAndHall(d,h);
			
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
