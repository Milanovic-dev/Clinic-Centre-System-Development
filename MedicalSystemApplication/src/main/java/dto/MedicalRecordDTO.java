package dto;

import java.util.ArrayList;
import java.util.List;

import model.Diagnosis;
import model.MedicalRecord;
import model.PatientMedicalReport;
import model.MedicalRecord.BloodType;

public class MedicalRecordDTO {


	private List<PatientMedicalReportDTO> reports = new ArrayList<>();
	private List<String> alergies;
	private String weight;
	private BloodType bloodType;
	private String height;


	public MedicalRecordDTO() {
		super();
		// TODO Auto-generated constructor stub
		reports = new ArrayList<>();
	}

	public MedicalRecordDTO(List<String> alergies, String weight, BloodType bloodType,
			String height) {
		super();
		this.reports = new ArrayList<PatientMedicalReportDTO>();
		this.alergies = alergies;
		this.weight = weight;
		this.bloodType = bloodType;
		this.height = height;
	}


	public MedicalRecordDTO(MedicalRecord record)
	{
		this.alergies = record.getAlergies();
		this.weight = record.getWeight();
		this.height = record.getHeight();
		this.bloodType = record.getBloodType();

		for(PatientMedicalReport pmr : record.getReports())
		{
			PatientMedicalReportDTO dto = new PatientMedicalReportDTO(pmr);
			this.reports.add(dto);
		}

	}


	public List<PatientMedicalReportDTO> getReports() {
		return reports;
	}

	public void setReports(List<PatientMedicalReportDTO> reports) {
		this.reports = reports;
	}



	public List<String> getAlergies() {
		return alergies;
	}

	public void setAlergies(List<String> alergies) {
		this.alergies = alergies;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public BloodType getBloodType() {
		return bloodType;
	}

	public void setBloodType(BloodType bloodType) {
		this.bloodType = bloodType;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

}
