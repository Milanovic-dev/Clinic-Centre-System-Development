package helpers;

import javax.persistence.Column;

import model.User;
import model.User.UserRole;

public class UserBuilder {

	private String email;
	private String password;
	private String name;
	private String surname;
	private String city;
	private String address;
	private String state;
	private String phone;
	private UserRole role;
	
	protected UserBuilder(String email)
	{
		this.email = email;
	}
	
	protected UserBuilder withPassword(String password)
	{
		this.password = password;
		
		return this;
	}
	
	protected UserBuilder withName(String name)
	{
		this.name = name;
		
		return this;
	}
	
	protected UserBuilder withSurname(String surname)
	{
		this.surname = surname;
		
		return this;
	}
	
	protected UserBuilder withCity(String city)
	{
		this.city = city;
		
		return this;
	}
	
	protected UserBuilder withAddress(String address)
	{
		this.address = address;
		
		return this;
	}
	
	protected UserBuilder withState(String state)
	{
		this.state = state;
		
		return this;
	}
	
	protected UserBuilder withPhone(String phone)
	{
		this.phone = phone;
		
		return this;
	}
	
	protected UserBuilder withRole(UserRole role)
	{
		this.role = role;
		
		return this;
	}
	
	protected User build()
	{
		User user = new User();
		user.setEmail(this.email);
		user.setPassword(this.password);
		user.setName(this.name);
		user.setSurname(this.surname);
		user.setCity(this.city);
		user.setAddress(this.address);
		user.setState(this.state);
		user.setPhone(this.phone);
		user.setRole(this.role);
		return user;
	}
	
	
}
