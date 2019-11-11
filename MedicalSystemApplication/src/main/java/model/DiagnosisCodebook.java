package model;

public class DiagnosisCodebook 
{
	 private Long id;
	 private String code;
	 
	 
	public DiagnosisCodebook() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DiagnosisCodebook(Long id, String code) {
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
