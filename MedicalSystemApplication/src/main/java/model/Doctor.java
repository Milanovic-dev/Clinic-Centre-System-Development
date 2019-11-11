package model;

import java.util.ArrayList;

public class Doctor extends User
{
	private String type;
	private String incuranseID;
	private float avgReview;
	private ArrayList<DoctorReview> review;
	private ArrayList<Appointment> scheduledAppointment;
	private ArrayList<Appointment> completedAppointment;
	//holiday list

	public Doctor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Doctor(String username, String password, String email, String name, String surname, String city,
			String address, String state, String phone) {
		super(username, password, email, name, surname, city, address, state, phone, UserRole.Doctor);
		scheduledAppointment = new ArrayList<Appointment>();
		completedAppointment = new ArrayList<Appointment>();
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	public String getIdInsuranse() {
		return incuranseID;
	}
	public void setIdInsuranse(String incuranseID) {
		this.incuranseID = incuranseID;
	}
	public float getAvgReview() {
		return avgReview;
	}
	public void setAvgReview(float avgReview) {
		this.avgReview = avgReview;
	}
		
	public ArrayList<DoctorReview> getReview() {
		return review;
	}

	public void setReview(ArrayList<DoctorReview> review) {
		this.review = review;
	}

	public String getIncuranseID() {
		return incuranseID;
	}
	public void setIncuranseID(String incuranseID) {
		this.incuranseID = incuranseID;
	}

	public ArrayList<Appointment> getScheduledAppointment() {
		return scheduledAppointment;
	}

	public void setScheduledAppointment(ArrayList<Appointment> scheduledAppointment) {
		this.scheduledAppointment = scheduledAppointment;
	}

	public ArrayList<Appointment> getCompletedAppointment() {
		return completedAppointment;
	}

	public void setCompletedAppointment(ArrayList<Appointment> completedAppointment) {
		this.completedAppointment = completedAppointment;
	}
	
}
