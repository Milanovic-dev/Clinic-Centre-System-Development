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
import javax.persistence.OneToOne;

@Entity
public class SurgeryRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "approved", nullable = false)
	private Boolean approved;

	@Column(name = "date", nullable = false)
	private Date date;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hall_id", referencedColumnName = "id")
	private Hall hall;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id", referencedColumnName = "id")
	private Patient patient;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clinic_id", referencedColumnName = "id")
	private Clinic clinic;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Doctor> doctors;

	@Column(name = "duration", nullable = false)
	private long duration;

	@Column(name = "price", nullable = false)
	private double price;

	public SurgeryRequest()
	{
		super();
		this.doctors = new ArrayList<Doctor>();
	}
	
	public SurgeryRequest(Long id, Boolean approved, Date date, Hall hall, Patient patient, Clinic clinic,
			 long duration, double price) {
		super();
		this.id = id;
		this.approved = approved;
		this.date = date;
		this.hall = hall;
		this.patient = patient;
		this.clinic = clinic;
		this.duration = duration;
		this.price = price;
		this.doctors = new ArrayList<Doctor>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
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

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
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
	
	
		
}
