package dto;

import model.Hall;

public class HallDTO {

	private String clinicName;
	private int number;
	private String name;
	
		
	public HallDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HallDTO(String clinicName, int number,String name) {
		super();
		this.clinicName = clinicName;
		this.number = number;
		this.name = name;
	} 	
	
	public HallDTO(Hall hall)
	{
		this.clinicName = hall.getClinic().getName();
		this.number = hall.getNumber();
		this.name = hall.getName();
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
