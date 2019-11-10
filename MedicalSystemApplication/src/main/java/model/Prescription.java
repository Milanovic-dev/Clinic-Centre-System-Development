package model;

import java.util.Date;

public class Prescription {

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
		this.nurse = nurse;
		isValid = true;
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
