package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class ClinicCenter 
{
	@OneToMany(fetch = FetchType.LAZY)
	private List<Clinic> clinics;
	
	@OneToOne(fetch = FetchType.LAZY)
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


	public List<Clinic> getClinics() {
		return clinics;
	}
	public void setClinics(List<Clinic> clinics) {
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
