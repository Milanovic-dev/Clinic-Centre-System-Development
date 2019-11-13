package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class MedicalCodebook 
{
	 private Long id;
	 
	 private List<String> codes;
	 
	 
	public MedicalCodebook() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MedicalCodebook(Long id) {
		super();
		this.id = id;
		codes = new ArrayList<String>();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getCodes() {
		return codes;
	}
	public void setCodes(List<String> codes) {
		this.codes = codes;
	}
	
	 
	 
}
