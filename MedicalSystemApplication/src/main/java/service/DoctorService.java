package service;

import java.util.List;

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
    
    public List<Doctor> findByType(String type)
    {
    	return doctorRepository.findByType(type);
    }
}
