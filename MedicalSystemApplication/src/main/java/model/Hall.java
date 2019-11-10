package model;

public class Hall
{
	private Long hallID;
	private Clinic clinic;
	private Integer number; 	

	public Hall() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Hall(Clinic clinic, Integer number) {
		super();
		this.clinic = clinic;
		this.number = number;
	
	}


	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}

	public Long getHallID() {
		return hallID;
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
				+ "]";
	}
	
	
}
