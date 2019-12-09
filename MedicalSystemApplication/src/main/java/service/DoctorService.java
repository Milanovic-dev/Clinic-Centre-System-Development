package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Clinic;
import model.Doctor;
import repository.DoctorRepository;

@Service
public class DoctorService {

	@Autowired
	private DoctorRepository doctorRepository;

    public Doctor findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }
}
