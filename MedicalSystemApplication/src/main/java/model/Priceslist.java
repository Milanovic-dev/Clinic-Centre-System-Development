package model;

public class Priceslist 
{
	private Long id;
	private Clinic clinic;
	private String typeOfExamination;
	private Long price;
	
	
	public Priceslist() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Priceslist(Long id, Clinic clinic, String typeOfExamination, Long price) {
		super();
		this.id = id;
		this.clinic = clinic;
		this.typeOfExamination = typeOfExamination;
		this.price = price;
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
