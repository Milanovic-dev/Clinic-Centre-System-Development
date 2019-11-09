package model;

import java.util.ArrayList;

public class Doctor extends User
{
	private String type;
	private String idInsuranse;
	private float avgRating;
	private ArrayList<Integer> ratings;
	//private ArrayList<Appointment> scheduledApp;
	//private ArrayList<Appointment> completedApp;
	//private ArrayList<Operation> scheduledOp;
	//private ArrayList<Operation> completedOp;
	//holiday list
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIdInsuranse() {
		return idInsuranse;
	}
	public void setIdInsuranse(String idInsuranse) {
		this.idInsuranse = idInsuranse;
	}
	public float getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(float avgRating) {
		this.avgRating = avgRating;
	}
	public ArrayList<Integer> getRatings() {
		return ratings;
	}
	public void setRatings(ArrayList<Integer> ratings) {
		this.ratings = ratings;
	}
	
	
	
}
