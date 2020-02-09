package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
public class Prescription {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "description", nullable = true)
	private String description;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<Drug> drugs = new ArrayList<>();

	@Column(name = "date", nullable = true)
	private Date validationDate;

	@Column(name = "valid", nullable = false)
	private Boolean isValid = false;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nurse_id",  nullable = true)
	private Nurse nurse;

	@Version
	private Integer version;
	
	
	public Prescription()
	{
		super();
	}

	public Prescription(String description, List<Drug> drugs){
		this.description =  description;
		this.drugs = drugs;
		this.isValid = false;
	}

	public Prescription(String description, Date validationDate, Nurse nurse) {
		this.description = description;
		this.drugs = new ArrayList<>();
		this.validationDate = validationDate;
		this.isValid = false;
		this.nurse = nurse;
	}

	public void validate(Nurse nurse)
	{
		nurse.getPrescriptions().add(this);
		this.nurse = nurse;
		isValid = true;
	}

	public Date getValidationDate() {
		return validationDate;
	}

	public void setValidationDate(Date validationDate) {
		this.validationDate = validationDate;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public Nurse getNurse() {
		return nurse;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Drug> getDrugs() {
		return drugs;
	}

	public void setDrugs(List<Drug> drugs) {
		this.drugs = drugs;
	}

	public Boolean getValid() {
		return isValid;
	}

	public void setValid(Boolean valid) {
		isValid = valid;
	}

	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
