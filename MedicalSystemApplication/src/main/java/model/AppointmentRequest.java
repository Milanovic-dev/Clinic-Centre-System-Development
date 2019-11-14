package model;

import java.util.Date;

import model.Appointment.AppointmentType;

import javax.persistence.*;

@Entity
public class AppointmentRequest
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "approved", nullable = false)
	private Boolean approved;

	@Column(name = "date", nullable = false)
	private Date startingDateAndTime;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hall_id", referencedColumnName = "id")
	private Hall hall;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id", referencedColumnName = "id")
	private Patient patient;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clinic_id", referencedColumnName = "id")
	private Clinic clinic;

	@Column(name = "duration", nullable = false)
	private long duration;

	@Column(name = "price", nullable = false)
	private double price;
	
	public AppointmentRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public AppointmentRequest(Date startingDateAndTime, Hall hall, Patient patient,long duration, double price,
			Clinic clinic, AppointmentType type) {
		super();
		this.startingDateAndTime = startingDateAndTime;
		this.hall = hall;
		this.patient = patient;
		this.clinic = clinic;
		this.duration = duration;
		this.price = price;
	}


	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}


	public Date getStartingDateAndTime() {
		return startingDateAndTime;
	}


	public void setStartingDateAndTime(Date startingDateAndTime) {
		this.startingDateAndTime = startingDateAndTime;
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


	public Clinic getClinic() {
		return clinic;
	}


	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	
	
	
}
