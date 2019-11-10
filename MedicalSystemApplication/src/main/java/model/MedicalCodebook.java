package model;

public class MedicalCodebook 
{
	 private Long id;
	 private String code;
	 
	 
	public MedicalCodebook() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public MedicalCodebook(Long id, String code) {
		super();
		this.id = id;
		this.code = code;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	 
	 
}
