package model;

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


	public Examination(String appointmentID, long startingDateAndTime, long duration, double price, Hall hall,
			Patient patient, Clinic clinic) {
		super(appointmentID, startingDateAndTime, duration, price, hall, patient, clinic);
		// TODO Auto-generated constructor stub
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
