package model;

public class Drug {

	private Long id;
	private String name;
	private String description;
	private String code;
	
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
