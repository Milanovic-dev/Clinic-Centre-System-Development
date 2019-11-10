package model;

import java.util.Collection;

public class PatientMedicalReport {
	
	private Appointment appointment;
	private Prescription prescription;
	
	public PatientMedicalReport(Appointment appointment, Prescription prescription) {
		super();
		this.appointment = appointment;
		this.prescription = prescription;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Prescription getPrescription() {
		return prescription;
	}

	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}
	
	
	
}
