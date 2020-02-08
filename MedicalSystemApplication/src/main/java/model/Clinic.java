package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @Column(name = "description", nullable = true)
	private String description;
    
    @Column(name="rating", nullable = false)
    private float rating;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Hall> halls;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Doctor> doctors;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ClinicReview> reviews;


    
    public Clinic() 
    {
    	super();
      
    }

    public Clinic(String name, String address, String city, String state, String description)
    { 	
    	super();
	    this.name = name;
	    this.address = address;
	    this.city = city;
	    this.state = state;
	    this.description = description;
	    this.halls = new ArrayList<Hall>();
	    this.doctors = new ArrayList<Doctor>();
	    this.reviews = new ArrayList<ClinicReview>();
    }

    public Clinic(Clinic clinic) {
    }
    
    

    public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
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
    
    public List<Doctor> getDoctors() {
    	return doctors;
    }
    
    public void setDoctors(List<Doctor> doctors) {
    	this.doctors = doctors;
    }
    
    public List<ClinicReview> getReviews() {
    	return reviews;
    }
    
    public void setReviews(List<ClinicReview> reviews) {
    	this.reviews = reviews;
    }

	public float calculateRating() {
		// TODO Auto-generated method stub
		List<ClinicReview> reviews = getReviews();
		List<Integer> ratings = new ArrayList<Integer>();
		float sum = 0;
		
		if(reviews.isEmpty())
		{
			return -1;
		}
		
		for(ClinicReview cr : reviews)
		{
			if(cr.getRating() >= 0)
			{
				ratings.add(cr.getRating());
			}
		}
		
		for(Integer r : ratings)
		{
			sum = sum + r;
		}
		
		float ret = (Float) sum / ratings.size();
		
		this.rating = ret;
		return ret;

	}

}
