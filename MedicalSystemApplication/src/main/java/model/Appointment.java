package model;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import javax.persistence.*;


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

	@ManyToMany(fetch = FetchType.LAZY)
	private List<Doctor> doctors;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "priceslist_id")
	private Priceslist priceslist;
	
	@Column(name = "appointmentType",nullable = true)
	private AppointmentType appointmentType;
	
	public Appointment() {
		super();
		this.doctors = new ArrayList<Doctor>();
		// TODO Auto-generated constructor stub
	}


	public Appointment(Date date, Hall hall, Patient patient, Clinic clinic, long duration,
			Priceslist priceslist, AppointmentType appointmentType) {
		super();
		this.date = date;
		this.hall = hall;
		this.patient = patient;
		this.clinic = clinic;
		this.duration = duration;
		this.doctors = new ArrayList<Doctor>();
		this.priceslist = priceslist;
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

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public Priceslist getPriceslist() {
		return priceslist;
	}

	public void setPricelist(Priceslist priceslist) {
		this.priceslist = priceslist;
	}

	public AppointmentType getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(AppointmentType appointmentType) {
		this.appointmentType = appointmentType;
	}

	public static class Builder
	{
		private Date date;
		private Hall hall;
		private Patient patient;
		private Clinic clinic;
		private Priceslist priceslist;
		private List<Doctor> doctors;
		private AppointmentType appointmentType;
		
		public Builder(Date date)
		{
			this.date = date;
			doctors = new ArrayList<Doctor>();
		}
		
		public Builder withHall(Hall hall)
		{
			this.hall = hall;
			
			return this;
		}
		
		public Builder withPatient(Patient patient)
		{
			this.patient = patient;
			
			return this;
		}
		
		public Builder withClinic(Clinic clinic)
		{
			this.clinic = clinic;
			
			return this;
		}
		
		
		public Builder withDoctors(ArrayList<Doctor> doctors)
		{
			this.doctors = doctors;
			
			return this;
		}
		
		public Builder withPriceslist(Priceslist priceslist)
		{
			this.priceslist = priceslist;
			
			return this;
		}
		
		public Builder withType(AppointmentType appointmentType)
		{
			this.appointmentType = appointmentType;
			
			return this;
		}
		
		public Appointment build()
		{
			Appointment app = new Appointment();
			app.setDate(this.date);
			app.setPatient(this.patient);
			app.setClinic(this.clinic);
			app.setHall(this.hall);
			app.setDoctors(this.doctors);
			app.setPricelist(priceslist);
			app.setAppointmentType(this.appointmentType);
			app.setAppointmentType(this.appointmentType);
			return app;
		}
		
	}
	
}
