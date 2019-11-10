package model;

public class Surgery 
{
	public Doctor doctor;
	private String appointmentID;
	private long startingDate;
	private long startingTime;
	private long duration;
	private double price;
	private Hall hall;
	private Clinic clinic;
	private Patient patient;
	
	
	public Surgery() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Surgery(Doctor doctor, String appointmentID, long startingDate, long startingTime, long duration,
			double price, Hall hall, Clinic clinic, Patient patient) {
		super();
		this.doctor = doctor;
		this.appointmentID = appointmentID;
		this.startingDate = startingDate;
		this.startingTime = startingTime;
		this.duration = duration;
		this.price = price;
		this.hall = hall;
		this.clinic = clinic;
		this.patient = patient;
	}

	public Clinic getClinic() {
		return clinic;
	}
	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public String getAppointmentID() {
		return appointmentID;
	}
	public void setAppointmentID(String appointmentID) {
		this.appointmentID = appointmentID;
	}
	
	public long getStartingDate() {
		return startingDate;
	}
	public void setStartingDate(long startingDate) {
		this.startingDate = startingDate;
	}
	public long getStartingTime() {
		return startingTime;
	}
	public void setStartingTime(long startingTime) {
		this.startingTime = startingTime;
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
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public String toString() {
		return "Surgery [doctor=" + doctor + ", appointmentID=" + appointmentID + ", startingDate=" + startingDate
				+ ", startingTime=" + startingTime + ", duration=" + duration + ", price=" + price + ", hall=" + hall
				+ ", patient=" + patient + "]";
	}
	
	
	
}
