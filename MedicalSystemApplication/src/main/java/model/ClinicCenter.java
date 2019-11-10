package model;

import java.util.ArrayList;

public class ClinicCenter 
{
	private ArrayList<Clinic> clinics;
	private CentreAdmin centreAdmin;
	
	
	
	public ClinicCenter() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public ClinicCenter(ArrayList<Clinic> clinics) {
		super();
		this.clinics = clinics;
		this.clinics = new ArrayList<Clinic>();
	}


	public ArrayList<Clinic> getClinics() {
		return clinics;
	}
	public void setClinics(ArrayList<Clinic> clinics) {
		this.clinics = clinics;
	}
	public CentreAdmin getCentreAdmin() {
		return centreAdmin;
	}
	public void setCentreAdmin(CentreAdmin centreAdmin) {
		this.centreAdmin = centreAdmin;
	}
	@Override
	public String toString() {
		return "ClinicCenter [clinics=" + clinics + ", centreAdmin=" + centreAdmin + "]";
	}
	
	
}
