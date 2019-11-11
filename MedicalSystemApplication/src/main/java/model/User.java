package model;

public abstract class User
{
	public enum UserRole{ Patient, Doctor, Nurse, ClinicAdmin, CentreAdmin}
		
	private Long id;
	private String username;
	private String password;
	private String email;
	private String name;
	private String surname;
	private String city;
	private String address;
	private String state;
	private String phone;
	private UserRole role;
	
	public User()
	{
		super();
	}

	public User(String username, String password, String email, String name, String surname, String city,
			String address, String state, String phone, UserRole role) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.city = city;
		this.address = address;
		this.state = state;
		this.phone = phone;
		this.role = role;
	}
	
	public User(RegistrationRequest request, UserRole role)
	{
		super();
		this.username = request.getUsername();
		this.password = request.getPassword();
		this.email = request.getEmail();
		this.name = request.getName();
		this.surname = request.getSurname();
		this.city = request.getCity();
		this.address = request.getAddress();
		this.state = request.getState();
		this.phone = request.getPhone();
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}
}
