package repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Appointment;
import model.Hall;
import model.Patient;
import model.User;

public interface AppointmentRepository extends JpaRepository<Appointment,Long>{

	public Appointment findByDateAndHall(Date date, Hall hall);
	
	public List<Appointment> findAllByPatient(Patient p);
}
