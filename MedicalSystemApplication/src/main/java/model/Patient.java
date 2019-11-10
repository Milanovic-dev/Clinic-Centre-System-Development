package model;

import java.util.ArrayList;
import java.util.Collection;

public class Patient extends User{

	private String insuranceId;
	private MedicalRecord medicalRecord;
	private Collection<Appointment> scheduledAppointments;

	public Patient()
	{
		super();
	}
	
	public Patient(String username, String password, String email, String name, String surname, String city,
			String address, String state, String phone) {
		super(username, password, email, name, surname, city, address, state, phone, UserRole.Patient);
		medicalRecord = new MedicalRecord();
		scheduledAppointments = new ArrayList<Appointment>();
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

	public Collection<Appointment> getScheduledAppointments() {
		return scheduledAppointments;
	}

	public void setScheduledAppointments(ArrayList<Appointment> scheduledAppointments) {
		this.scheduledAppointments = scheduledAppointments;
	}
	
	
}
