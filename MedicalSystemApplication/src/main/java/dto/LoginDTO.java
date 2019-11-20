package dto;

public class LoginDTO {

	private String email;
	private String password;
	
	public LoginDTO()
	{
		super();
	}
	
	public LoginDTO(String username, String password) {
		super();
		this.email = username;
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String username) {
		this.email = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString()
	{
		return "["+this.email + " + " + this.password + "]";
	}
	
}
