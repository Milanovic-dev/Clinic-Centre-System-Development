package repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Appointment;
import model.AppointmentRequest;
import model.Hall;

public interface AppointmentRequestRepository  extends JpaRepository<AppointmentRequest,Long>{

	public AppointmentRequest findByDateAndHall(Date date, Hall hall);
	
}
