package dto;

import model.User;

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
	
	public UserDTO(User dto)
	{
		email = dto.getEmail();
		password = dto.getPassword();
		name = dto.getName();
		surname = dto.getSurname();
		city = dto.getCity();
		address = dto.getAddress();
		state = dto.getState();
		phone = dto.getPhone();
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
