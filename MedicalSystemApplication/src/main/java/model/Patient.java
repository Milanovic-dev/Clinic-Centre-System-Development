package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GenerationType;

import helpers.UserBuilder;
import model.User.UserRole;

import javax.persistence.*;


@Entity
public class Patient extends User{
	
	@Column(name = "insuranceId", nullable = true)
	private String insuranceId;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "medicalRecord_id", referencedColumnName = "id")
	private MedicalRecord medicalRecord;
	

	public Patient()
	{
		super();
	}
	
	public Patient(String password, String email, String name, String surname, String city,
			String address, String state, String phone) {
		super(password, email, name, surname, city, address, state, phone, UserRole.Patient);
		medicalRecord = new MedicalRecord();
		this.setIsFirstLog(false);
	}
	
	public Patient(RegistrationRequest request)
	{
		super(request,UserRole.Patient);
		medicalRecord = new MedicalRecord();
		this.setIsFirstLog(false);
	}
	
	public Patient(User user)
	{
		super(user);
		medicalRecord = new MedicalRecord();
		this.setIsFirstLog(false);
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
	
	public static class Builder extends UserBuilder
	{
		private String insuranceId;
		
		public Builder(String email)
		{
			super(email);
		}
		

		public Builder withPassword(String password)
		{
			super.withPassword(password);
			
			return this;
		}

		public Builder withName(String name)
		{
			super.withName(name);
			
			return this;
		}
	
		public Builder withSurname(String surname)
		{
			super.withSurname(surname);
			
			return this;
		}
		
		public Builder withCity(String city)
		{
			super.withCity(city);
			
			return this;
		}
		
		public Builder withAddress(String address)
		{
			super.withAddress(address);
			
			return this;
		}
		
		public Builder withState(String state)
		{
			super.withState(state);
			
			return this;
		}
		
		public Builder withPhone(String phone)
		{
			super.withPhone(phone);
			
			return this;
		}
						
		public Builder withInsuranceID(String insuranceId)
		{
			this.insuranceId = insuranceId;
			
			return this;
		}
		
		
		public Patient build()
		{
			this.withRole(UserRole.Patient);
			User user = super.build();
			Patient p = new Patient(user);	
			p.setInsuranceId(this.insuranceId);
			return p;
		}
	}
	
}
