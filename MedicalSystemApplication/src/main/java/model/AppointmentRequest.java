package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import model.Appointment.AppointmentType;

@Entity
public class AppointmentRequest {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name= "startingDateAndTime",nullable = false)
	private Date date;
		
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Hall hall;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clinic_id")
	private Clinic clinic;
		
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Doctor> doctors;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "priceslist_id")
	private Priceslist priceslist;
	
	@Column(name = "appointmentType",nullable = true)
	private AppointmentType appointmentType;
	
	@Column(name = "timestamp")
	private Date timestamp;
	
	public AppointmentRequest()
	{
		super();
		this.doctors = new ArrayList<Doctor>();
	}
		
	public AppointmentRequest(Long id, Date date,Hall hall, Patient patient, Clinic clinic,
			Priceslist priceslist, AppointmentType appointmentType) {
		super();
		this.id = id;
		this.date = date;
		this.hall = hall;
		this.patient = patient;
		this.clinic = clinic;
		this.doctors = new ArrayList<Doctor>();
		this.priceslist = priceslist;
		this.appointmentType = appointmentType;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
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

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public Priceslist getPriceslist() {
		return priceslist;
	}

	public void setPriceslist(Priceslist priceslist) {
		this.priceslist = priceslist;
	}

	public AppointmentType getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(AppointmentType appointmentType) {
		this.appointmentType = appointmentType;
	}
	
	
	
}
