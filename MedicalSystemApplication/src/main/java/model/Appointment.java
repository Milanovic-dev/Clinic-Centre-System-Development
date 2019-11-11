package model;

import java.util.Date;

public abstract class Appointment 
{
	public enum AppointmentType{ Examination, Surgery }
	
	private Long id;
	private Date startingDateAndTime;
	private Hall hall;
	private Patient patient;
	private Clinic clinic;
	private long duration;
	private double price;
	private AppointmentType appointmentType;
	
	public Appointment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Appointment(Date startingDateAndTime, long duration, double price,
			Hall hall, Patient patient, Clinic clinic,AppointmentType type) {
		super();
		this.startingDateAndTime = startingDateAndTime;
		this.duration = duration;
		this.price = price;
		this.hall = hall;
		this.patient = patient;
		this.clinic = clinic;
		this.appointmentType = type;
	}
	
	public Appointment(AppointmentRequest request,AppointmentType type)
	{
		this.startingDateAndTime = request.getStartingDateAndTime();
		this.hall = request.getHall();
		this.patient = request.getPatient();
		this.clinic = request.getClinic();
		this.duration = request.getDuration();
		this.price = request.getPrice();
		this.appointmentType = type;
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


	public Long getId() {
		return id;
	}

	public Date getStartingDateAndTime() {
		return startingDateAndTime;
	}
	public void setStartingDateAndTime(Date startingDateAndTime) {
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
	
	public AppointmentType getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(AppointmentType appointmentType) {
		this.appointmentType = appointmentType;
	}

	@Override
	public String toString() {
		return "Appointment [appointmentID=" + id + ", startingDateAndTime=" + startingDateAndTime
				+ ", duration=" + duration + ", price=" + price + ", hall=" + hall + "]";
	}
	
	
	
}
