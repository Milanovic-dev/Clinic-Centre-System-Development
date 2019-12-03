package repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Appointment;
import model.AppointmentRequest;
import model.Clinic;
import model.Hall;

public interface AppointmentRequestRepository  extends JpaRepository<AppointmentRequest,Long>{

	public AppointmentRequest findByDateAndHallAndClinic(Date date, Hall hall,Clinic clinic);
	
	public List<AppointmentRequest> findAllByClinic(Clinic clinic);
}
