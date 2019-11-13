package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Doctor extends User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "type", nullable = false)
	private String type;
	
	@Column(name = "insuranceId", nullable = false)
	private String insuranceId;
	
	@Column(name = "shiftStart", nullable = false)
    private String shiftStart;
	
    @Column(name = "shiftEnd", nullable = false)
    private String shiftEnd;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Clinic clinic;
    
    @OneToMany(fetch = FetchType.LAZY)
	private List<DoctorReview> review;
    
    @OneToMany(fetch = FetchType.LAZY)
	private List<Appointment> scheduledAppointment;
    
    @OneToMany(fetch = FetchType.LAZY)
	private List<Appointment> completedAppointment;
	

	public Doctor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Doctor(String username, String password, String email, String name, String surname, String city,
			String address, String state, String phone) {
		super(username, password, email, name, surname, city, address, state, phone, UserRole.Doctor);
		scheduledAppointment = new ArrayList<Appointment>();
		completedAppointment = new ArrayList<Appointment>();
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

	
	public List<DoctorReview> getReview() {
		return review;
	}

	public void setReview(List<DoctorReview> review) {
		this.review = review;
	}


	public List<Appointment> getScheduledAppointment() {
		return scheduledAppointment;
	}

	public void setScheduledAppointment(List<Appointment> scheduledAppointment) {
		this.scheduledAppointment = scheduledAppointment;
	}

	public List<Appointment> getCompletedAppointment() {
		return completedAppointment;
	}

	public void setCompletedAppointment(List<Appointment> completedAppointment) {
		this.completedAppointment = completedAppointment;
	}
	
}
