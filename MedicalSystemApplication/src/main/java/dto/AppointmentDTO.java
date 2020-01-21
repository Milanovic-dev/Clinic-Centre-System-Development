package dto;

import java.util.ArrayList;
import java.util.List;

import helpers.DateUtil;
import model.*;
import model.Appointment.AppointmentType;

public class AppointmentDTO {

	private String date;
	private String patientEmail;
	private String clinicName;
	private int hallNumber;
	private List<String> doctors;
	private long duration;
	private float price;
	private String typeOfExamination;
	private AppointmentType type;
	private int version;
	private String startTimestamp;
	
	public AppointmentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AppointmentDTO(String date, String patientEmail, String clinicName, int hallNumber, List<String> doctors,
			long duration, String priceslist, AppointmentType type) {
		super();
		this.date = date;
		this.patientEmail = patientEmail;
		this.clinicName = clinicName;
		this.hallNumber = hallNumber;
		this.doctors = doctors;
		this.duration = duration;
		this.typeOfExamination = priceslist;
		this.type = type;
	}
	
	public AppointmentDTO(Appointment appointment)
	{
		this.date = DateUtil.getInstance().getString(appointment.getDate(),"dd-MM-yyyy HH:mm");
		if(appointment.getPatient() != null)
			this.patientEmail = appointment.getPatient().getEmail();
		this.clinicName = appointment.getClinic().getName();
		this.hallNumber = appointment.getHall().getNumber();
		this.doctors = new ArrayList<String>();
		for(Doctor doc : appointment.getDoctors())
		{
			doctors.add(doc.getEmail());
		}
		this.duration = appointment.getDuration();
		if(appointment.getPriceslist() != null)
		{
			this.typeOfExamination = appointment.getPriceslist().getTypeOfExamination();
			this.price = appointment.getPriceslist().getPrice();
		}
		this.type = appointment.getAppointmentType();	
		this.version = appointment.getVersion();
	}
	
	public AppointmentDTO(AppointmentRequest appointment)
	{
		this.date = DateUtil.getInstance().getString(appointment.getDate(),"dd-MM-yyyy HH:mm");
		this.startTimestamp = DateUtil.getInstance().getString(appointment.getTimestamp(), "dd-MM-yyyy HH:mm");
		this.patientEmail = appointment.getPatient().getEmail();
		this.clinicName = appointment.getClinic().getName();
		if(appointment.getHall() != null)
			this.hallNumber = appointment.getHall().getNumber();
		this.doctors = new ArrayList<String>();
		for(Doctor doc : appointment.getDoctors())
		{
			doctors.add(doc.getEmail());
		}
		this.typeOfExamination = appointment.getPriceslist().getTypeOfExamination();
		this.price = appointment.getPriceslist().getPrice();
		this.type = appointment.getAppointmentType();	
	}
	
	
	
	public String getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(String startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
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
	
	public String getTypeOfExamination() {
		return typeOfExamination;
	}

	public void setTypeOfExamination(String priceslist) {
		this.typeOfExamination = priceslist;
	}

	public AppointmentType getType() {
		return type;
	}

	public void setType(AppointmentType type) {
		this.type = type;
	}
	
	
}
