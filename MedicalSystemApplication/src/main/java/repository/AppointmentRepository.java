package repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import model.Appointment;
import model.Clinic;
import model.Doctor;
import model.Hall;
import model.Patient;
import model.User;

public interface AppointmentRepository extends JpaRepository<Appointment,Long>{

	public Appointment findByDateAndHallAndClinic(Date date, Hall hall, Clinic clinic);
	public List<Appointment> findAllByHallAndClinic(Hall hall,Clinic clinic);
	public List<Appointment> findAllByPatient(Patient p);
	
	@Query(value = "select * from appointment where id = any(select appointments_id from doctor_appointments where doctor_id=?1)",nativeQuery = true)
	public List<Appointment> findAllByDoctor(Long doctorID);
	public List<Appointment> findAllByHall(Hall hall);
	public List<Appointment> findAllByClinic(Clinic c);
	public List<Appointment> findAllByPredefined(Boolean predefined);
}
