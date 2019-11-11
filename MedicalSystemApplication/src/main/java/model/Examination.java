package model;

import java.util.Date;

public class Examination extends Appointment
{
	private Doctor doctor;
	private String typeOfExamination;
	
	public Examination() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Examination(Doctor doctor, String typeOfExamination) {
		super();
		this.doctor = doctor;
		this.typeOfExamination = typeOfExamination;
	}


	public Examination(Date startingDateAndTime, long duration, double price, Hall hall,
			Patient patient, Clinic clinic, Doctor doctor, String typeOfExamination) {
		super(startingDateAndTime, duration, price, hall, patient, clinic,AppointmentType.Examination);
		this.doctor = doctor;
		this.typeOfExamination = typeOfExamination;
	}
	
	public Examination(AppointmentRequest request,Doctor doctor, String typeOfExamination)
	{
		super(request,AppointmentType.Examination);
		this.doctor = doctor;
		this.typeOfExamination = typeOfExamination;
	}

	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public String getTypeOfExamination() {
		return typeOfExamination;
	}
	public void setTypeOfExamination(String typeOfExamination) {
		this.typeOfExamination = typeOfExamination;
	}

}
