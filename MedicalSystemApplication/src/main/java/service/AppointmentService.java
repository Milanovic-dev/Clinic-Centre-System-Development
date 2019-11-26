package service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Appointment;
import model.Hall;
import model.Patient;
import model.User;
import repository.AppointmentRepository;
import repository.AppointmentRequestRepository;
import repository.HallRepository;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired 
	private HallRepository hallRepository;
	
	
	public Appointment findByDateAndHall(Date date, Hall hall)
	{
		return appointmentRepository.findByDateAndHall(date, hall);
	}
	
	public Appointment findByDateAndHall(String date, int hallNumber)
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
	
	public void save(Appointment appointment)
	{
		appointmentRepository.save(appointment);
	}
	
	public List<Appointment> findAllByPatient(Patient p)
	{
		return appointmentRepository.findAllByPatient(p);
	}
}
