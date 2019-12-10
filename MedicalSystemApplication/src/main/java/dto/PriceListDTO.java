package dto;

import model.Priceslist;

public class PriceListDTO {
	private String clinicName;
	private String typeOfExamination;
	private Long price;
	
	public  PriceListDTO(Priceslist pr)
	{
		this.clinicName = pr.getClinic().getName();
		this.typeOfExamination = pr.getTypeOfExamination();
		this.price = pr.getPrice();
	}
	
	public PriceListDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PriceListDTO(String clinicName, String typeOfExamination, Long price) {
		super();
		this.clinicName = clinicName;
		this.typeOfExamination = typeOfExamination;
		this.price = price;
	}
	
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public String getTypeOfExamination() {
		return typeOfExamination;
	}
	public void setTypeOfExamination(String typeOfExamination) {
		this.typeOfExamination = typeOfExamination;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	
	
}
