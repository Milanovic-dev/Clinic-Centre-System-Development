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
	
	@Column(name = "alergies", nullable = true)
	private String alergies;
	
	@Column(name = "height", nullable = true)
	private String weight;
	
	@Column(name = "weight", nullable = true)
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

	public List<PatientMedicalReport> getReports() {
		return reports;
	}

	public void setReports(List<PatientMedicalReport> reports) {
		this.reports = reports;
	}
	
	
}
