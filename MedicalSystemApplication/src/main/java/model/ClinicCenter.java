package model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table
public class ClinicCenter 
{
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "clinic_id")
	private ArrayList<Clinic> clinics;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
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
