package model;

import java.util.ArrayList;
import java.util.Collection;

public class Recipe {

	private String header;
	private String description;
	private Collection<Drug> drugs;
	
	
	public Recipe(String header, String description) {
		super();
		this.header = header;
		this.description = description;
		drugs = new ArrayList<Drug>();
	}


	public String getHeader() {
		return header;
	}


	public void setHeader(String header) {
		this.header = header;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Collection<Drug> getDrugs() {
		return drugs;
	}


	public void setDrugs(Collection<Drug> drugs) {
		this.drugs = drugs;
	}
	
	
	
}
