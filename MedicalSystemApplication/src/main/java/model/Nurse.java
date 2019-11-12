package model;

import java.util.ArrayList;

public class Nurse extends User{
	
    private String insuranceID;
    private ArrayList<Prescription> prescriptions;

    public Nurse() {
    }

    public Nurse(String insuranceID) {
        this.insuranceID = insuranceID;
        this.prescriptions = new ArrayList<Prescription>();
    }

    public Nurse(String username, String password, String email, String name, String surname, String city, String address, String state, String phone, String insuranceID) {
        super(username, password, email, name, surname, city, address, state, phone, UserRole.Nurse);
        this.insuranceID = insuranceID;
        this.prescriptions = new ArrayList<Prescription>();
    }

    public String getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(String insuranceID) {
        this.insuranceID = insuranceID;
    }

	public ArrayList<Prescription> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(ArrayList<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

    
}
