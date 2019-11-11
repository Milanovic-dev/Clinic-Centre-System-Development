package model;

import java.util.Date;



public class AppointmentRequest extends Appointment
{
	private Boolean approved;
	
	public AppointmentRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public AppointmentRequest(Date startingDateAndTime, long duration, double price, Hall hall, Patient patient,
			Clinic clinic, AppointmentType type, Boolean approved) {
		super(startingDateAndTime, duration, price, hall, patient, clinic, type);
		this.approved = approved;
	}


	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	
}
