package model;

import java.util.ArrayList;

public class Hall
{
	private String hallID;
	private Clinic clinic;
	private Integer number; 
	private ArrayList<Appointment> scheduleAppointments;
	private ArrayList<Surgery> scheduleSurgeries;
	

	public Hall() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Hall(String hallID, Clinic clinic, Integer number) {
		super();
		this.hallID = hallID;
		this.clinic = clinic;
		this.number = number;
		
		scheduleAppointments = new ArrayList<Appointment>();
		scheduleSurgeries = new ArrayList<Surgery>();
	}


	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public ArrayList<Appointment> getScheduleAppointments() {
		return scheduleAppointments;
	}
	public void setScheduleAppointments(ArrayList<Appointment> scheduleAppointments) {
		this.scheduleAppointments = scheduleAppointments;
	}
	public ArrayList<Surgery> getScheduleSurgeries() {
		return scheduleSurgeries;
	}
	public void setScheduleSurgeries(ArrayList<Surgery> scheduleSurgeries) {
		this.scheduleSurgeries = scheduleSurgeries;
	}
	public String getHallID() {
		return hallID;
	}
	public void setHallID(String hallID) {
		this.hallID = hallID;
	}
	public Clinic getClinic() {
		return clinic;
	}
	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}


	@Override
	public String toString() {
		return "Hall [hallID=" + hallID + ", clinic=" + clinic + ", number=" + number + ", scheduleAppointments="
				+ scheduleAppointments + ", scheduleSurgeries=" + scheduleSurgeries + "]";
	}
	
	
}
