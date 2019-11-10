package model;

public class Appointment 
{
	private String appointmentID;
	private long startingDateAndTime;
	private long duration;
	private double price;
	private Hall hall;
	private Patient patient;
	private Clinic clinic;
	
	
	public Appointment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Appointment(String appointmentID, long startingDateAndTime, long duration, double price,
			Hall hall, Patient patient, Clinic clinic) {
		super();
		this.appointmentID = appointmentID;
		this.startingDateAndTime = startingDateAndTime;
		this.duration = duration;
		this.price = price;
		this.hall = hall;
		this.patient = patient;
		this.clinic = clinic;
	}



	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public Patient getPatient() {
		return patient;
	}


	public void setPatient(Patient patient) {
		this.patient = patient;
	}


	public String getAppointmentID() {
		return appointmentID;
	}
	public void setAppointmentID(String appointmentID) {
		this.appointmentID = appointmentID;
	}
	public long getStartingDateAndTime() {
		return startingDateAndTime;
	}
	public void setStartingDateAndTime(long startingDateAndTime) {
		this.startingDateAndTime = startingDateAndTime;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Hall getHall() {
		return hall;
	}
	public void setHall(Hall hall) {
		this.hall = hall;
	}


	@Override
	public String toString() {
		return "Appointment [appointmentID=" + appointmentID + ", startingDateAndTime=" + startingDateAndTime
				+ ", duration=" + duration + ", price=" + price + ", hall=" + hall + "]";
	}
	
	
	
}
