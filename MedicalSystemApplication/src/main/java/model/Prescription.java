package model;

import java.util.Date;

public class Prescription {

	private Long id;
	private Recipe recipe;
	private Date validationDate;
	private Boolean isValid;
	private Nurse nurse;
	
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
