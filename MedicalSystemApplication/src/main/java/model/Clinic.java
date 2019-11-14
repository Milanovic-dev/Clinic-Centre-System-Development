package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Clinic 
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @Column(name = "name", nullable = false)
	private String name;

    @Column(name = "address", nullable = false)
	private String address;

    @Column(name = "city", nullable = false)
	private String city;

    @Column(name = "state", nullable = false)
	private String state;

    @Column(name = "description", nullable = false)
	private String description;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Hall> halls;

    @OneToMany(fetch = FetchType.LAZY)
    private List<ClinicAdmin> clinicAdmins;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Doctor> doctors;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Appointment> appointments;

    @OneToMany(fetch = FetchType.LAZY)
    private List<ClinicReview> reviews;


    
    public Clinic() 
    {
    	super();
      
    }

    public Clinic(String name, String address, String city, String state, String description, Long id)
    { 	
    	super();
	    this.name = name;
	    this.address = address;
	    this.city = city;
	    this.state = state;
	    this.description = description;
	    this.id = id;
	
	    this.halls = new ArrayList<Hall>();
	    this.clinicAdmins = new ArrayList<ClinicAdmin>();
	    this.doctors = new ArrayList<Doctor>();
	    this.appointments = new ArrayList<Appointment>();
	    this.reviews = new ArrayList<ClinicReview>();
    }

    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getAddress() {
    	return address;
    }
    
    public void setAddress(String address) {
    	this.address = address;
    }
    
    public String getCity() {
    	return city;
    }
    
    public void setCity(String city) {
    	this.city = city;
    }
    
    public String getState() {
    	return state;
    }
    
    public void setState(String state) {
    	this.state = state;
    }
    
    public String getDescription() {
    	return description;
    }
    
    public void setDescription(String description) {
    	this.description = description;
    }
    
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public List<Hall> getHalls() {
    	return halls;
    }
    
    public void setHalls(List<Hall> halls) {
    	this.halls = halls;
    }
    
    public List<ClinicAdmin> getClinicAdmins() {
    	return clinicAdmins;
    }
    
    public void setClinicAdmins(List<ClinicAdmin> clinicAdmins) {
    	this.clinicAdmins = clinicAdmins;
    }
    
    public List<Doctor> getDoctors() {
    	return doctors;
    }
    
    public void setDoctors(List<Doctor> doctors) {
    	this.doctors = doctors;
    }
    
    public List<Appointment> getAppointments() {
    	return appointments;
    }
    
    public void setAppointments(List<Appointment> appointments) {
    	this.appointments = appointments;
    }
    
    public List<ClinicReview> getReviews() {
    	return reviews;
    }
    
    public void setReviews(List<ClinicReview> reviews) {
    	this.reviews = reviews;
    }

}
