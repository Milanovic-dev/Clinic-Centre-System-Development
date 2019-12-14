package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import dto.DoctorDTO;
import helpers.DateUtil;
import helpers.UserBuilder;
import model.Patient.Builder;

@Entity
public class Doctor extends User
{

	@Column(name = "type", nullable = true)
	private String type;
		
	@Column(name = "shiftStart", nullable = true)
    private Date shiftStart;
	
    @Column(name = "shiftEnd", nullable = true)
    private Date shiftEnd;
    
    @Column(name = "avarageRating", nullable = true)
    private float avarageRating;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Clinic clinic;
    
    @OneToMany(fetch = FetchType.LAZY)
	private List<ReviewDoctor> reviews;
  
	@OneToMany(fetch = FetchType.LAZY)
	private List<Appointment> appointments;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Vacation> vacations;
  
	public Doctor() {
		super();
		vacations = new ArrayList<Vacation>();
		// TODO Auto-generated constructor stub
	}

	public Doctor(String password, String email, String name, String surname, String city,
			String address, String state, String phone) {
		super(password, email, name, surname, city, address, state, phone, UserRole.Doctor);
		this.setIsFirstLog(true);
		this.appointments = new ArrayList<>();
		this.vacations = new ArrayList<Vacation>();
		this.reviews = new ArrayList<ReviewDoctor>();
	}

	public Doctor(User user) {
		super(user);
		this.setRole(UserRole.Doctor);
		this.setIsFirstLog(true);
		this.appointments = new ArrayList<>();
		this.vacations = new ArrayList<Vacation>();
		this.reviews = new ArrayList<ReviewDoctor>();
	}
	
	public Doctor(DoctorDTO dto)
	{
		super(dto.getUser());
		this.setRole(UserRole.Doctor);
		this.setIsFirstLog(true);
		this.shiftStart = DateUtil.getInstance().GetDate(dto.getShiftStart(), "HH:mm");
		this.shiftEnd = DateUtil.getInstance().GetDate(dto.getShiftEnd(), "HH:mm");
		this.appointments = new ArrayList<Appointment>();
		this.vacations = new ArrayList<Vacation>();
		this.reviews = new ArrayList<ReviewDoctor>();
	}

	public Boolean IsFreeOn(Date date)
	{
		for(Vacation v: vacations)
		{
			Date start = v.getStartDate();
			Date end = v.getEndDate();
			
			if(date.after(start) && date.before(end))
			{
				return false;
			}
		}
		
		return true;
	}
		
	public List<Vacation> getVacations() {
		return vacations;
	}

	public void setVacations(List<Vacation> vacations) {
		this.vacations = vacations;
	}

	public Date getShiftStart() {
		return shiftStart;
	}

	public void setShiftStart(Date shiftStart) {
		this.shiftStart = shiftStart;
	}

	public Date getShiftEnd() {
		return shiftEnd;
	}

	public void setShiftEnd(Date shiftEnd) {
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
			
	public float getAvarageRating() {
		return avarageRating;
	}

	public void setAvarageRating(float avarageRating) {
		this.avarageRating = avarageRating;
	}

	public List<ReviewDoctor> getReviews() {
		return reviews;
	}

	public void setReviews(List<ReviewDoctor> reviews) {
		this.reviews = reviews;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	public static class Builder extends UserBuilder
	{
		private String type;
		private String insuranceId;
	    private Date shiftStart;
	    private Date shiftEnd;
	    public Clinic clinic;
		
		public Builder(String email)
		{
			super(email);
		}
		

		public Builder withPassword(String password)
		{
			super.withPassword(password);
			
			return this;
		}

		public Builder withName(String name)
		{
			super.withName(name);
			
			return this;
		}
	
		public Builder withSurname(String surname)
		{
			super.withSurname(surname);
			
			return this;
		}
		
		public Builder withCity(String city)
		{
			super.withCity(city);
			
			return this;
		}
		
		public Builder withAddress(String address)
		{
			super.withAddress(address);
			
			return this;
		}
		
		public Builder withState(String state)
		{
			super.withState(state);
			
			return this;
		}
		
		public Builder withPhone(String phone)
		{
			super.withPhone(phone);
			
			return this;
		}
						
		public Builder withInsuranceID(String insuranceId)
		{
			this.insuranceId = insuranceId;
			
			return this;
		}
		
		public Builder withType(String type)
		{
			this.type = type;
			
			return this;
		}
		
		public Builder withShiftStart(Date shiftStart)
		{
			this.shiftStart = shiftStart;
			
			return this;
		}
		
		public Builder withShiftEnd(Date shiftEnd)
		{
			this.shiftEnd = shiftEnd;
			
			return this;
		}
		
		public Builder withClinic(Clinic clinic)
		{
			this.clinic = clinic;
			
			return this;
		}
		
		public Doctor build()
		{
			super.withRole(UserRole.Doctor);
			User user = super.build();
			Doctor d = new Doctor(user);
			d.setType(this.type);
			d.setShiftStart(this.shiftStart);
			d.setShiftEnd(this.shiftEnd);
			d.setInsuranceId(this.insuranceId);
			d.setClinic(this.clinic);
			return d;
		}
	}
}
