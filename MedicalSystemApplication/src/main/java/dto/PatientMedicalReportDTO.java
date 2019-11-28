package dto;

import java.util.Date;
import java.util.List;

import model.Clinic;
import model.PatientMedicalReport;
import model.Prescription;

public class PatientMedicalReportDTO {

    private String description;
    private Date dateAndTime;
    private String doctorEmail;
    private String clinicName;
	private List<Prescription> prescription;
	
	
	public PatientMedicalReportDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PatientMedicalReportDTO(String description, Date dateAndTime, String doctorEmail, String clinicEmail,
			List<Prescription> prescription) {
		super();
		this.description = description;
		this.dateAndTime = dateAndTime;
		this.doctorEmail = doctorEmail;
		this.clinicName = clinicEmail;
		this.prescription = prescription;
	}
	
	public PatientMedicalReportDTO(PatientMedicalReport report)
	{
		this.description = report.getDescription();
		this.dateAndTime = report.getDateAndTime();
		this.clinicName = report.getClinic().getName();
		this.doctorEmail = report.getDoctor().getEmail();
		this.prescription = report.getPrescription();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public String getDoctorEmail() {
		return doctorEmail;
	}

	public void setDoctorEmail(String doctorEmail) {
		this.doctorEmail = doctorEmail;
	}

	public List<Prescription> getPrescription() {
		return prescription;
	}

	public void setPrescription(List<Prescription> prescription) {
		this.prescription = prescription;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	
	
	
}
