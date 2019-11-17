package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.*;


@Entity
public class Patient extends User{
	
	@Column(name = "insuranceId", nullable = true)
	private String insuranceId;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "medicalRecord_id", referencedColumnName = "id")
	private MedicalRecord medicalRecord;
	

	public Patient()
	{
		super();
	}
	
	public Patient(String username, String password, String email, String name, String surname, String city,
			String address, String state, String phone) {
		super(username, password, email, name, surname, city, address, state, phone, UserRole.Patient);
		medicalRecord = new MedicalRecord();
	}
	
	public Patient(RegistrationRequest request)
	{
		super(request,UserRole.Patient);
		medicalRecord = new MedicalRecord();
	}

	public String getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}
	
}
