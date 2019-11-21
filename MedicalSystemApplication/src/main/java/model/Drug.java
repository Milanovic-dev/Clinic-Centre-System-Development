package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Drug {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name= "name",nullable = false)
	private String name;
	@Column(name= "description",nullable = true)
	private String description;
	@Column(name= "code",nullable = false)
	private String code;
	
	
	public Drug()
	{
		
	}
	
	public Drug(String name, String description, String code) {
		super();
		this.name = name;
		this.description = description;
		this.code = code;
	}
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
