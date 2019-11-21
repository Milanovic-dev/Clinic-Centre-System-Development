package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Recipe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    @Column(name = "header", nullable = false)
	private String header;
    
    @Column(name = "description", nullable = false)
	private String description;
    
    @ManyToMany
	private List<Drug> drugs;
	
    public Recipe()
    {
    	super();
    }
	
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


	public List<Drug> getDrugs() {
		return drugs;
	}


	public void setDrugs(List<Drug> drugs) {
		this.drugs = drugs;
	}
	
	
	
}
