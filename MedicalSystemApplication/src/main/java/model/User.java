package model;

import javax.persistence.*;

import dto.UserDTO;

@Entity(name = "users")
@Table
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User
{
	public enum UserRole{ Patient, Doctor, Nurse, ClinicAdmin, CentreAdmin}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "insuranceId", nullable = true)
	private String insuranceId;
	
	@Column(name="deleted",nullable = true)
	private Boolean deleted;
	
	@Column(name = "isFirstLog", nullable = false)
	private Boolean isFirstLog;
	
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
	
	@Column(name = "phone", nullable = false)
	private String phone;
	
	@Column(name = "role", nullable = false)
	private UserRole role;
	
	public User()
	{
		super();
		this.deleted = false;
	}

	public User(String password, String email, String name, String surname, String city,
			String address, String state, String phone, UserRole role) {
		super();
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.city = city;
		this.address = address;
		this.state = state;
		this.phone = phone;
		this.role = role;
		this.deleted = false;
	}
	
	public User(RegistrationRequest request, UserRole role)
	{
		super();
		this.password = request.getPassword();
		this.email = request.getEmail();
		this.name = request.getName();
		this.surname = request.getSurname();
		this.city = request.getCity();
		this.address = request.getAddress();
		this.state = request.getState();
		this.phone = request.getPhone();
		this.insuranceId = request.getInsuranceId();
		this.role = role;
		this.deleted = false;
	}
	
	

	public User(User user) {
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.city = user.getCity();
		this.address = user.getAddress();
		this.state = user.getState();
		this.phone = user.getPhone();
		this.role = user.getRole();
		this.insuranceId = user.getInsuranceId();
		this.deleted = false;
	}

	
	
	public User(UserDTO user) {
		// TODO Auto-generated constructor stub
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.city = user.getCity();
		this.address = user.getAddress();
		this.state = user.getState();
		this.phone = user.getPhone();
		this.role = user.getRole();
		this.insuranceId = user.getInsuranceId();
		this.deleted = false;
	}

	public String getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getIsFirstLog() {
		return isFirstLog;
	}

	public void setIsFirstLog(Boolean isFirstLog) {
		this.isFirstLog = isFirstLog;
	}

	public void setId(Long id) {
		this.id = id;
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
