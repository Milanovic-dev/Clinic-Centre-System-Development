
package model;

import javax.persistence.*;

import helpers.UserBuilder;
import model.Doctor.Builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class ClinicAdmin extends User {

	
    @ManyToOne(fetch = FetchType.EAGER)
    private Clinic clinic;

    @OneToMany(fetch = FetchType.LAZY)
    private List<AppointmentRequest> appointmentRequests;

    @OneToMany(fetch = FetchType.LAZY)
    private  List<VacationRequest> vacationRequests;

    public ClinicAdmin(){
    	setRole(UserRole.ClinicAdmin);
    	this.setIsFirstLog(true);
    }

    public ClinicAdmin(String password, String email, String name, String surname, String city, String address, String state, String phone, Clinic clinic) {
        super(password, email, name, surname, city, address, state, phone, UserRole.ClinicAdmin);
        this.clinic = clinic;
        this.appointmentRequests = new ArrayList<>();
        this.vacationRequests = new ArrayList<>();
        this.setIsFirstLog(true);
    }

    public ClinicAdmin(User user)
    {
    	super(user);
    	this.appointmentRequests = new ArrayList<>();
        this.vacationRequests = new ArrayList<>();
        this.setIsFirstLog(true);
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public List<AppointmentRequest> getAppointmentRequests() {
        return appointmentRequests;
    }

    public void setAppointmentRequests(List<AppointmentRequest> appointmentRequests) {
        this.appointmentRequests = appointmentRequests;
    }

    public List<VacationRequest> getVacationRequests() {
        return vacationRequests;
    }

    public void setVacationRequests(List<VacationRequest> vacationRequests) {
        this.vacationRequests = vacationRequests;
    }
    
    public static class Builder extends UserBuilder
    {
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
		
		public Builder withClinic(Clinic clinic)
		{
			this.clinic = clinic;
			
			return this;
		}
		
		public ClinicAdmin build()
		{
			super.withRole(UserRole.ClinicAdmin);
			User user = super.build();
			ClinicAdmin a = new ClinicAdmin(user);
			a.setClinic(this.clinic);
			return a;
		}
    }
}

