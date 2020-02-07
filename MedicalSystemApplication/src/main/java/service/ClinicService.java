package service;

import model.Clinic;
import model.Doctor;
import model.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import repository.ClinicRepository;
import repository.RegistrationRequestRepository;

import java.util.List;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    public Clinic findByName(String name) {
        return clinicRepository.findByName(name);
    }
    
    public Clinic findByDoctor(Doctor d) {
        return clinicRepository.findByDoctors(d);
    }
    

    public void save(Clinic clinic)
    {
        clinicRepository.save(clinic);
    }
    
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Clinic> findAllSafe()
    {
    	return clinicRepository.findAll();
    }

	public List<Clinic> findAll() {
		return clinicRepository.findAll();
	}
}
