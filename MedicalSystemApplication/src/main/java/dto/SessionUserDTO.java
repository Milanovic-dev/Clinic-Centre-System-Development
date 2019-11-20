package dto;

import model.User;
import model.User.UserRole;

public class SessionUserDTO 
{
	private String email;
	private String name;
	private String surname;
	private String city;
	private String address;
	private String state;
	private String phone;
	private UserRole role;
	
	public SessionUserDTO()
	{
		super();
	}

	public SessionUserDTO(String email, String name, String surname, String city,
			String address, String state, String phone, UserRole role) {
		super();
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.city = city;
		this.address = address;
		this.state = state;
		this.phone = phone;
		this.role = role;
	}
	
	public SessionUserDTO(User user)
	{
		super();
		this.email = user.getEmail();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.city = user.getCity();
		this.address = user.getAddress();
		this.state = user.getState();
		this.phone = user.getPhone();
		this.role = user.getRole();
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
	
	
	
	
}
