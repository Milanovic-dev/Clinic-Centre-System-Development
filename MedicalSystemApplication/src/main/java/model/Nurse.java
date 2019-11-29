package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
@Entity
public class Nurse extends User{
	

	@Column(name = "insuranceId", nullable = true)
    private String insuranceID;
	
	@Column(name = "shiftStart", nullable = true)
    private String shiftStart;
	
    @Column(name = "shiftEnd", nullable = true)
    private String shiftEnd;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Clinic clinic;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<Prescription> prescriptions;

    public Nurse() {
    }

    public Nurse(String insuranceID) {
        this.insuranceID = insuranceID;
        this.prescriptions = new ArrayList<Prescription>();
        this.setIsFirstLog(true);
    }

    public Nurse(String username, String password, String email, String name, String surname, String city, String address, String state, String phone, String insuranceID) {
        super(username, password, email, name, surname, city, address, state, phone, UserRole.Nurse);
        this.insuranceID = insuranceID;
        this.prescriptions = new ArrayList<Prescription>();
        this.setIsFirstLog(true);
    }

    public String getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(String insuranceID) {
        this.insuranceID = insuranceID;
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
   
}
