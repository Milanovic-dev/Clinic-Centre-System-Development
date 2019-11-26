package dto;

import java.util.List;

import model.Appointment.AppointmentType;

public class AppointmentRequestDTO {

	private String date;
	private String patientEmail;
	private String clinicName;
	private List<String> doctors;
	private String appointmentDescription;
	private AppointmentType type;
	
	public AppointmentRequestDTO()
	{
		super();
	}

	public AppointmentRequestDTO(String date, String patientEmail, String clinicName, List<String> doctors,
			String appointmentDescription, AppointmentType type) {
		super();
		this.date = date;
		this.patientEmail = patientEmail;
		this.clinicName = clinicName;
		this.doctors = doctors;
		this.appointmentDescription = appointmentDescription;
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPatientEmail() {
		return patientEmail;
	}

	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public List<String> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<String> doctors) {
		this.doctors = doctors;
	}

	public String getAppointmentDescription() {
		return appointmentDescription;
	}

	public void setAppointmentDescription(String appointmentDescription) {
		this.appointmentDescription = appointmentDescription;
	}

	public AppointmentType getType() {
		return type;
	}

	public void setType(AppointmentType type) {
		this.type = type;
	}
}
