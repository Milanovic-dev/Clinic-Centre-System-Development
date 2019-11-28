package dto;

import java.util.ArrayList;
import java.util.List;

import model.*;
import model.Appointment.AppointmentType;

public class AppointmentDTO {

	private String date;
	private String patientEmail;
	private String clinicName;
	private int hallNumber;
	private List<String> doctors;
	private long duration;
	private double price;
	private String appointmentDescription;
	private AppointmentType type;
	
	
	public AppointmentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AppointmentDTO(String date, String patientEmail, String clinicName, int hallNumber, List<String> doctors,
			long duration, double price, String appointmentDescription, AppointmentType type) {
		super();
		this.date = date;
		this.patientEmail = patientEmail;
		this.clinicName = clinicName;
		this.hallNumber = hallNumber;
		this.doctors = doctors;
		this.duration = duration;
		this.price = price;
		this.appointmentDescription = appointmentDescription;
		this.type = type;
	}
	
	public AppointmentDTO(Appointment appointment)
	{
		this.date = appointment.getDate().toString();
		this.patientEmail = appointment.getPatient().getEmail();
		this.clinicName = appointment.getClinic().getName();
		this.hallNumber = appointment.getHall().getNumber();
		this.doctors = new ArrayList<String>();
		for(Doctor doc : appointment.getDoctors())
		{
			doctors.add(doc.getEmail());
		}
		this.duration = appointment.getDuration();
		this.price = appointment.getPrice();
		this.appointmentDescription = appointment.getAppointmentDescription();
		this.type = appointment.getAppointmentType();	
	}
	
	public AppointmentDTO(AppointmentRequest appointment)
	{
		this.date = appointment.getDate().toString();
		this.patientEmail = appointment.getPatient().getEmail();
		this.clinicName = appointment.getClinic().getName();
		this.hallNumber = appointment.getHall().getNumber();
		this.doctors = new ArrayList<String>();
		for(Doctor doc : appointment.getDoctors())
		{
			doctors.add(doc.getEmail());
		}
		this.appointmentDescription = appointment.getAppointmentDescription();
		this.type = appointment.getAppointmentType();	
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPatientEmail() {
		return patientEmail;
	}

	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public int getHallNumber() {
		return hallNumber;
	}

	public void setHallNumber(int hallNumber) {
		this.hallNumber = hallNumber;
	}

	public List<String> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<String> doctors) {
		this.doctors = doctors;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getAppointmentDescription() {
		return appointmentDescription;
	}

	public void setAppointmentDescription(String appointmentDescription) {
		this.appointmentDescription = appointmentDescription;
	}

	public AppointmentType getType() {
		return type;
	}

	public void setType(AppointmentType type) {
		this.type = type;
	}
	
	
}
