package service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import helpers.DateUtil;
import model.Appointment;
import model.Clinic;
import model.Doctor;
import model.Hall;
import model.Patient;
import model.Priceslist;
import model.User;
import repository.AppointmentRepository;
import repository.AppointmentRequestRepository;
import repository.ClinicRepository;
import repository.HallRepository;

@Service
@EnableTransactionManagement
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired 
	private HallRepository hallRepository;
	
	@Autowired
	private ClinicRepository clinicRepository;
	
	public Appointment findAppointment(Date date, Hall hall, Clinic clinic)
	{
		
		return appointmentRepository.findByDateAndHallAndClinic(date, hall, clinic);
	}
	
	public List<Appointment> findAllByHallAndClinic(Hall hall,Clinic clinic)
	{
		return appointmentRepository.findAllByHallAndClinic(hall,clinic);
	}
	
	public List<Appointment> findAll()
	{
		return appointmentRepository.findAll();
	}
	
	public List<Appointment> findAllByPredefined()
	{
		return appointmentRepository.findAllByPredefined(true);
	}
	
	public List<Appointment> findAllByPricesList(Priceslist pl)
	{
		return appointmentRepository.findAllByPriceslist(pl);
	}

	public Appointment findAppointment(String date, int hallNumber, String clinic)
	{	
		Date d = DateUtil.getInstance().getDate(date, "dd-MM-yyyy HH:mm");
			
		Hall h = hallRepository.findByNumber(hallNumber);
		Clinic c = clinicRepository.findByName(clinic);
			
		return findAppointment(d,h,c);
	}
	

	public void save(Appointment appointment)
	{	
		appointmentRepository.save(appointment);
	}
	
	@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void saveLock(Appointment appointment) throws ObjectOptimisticLockingFailureException
	{
		Appointment app = appointmentRepository.findByDateAndHallAndClinic(appointment.getDate(), appointment.getHall(), appointment.getClinic());
		
		if(app != null)
		{
			if(app.getVersion() != appointment.getVersion())
			{
				throw new ObjectOptimisticLockingFailureException("Resource Locked.", app);
			}
		}
		
		appointmentRepository.save(appointment);
	}
	
	public List<Appointment> findAllByPatient(Patient p)
	{
		return appointmentRepository.findAllByPatient(p);
	}
	
	public List<Appointment> findAllByDoctor(Long doctorID)
	{
		return appointmentRepository.findAllByDoctor(doctorID);
	}
	
	public List<Appointment> findAllByDoctorAndPatient(Doctor d,Patient p)
	{
		List<Appointment> apps = appointmentRepository.findAllByClinic(d.getClinic());
		List<Appointment> ret = new ArrayList<Appointment>();
		for(Appointment app : apps)
		{
			List<Doctor> doctors = app.getDoctors();
			
			if(app.getPatient() == null )
			{
				continue;
			}
			
			if(!app.getPatient().getEmail().equalsIgnoreCase(p.getEmail()))
			{
				continue;
			}

			for(Doctor doc : doctors)
			{
				if(doc.getEmail().equalsIgnoreCase(d.getEmail()))
				{
					ret.add(app);
				}
			}
		}
		
		return ret;
	}
	
	public List<Appointment> findAllByDoctor(Doctor d)
	{
		List<Appointment> apps = appointmentRepository.findAllByClinic(d.getClinic());
		List<Appointment> ret = new ArrayList<Appointment>();
		for(Appointment app : apps)
		{
			List<Doctor> doctors = app.getDoctors();
			
			for(Doctor doc : doctors)
			{
				if(doc.getEmail().equals(d.getEmail()))
				{
					ret.add(app);
				}
			}
		}
		
		return ret;
	}
	
	public List<Appointment> findAllByHall(Hall hall)
	{
		return appointmentRepository.findAllByHall(hall);
	}
	
	public List<Appointment> findAllByDate(Date date)
	{
		return appointmentRepository.findAllByDate(date);
	}
	
	public List<Appointment> findAllByClinic(Clinic c)
	{
		return appointmentRepository.findAllByClinic(c);
	}


}
