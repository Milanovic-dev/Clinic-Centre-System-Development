package model;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


@Entity
public class Appointment 
{
	public enum AppointmentType{ Examination, Surgery }
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name= "startingDateAndTime",nullable = false)
	private Date date;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Hall hall;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clinic_id")
	private Clinic clinic;
	
	@Column(name = "duration",nullable = true)
	private long duration;
	
	@Column(name = "price",nullable = true)
	private double price;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Doctor> doctors;
	
	@Column(name = "type", nullable = false)
	private String appointmentDescription;
	
	@Column(name = "appointmentType",nullable = true)
	private AppointmentType appointmentType;
	
	public Appointment() {
		super();
		this.doctors = new ArrayList<Doctor>();
		// TODO Auto-generated constructor stub
	}
	
		
	public Appointment(Long id, Date date, Hall hall, Patient patient, Clinic clinic, long duration, double price,
			String appointmentDescription, AppointmentType appointmentType) {
		super();
		this.id = id;
		this.date = date;
		this.hall = hall;
		this.patient = patient;
		this.clinic = clinic;
		this.duration = duration;
		this.price = price;
		this.doctors = new ArrayList<Doctor>();
		this.appointmentDescription = appointmentDescription;
		this.appointmentType = appointmentType;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
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

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public String getAppointmentDescription() {
		return appointmentDescription;
	}

	public void setAppointmentDescription(String appointmentDescription) {
		this.appointmentDescription = appointmentDescription;
	}

	public AppointmentType getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(AppointmentType appointmentType) {
		this.appointmentType = appointmentType;
	}

	@Override
	public String toString() {
		return "Appointment [appointmentID=" + id + ", startingDateAndTime=" + date
				+ ", duration=" + duration + ", price=" + price + ", hall=" + hall + "]";
	}
	
	
	
}
