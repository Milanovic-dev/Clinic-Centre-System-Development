package model;

import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.*;


@Entity
public class Patient extends User{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "insuranceId", nullable = false)
	private String insuranceId;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "medicalRecord_id", referencedColumnName = "id")
	private MedicalRecord medicalRecord;
	@OneToMany(fetch = FetchType.LAZY)
	private ArrayList<Appointment> scheduledAppointments;

	public Patient()
	{
		super();
	}
	
	public Patient(String username, String password, String email, String name, String surname, String city,
			String address, String state, String phone) {
		super(username, password, email, name, surname, city, address, state, phone, UserRole.Patient);
		medicalRecord = new MedicalRecord();
		scheduledAppointments = new ArrayList<Appointment>();
	}
	
	public Patient(RegistrationRequest request)
	{
		super(request,UserRole.Patient);
		medicalRecord = new MedicalRecord();
		scheduledAppointments = new ArrayList<Appointment>();
	}

	public String getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public ArrayList<Appointment> getScheduledAppointments() {
		return scheduledAppointments;
	}

	public void setScheduledAppointments(ArrayList<Appointment> scheduledAppointments) {
		this.scheduledAppointments = scheduledAppointments;
	}
	
	
}
