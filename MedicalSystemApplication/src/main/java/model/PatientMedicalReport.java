package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import dto.PrescriptionDTO;

@Entity
public class PatientMedicalReport {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "description", nullable = true)
    private String description;
	
	@Column(name = "dateAndTime", nullable = true)
    private Date dateAndTime;

	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "prescription_id", nullable = true)
	private Prescription prescription;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<Diagnosis> diagnosis;

	@Version
	private Integer version;
	

	public PatientMedicalReport()
	{
		super();
		this.diagnosis = new ArrayList<>();

	}
	
	public PatientMedicalReport(String description, Date dateAndTime, Doctor doctor, Clinic clinic, Patient patient, Prescription prescription) {
		super();
		this.description = description;
		this.dateAndTime = dateAndTime;
		this.doctor = doctor;
		this.clinic = clinic;
		this.patient = patient;
		this.prescription = prescription;
		this.diagnosis = new ArrayList<>();
	}
	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Prescription getPrescription() {
		return prescription;
	}

	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}

	public Date getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public List<Diagnosis> getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(List<Diagnosis> diagnosis) {
		this.diagnosis = diagnosis;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public static class Builder{
	    private String description;
	    private Date dateAndTime;
	    private Doctor doctor;
	    private Clinic clinic;
		private Patient patient;
		private Prescription prescription;
		private List<Diagnosis> diagnosis;
			
		public Builder(Date dateAndTime)
		{
			this.dateAndTime = dateAndTime;	
		} 
		
		public Builder withDescription(String desc)
		{
			this.description = desc;
			
			return this;
		}
		
		public Builder withDoctor(Doctor d)
		{
			this.doctor = d;
			
			return this;
		}
		
		public Builder withClinic(Clinic c)
		{
			this.clinic = c;
			
			return this;
		}
		
		public Builder withPatient(Patient p)
		{
			this.patient = p;
			
			return this;
		}
		
		public Builder withPrescription(Prescription p)
		{
			this.prescription = p;
			
			return this;
		}
		
		public Builder withDiagnosis(List<Diagnosis> diag)
		{
			this.diagnosis = diag;
			
			return this;
		}
		
		public PatientMedicalReport build()
		{
			PatientMedicalReport rp = new PatientMedicalReport();
			rp.setClinic(this.clinic);
			rp.setDateAndTime(this.dateAndTime);
			rp.setDescription(this.description);
			rp.setDiagnosis(this.diagnosis);
			rp.setDoctor(this.doctor);
			rp.setPatient(this.patient);
			rp.setPrescription(this.prescription);
			return rp;
		}
	}		
}
