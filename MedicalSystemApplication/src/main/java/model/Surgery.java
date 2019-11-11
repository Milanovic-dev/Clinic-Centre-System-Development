package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Surgery extends Appointment
{
	private ArrayList<Doctor> doctors;

	
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


	public ArrayList<Doctor> getDoctors() {
		return doctors;
	}


	public void setDoctors(ArrayList<Doctor> doctors) {
		this.doctors = doctors;
	}


	
}
