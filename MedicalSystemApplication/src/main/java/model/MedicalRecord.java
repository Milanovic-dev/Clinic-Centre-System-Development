package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class MedicalRecord {

	public enum BloodType{A, B, AB, O}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(fetch = FetchType.LAZY)
	private List<PatientMedicalReport> reports;
	
	@Column(name = "bloodType", nullable = true)
	private BloodType bloodType;
	
	@ElementCollection
	@CollectionTable(name="Alergies", joinColumns=@JoinColumn(name="user_id"))
	@Column(name="alergie")
	private List<String> alergies;
	
	@Column(name = "height", nullable = true)
	private String weight;
	
	@Column(name = "weight", nullable = true)
	private String height;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
	public MedicalRecord()
	{
		super();
		reports = new ArrayList<PatientMedicalReport>();
	}
	
		
	public BloodType getBloodType() {
		return bloodType;
	}


	public void setBloodType(BloodType bloodType) {
		this.bloodType = bloodType;
	}


	public List<String> getAlergies() {
		return alergies;
	}


	public void setAlergies(List<String> alergies) {
		this.alergies = alergies;
	}

	public String getWeight() {
		return weight;
	}



	public void setWeight(String weight) {
		this.weight = weight;
	}



	public String getHeight() {
		return height;
	}



	public void setHeight(String height) {
		this.height = height;
	}



	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public List<PatientMedicalReport> getReports() {
		return reports;
	}

	public void setReports(List<PatientMedicalReport> reports) {
		this.reports = reports;
	}
	
	public void addReport(PatientMedicalReport report){
		if(this.reports == null){
			reports = new ArrayList<>();
			reports.add(report);
		}
	}
}
