package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Doctor extends User
{

	@Column(name = "type", nullable = true)
	private String type;
	
	@Column(name = "insuranceId", nullable = true)
	private String insuranceId;
	
	@Column(name = "shiftStart", nullable = true)
    private String shiftStart;
	
    @Column(name = "shiftEnd", nullable = true)
    private String shiftEnd;
    
    @Column(name = "avarageRating", nullable = true)
    private float avarageRating;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Clinic clinic;
    
    @OneToMany(fetch = FetchType.LAZY)
	private List<ReviewDoctor> review;
    
    @Column(name="deleted",nullable = false)
	private Boolean deleted;
      
	public Doctor() {
		super();
		deleted = false;

		// TODO Auto-generated constructor stub
	}

	public Doctor(String username, String password, String email, String name, String surname, String city,
			String address, String state, String phone) {
		super(username, password, email, name, surname, city, address, state, phone, UserRole.Doctor);
		this.setIsFirstLog(true);
		this.deleted = false;
	}

	public Doctor(User user) {
		super(user);
		this.setRole(UserRole.Doctor);
		this.setIsFirstLog(true);
		this.deleted = false;

	}

	
	
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getShiftStart() {
		return shiftStart;
	}

	public void setShiftStart(String shiftStart) {
		this.shiftStart = shiftStart;
	}

	public String getShiftEnd() {
		return shiftEnd;
	}

	public void setShiftEnd(String shiftEnd) {
		this.shiftEnd = shiftEnd;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
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
	
}
