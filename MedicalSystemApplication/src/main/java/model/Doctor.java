package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Doctor extends User
{

	@Column(name = "type", nullable = false)
	private String type;
	
	@Column(name = "insuranceId", nullable = false)
	private String insuranceId;
	
	@Column(name = "shiftStart", nullable = false)
    private String shiftStart;
	
    @Column(name = "shiftEnd", nullable = false)
    private String shiftEnd;
    
    @Column(name = "avarageRating", nullable = false)
    private float avarageRating;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Clinic clinic;
    
    @OneToMany(fetch = FetchType.LAZY)
	private List<ReviewDoctor> review;
    
    @OneToMany(fetch = FetchType.LAZY)
	private List<Appointment> scheduledAppointment;
    
	public Doctor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Doctor(String username, String password, String email, String name, String surname, String city,
			String address, String state, String phone) {
		super(username, password, email, name, surname, city, address, state, phone, UserRole.Doctor);
		scheduledAppointment = new ArrayList<Appointment>();
		this.setIsFirstLog(true);
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	
	public String getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}
	
	
	public float getAvarageRating() {
		return avarageRating;
	}

	public void setAvarageRating(float avarageRating) {
		this.avarageRating = avarageRating;
	}

	public List<ReviewDoctor> getReview() {
		return review;
	}

	public void setReview(List<ReviewDoctor> review) {
		this.review = review;
	}


	public List<Appointment> getScheduledAppointment() {
		return scheduledAppointment;
	}

	public void setScheduledAppointment(List<Appointment> scheduledAppointment) {
		this.scheduledAppointment = scheduledAppointment;
	}

	
}
