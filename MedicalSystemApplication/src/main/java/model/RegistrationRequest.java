package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class RegistrationRequest{
	
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
      
    @Column(name = "username", nullable = false)
    private String username;
    
    @Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "surname", nullable = false)
	private String surname;
	
	@Column(name = "city", nullable = false)
	private String city;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "state", nullable = false)
	private String state;
	
	@Column(name = "phoneNumber", nullable = false)
	private String phone;

    public RegistrationRequest() {
    	super();
    }

    public RegistrationRequest(String username, String password, String email, String name, String surname, String city, String address, String state, String phone) {
    	this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.city = city;
		this.address = address;
		this.state = state;
		this.phone = phone;
    }
    
    public RegistrationRequest(RegistrationRequest req)
    {
    	this.username = req.getUsername();
		this.password = req.getPassword();
		this.email = req.getEmail();
		this.name = req.getName();
		this.surname = req.getSurname();
		this.city = req.getCity();
		this.address = req.getAddress();
		this.state = req.getState();
		this.phone = req.getPhone();
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
