package dto;

import model.User;
import model.User.UserRole;

public class UserDTO 
{
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
	
	public UserDTO()
	{
		super();
	}
		
	public UserDTO(String username, String password, String email, String name, String surname, String city,
			String address, String state, String phone,UserRole role) {
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

	public UserDTO(User dto)
	{
		super();
		email = dto.getEmail();
		password = dto.getPassword();
		name = dto.getName();
		surname = dto.getSurname();
		city = dto.getCity();
		address = dto.getAddress();
		state = dto.getState();
		phone = dto.getPhone();
		role = dto.getRole();
	}
	
	
	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
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
	
	
	
}
