package model;

import java.util.ArrayList;
import java.util.Collection;

public class MedicalRecord {

	private Collection<PatientMedicalReport> reports;
	
	public MedicalRecord()
	{
		super();
	}

	public Collection<PatientMedicalReport> getReports() {
		return reports;
	}

	public void setReports(Collection<PatientMedicalReport> reports) {
		this.reports = reports;
	}
	
	
}
