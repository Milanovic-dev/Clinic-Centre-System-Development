package dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.*;

public class PatientMedicalReportDTO {

    private String description;
    private Date dateAndTime;
    private String doctorEmail;
    private String clinicName;
    private List<String> diagnosis = new ArrayList<>();
	private PrescriptionDTO prescription;
	private String patientEmail;
	private long id;
	
	
	public PatientMedicalReportDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PatientMedicalReportDTO(String description, Date dateAndTime, String doctorEmail, String clinicEmail,
			PrescriptionDTO prescription, String patientEmail) {
		super();
		this.description = description;
		this.dateAndTime = dateAndTime;
		this.doctorEmail = doctorEmail;
		this.clinicName = clinicEmail;
		this.prescription = prescription;
		this.patientEmail = patientEmail;
	}
	
	public PatientMedicalReportDTO(PatientMedicalReport report)
	{
		PrescriptionDTO dto = new PrescriptionDTO();
		this.description = report.getDescription();
		this.dateAndTime = report.getDateAndTime();
		this.clinicName = report.getClinic().getName();
		this.doctorEmail = report.getDoctor().getEmail();
		this.prescription = dto;
		this.diagnosis = new ArrayList<>();
		for(Diagnosis d : report.getDiagnosis())
		{
			diagnosis.add(d.getName());
		}
		this.patientEmail = report.getPatient().getEmail();
		this.id = report.getId();
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

	public PrescriptionDTO getPrescription() {
		return prescription;
	}

	public void setPrescription(PrescriptionDTO prescription) {
		this.prescription = prescription;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public List<String> getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(List<String> diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getPatientEmail() {
		return patientEmail;
	}

	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
