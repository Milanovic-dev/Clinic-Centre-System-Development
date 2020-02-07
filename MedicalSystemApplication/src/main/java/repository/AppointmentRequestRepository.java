package repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Appointment;
import model.AppointmentRequest;
import model.Clinic;
import model.Hall;
import model.Patient;

public interface AppointmentRequestRepository  extends JpaRepository<AppointmentRequest,Long>{

	AppointmentRequest findByDateAndHallAndClinic(Date date, Hall hall,Clinic clinic);
	
	AppointmentRequest findByDateAndPatientAndClinic(Date date, Patient patient, Clinic clinic);
	List<AppointmentRequest> findAllByPatient(Patient patient);
	List<AppointmentRequest> findAllByClinic(Clinic clinic);

	List<AppointmentRequest> findAll();

}
