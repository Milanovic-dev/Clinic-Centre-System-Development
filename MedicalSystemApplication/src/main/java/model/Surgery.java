package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;


@Entity
public class Surgery extends Appointment
{
	@ManyToMany
	private List<Doctor> doctors;

	
	public Surgery() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Surgery(Date startingDateAndTime, long duration, double price, Hall hall, Patient patient, Clinic clinic,
			AppointmentType type) {
		super(startingDateAndTime, duration, price, hall, patient, clinic, type);
		
		doctors = new ArrayList<Doctor>();
	}
	
	public Surgery(AppointmentRequest request)
	{
		super(request,AppointmentType.Surgery);
		doctors = new ArrayList<Doctor>();
	}


	public List<Doctor> getDoctors() {
		return doctors;
	}


	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}


	
}
