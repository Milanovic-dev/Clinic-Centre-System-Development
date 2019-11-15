package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class PatientMedicalReport {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "reportDescription", nullable = true)
    private String description;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="appointment_id")
	private Appointment appointment;
	
	@ManyToMany
    @JoinTable(name = "patientMedicalReports_prescriptions", joinColumns = @JoinColumn(name = "patientMedicalReport_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "prescription_id", referencedColumnName = "id"))
	private List<Prescription> prescription;
	
	public PatientMedicalReport(Appointment appointment, String description) {
		super();
		this.appointment = appointment;
		this.description = description;
		this.prescription = new ArrayList<Prescription>();
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Prescription> getPrescription() {
		return prescription;
	}

	public void setPrescription(List<Prescription> prescription) {
		this.prescription = prescription;
	}
	
	
	
	
}
