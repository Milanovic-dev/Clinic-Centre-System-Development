package model;

import javax.persistence.*;


@Entity
public class Priceslist 
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Clinic clinic;

	@Column(name = "typeOfExamination", nullable = false)
	private String typeOfExamination;

	@Column(name = "price", nullable = false)
	private Long price;
	
	@Column(name = "deleted", nullable = false)
	private Boolean deleted;
	
	
	public Priceslist() {
		super();
		this.deleted = false;
		// TODO Auto-generated constructor stub
	}
	public Priceslist(Clinic clinic, String typeOfExamination, Long price) {
		super();
		this.clinic = clinic;
		this.typeOfExamination = typeOfExamination;
		this.price = price;
		this.deleted = false;
	}
	
	
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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
