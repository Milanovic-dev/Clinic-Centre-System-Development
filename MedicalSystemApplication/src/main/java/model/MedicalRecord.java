package model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

@Entity
public class MedicalRecord {

	public enum BloodType{A, B, AB, O}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToMany(mappedBy = "medicalRecord", fetch = FetchType.LAZY)
	private ArrayList<PatientMedicalReport> reports;
	@Column(name = "bloodType", nullable = false)
	private BloodType bloodType;
	@Column(name = "alergies", nullable = false)
	private String alergies;
	@Column(name = "height", nullable = false)
	private String weight;
	@Column(name = "weight", nullable = false)
	private String height;
	
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


	public String getAlergies() {
		return alergies;
	}


	public void setAlergies(String alergies) {
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

	public ArrayList<PatientMedicalReport> getReports() {
		return reports;
	}

	public void setReports(ArrayList<PatientMedicalReport> reports) {
		this.reports = reports;
	}
	
	
}
