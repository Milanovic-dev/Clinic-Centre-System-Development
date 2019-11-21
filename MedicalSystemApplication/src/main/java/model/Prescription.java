package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Prescription {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;
	@Column(name = "date", nullable = false)
	private Date validationDate;
	@Column(name = "valid", nullable = false)
	private Boolean isValid;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nurse_id")
	private Nurse nurse;
	
	
	public Prescription()
	{
		super();
	}
	
	public Prescription(Recipe recipe, Date validationDate) {
		super();
		this.recipe = recipe;
		this.validationDate = validationDate;
		this.isValid = false;
	}
	
	public void validate(Nurse nurse)
	{
		nurse.getPrescriptions().add(this);
		this.nurse = nurse;
		isValid = true;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
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
	
}
