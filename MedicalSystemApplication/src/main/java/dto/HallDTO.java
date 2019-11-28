package dto;

import model.Hall;

public class HallDTO {

	private String clinicName;
	private int number;
	
		
	public HallDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HallDTO(String clinicName, int number) {
		super();
		this.clinicName = clinicName;
		this.number = number;
	} 	
	
	public HallDTO(Hall hall)
	{
		this.clinicName = hall.getClinic().getName();
		this.number = hall.getNumber();
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	
}
