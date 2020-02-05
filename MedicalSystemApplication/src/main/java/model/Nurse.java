package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import dto.NurseDTO;
import helpers.DateUtil;
import helpers.UserBuilder;
import model.Doctor.Builder;
import model.User.UserRole;
@Entity
public class Nurse extends User{


	@Column(name = "type", nullable = true)
	private String type;

	@Column(name = "shiftStart")
    private Date shiftStart;
	
    @Column(name = "shiftEnd")
    private Date shiftEnd;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Clinic clinic;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<Prescription> prescriptions;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Vacation> vacations;


    public Nurse() {
    }

    public Nurse(String insuranceID) {
        super.setInsuranceId(insuranceID);
        this.prescriptions = new ArrayList<>();
		this.vacations = new ArrayList<>();
        this.setIsFirstLog(true);
    }

    public Nurse(String password, String email, String name, String surname, String city, String address, String state, String phone, String insuranceID) {
        super(password, email, name, surname, city, address, state, phone, UserRole.Nurse);
        super.setInsuranceId(insuranceID);
        this.prescriptions = new ArrayList<>();
		this.vacations = new ArrayList<>();
        this.setIsFirstLog(true);
    }
    
    public Nurse(User user)
    {
    	super(user);   
    	this.prescriptions = new ArrayList<>();
    	this.vacations = new ArrayList<>();
        this.setIsFirstLog(true);
    }

	public Nurse(NurseDTO dto)
	{
		super(dto.getUser());
		this.setRole(UserRole.Doctor);
		this.setIsFirstLog(true);
		this.shiftStart = DateUtil.getInstance().getDate(dto.getShiftStart(), "HH:mm");
		this.shiftEnd = DateUtil.getInstance().getDate(dto.getShiftEnd(), "HH:mm");
		this.vacations = new ArrayList<>();
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Prescription> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(List<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}


	public static class Builder extends UserBuilder
	{
		private String insuranceId;
	    private Date shiftStart;
	    private Date shiftEnd;
	    public Clinic clinic;
	    public String type;
				
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

		public Builder withType(String type)
		{
			this.type = type;

			return this;
		}
		
		public Nurse build()
		{
			super.withRole(UserRole.Nurse);
			User user = super.build();
			Nurse n = new Nurse(user);
			n.setClinic(this.clinic);
			n.setInsuranceId(this.insuranceId);
			n.setShiftStart(this.shiftStart);
			n.setShiftEnd(this.shiftEnd);
			n.setType(this.type);
			return n;
		}
	}
   
}
