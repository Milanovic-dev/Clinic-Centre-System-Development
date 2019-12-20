package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
public class PatientMedicalReport {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "description", nullable = true)
    private String description;
	
	@Column(name = "dateAndTime", nullable = false)
    private Date dateAndTime;

	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;
	
	@ManyToMany
	@JoinTable(joinColumns = @JoinColumn(name = "patientMedicalReport_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "prescription_id", referencedColumnName = "id"))
	private List<Prescription> prescription;

	@ManyToMany
	@JoinTable(joinColumns = @JoinColumn(name = "patientMedicalReport_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "diagnosis_id", referencedColumnName = "id"))
	private List<Diagnosis> diagnosis;
	
	public PatientMedicalReport()
	{
		super();
		this.prescription = new ArrayList<>();
		this.diagnosis = new ArrayList<>();

	}
	
	public PatientMedicalReport(String description, Date dateAndTime, Doctor doctor, Clinic clinic) {
		super();
		this.description = description;
		this.dateAndTime = dateAndTime;
		this.doctor = doctor;
		this.clinic = clinic;
		this.prescription = new ArrayList<>();
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

	public List<Prescription> getPrescription() {
		return prescription;
	}

	public void setPrescription(List<Prescription> prescription) {
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
}
