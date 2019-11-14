package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ClinicCenter 
{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Clinic> clinics;

	@OneToMany(fetch = FetchType.LAZY)
	private List<CentreAdmin> centreAdmins;



	public ClinicCenter() {
		super();
		this.clinics = new ArrayList<>();
		this.centreAdmins = new ArrayList<>();
	}

	
	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	}

	public List<Clinic> getClinics() {
		return clinics;
	}
	public void setClinics(List<Clinic> clinics) {
		this.clinics = clinics;
	}

	public List<CentreAdmin> getCentreAdmins() {
		return centreAdmins;
	}

	public void setCentreAdmins(List<CentreAdmin> centreAdmins) {
		this.centreAdmins = centreAdmins;
	}
}
