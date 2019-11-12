package model;

import java.util.ArrayList;
import java.util.Collection;

public class MedicalRecord {

	private Long id;
	private ArrayList<PatientMedicalReport> reports;
	
	public MedicalRecord()
	{
		super();
		reports = new ArrayList<PatientMedicalReport>();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public ArrayList<PatientMedicalReport> getReports() {
		return reports;
	}

	public void setReports(ArrayList<PatientMedicalReport> reports) {
		this.reports = reports;
	}
	
	
}
