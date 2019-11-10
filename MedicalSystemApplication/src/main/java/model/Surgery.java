package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Surgery extends Appointment
{
	private Collection<Doctor> doctors;

	
	public Surgery() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Surgery(Date startingDateAndTime, long duration, double price, Hall hall,
			Patient patient, Clinic clinic) {
		super(startingDateAndTime, duration, price, hall, patient, clinic, AppointmentType.Surgery);
		doctors = new ArrayList<Doctor>();
	}

	public Collection<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(Collection<Doctor> doctors) {
		this.doctors = doctors;
	}
	
}
