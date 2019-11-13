package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class DiagnosisCodebook 
{

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
	 private Long id;
    
     @OneToMany(fetch = FetchType.LAZY)
	 private List<String> code;
	 
	 
	public DiagnosisCodebook() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DiagnosisCodebook(Long id, String code) {
		super();
		this.id = id;
		this.code = new ArrayList<String>();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<String> getCode() {
		return code;
	}
	public void setCode(List<String> code) {
		this.code = code;
	}
	
	 
	 
}
