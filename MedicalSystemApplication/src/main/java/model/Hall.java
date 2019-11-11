package model;

public class Hall
{
	private Long id;
	private Clinic clinic;
	private int number; 	

	public Hall() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Hall(Clinic clinic, int number) {
		super();
		this.clinic = clinic;
		this.number = number;
	
	}


	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Clinic getClinic() {
		return clinic;
	}
	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}


	@Override
	public String toString() {
		return "Hall [hallID=" + id + ", clinic=" + clinic + ", number=" + number + ", scheduleAppointments="
				+ "]";
	}
	
	
}
