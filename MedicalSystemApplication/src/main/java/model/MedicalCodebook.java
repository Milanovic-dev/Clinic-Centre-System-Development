package model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class MedicalCodebook 
{
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @OneToMany(fetch = FetchType.LAZY)
	 @JoinColumn(name = "drug_id")
	 private ArrayList<String> codes;
	 
	 
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

	public ArrayList<String> getCodes() {
		return codes;
	}
	public void setCodes(ArrayList<String> codes) {
		this.codes = codes;
	}
	
	 
	 
}
