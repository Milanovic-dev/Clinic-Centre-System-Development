package model;

import java.util.Collection;

public class Surgery extends Appointment
{
	private Collection<Doctor> doctors;

	public Collection<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(Collection<Doctor> doctors) {
		this.doctors = doctors;
	}
	
}
