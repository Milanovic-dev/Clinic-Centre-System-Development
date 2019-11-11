package model;

import java.util.ArrayList;
import java.util.Collection;

public class Recipe {

	private Long id;
	private String header;
	private String description;
	private ArrayList<Drug> drugs;
	
	
	public Recipe(String header, String description) {
		super();
		this.header = header;
		this.description = description;
		drugs = new ArrayList<Drug>();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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


	public ArrayList<Drug> getDrugs() {
		return drugs;
	}


	public void setDrugs(ArrayList<Drug> drugs) {
		this.drugs = drugs;
	}
	
	
	
}
