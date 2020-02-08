package service;

import model.Clinic;
import model.ClinicReview;
import model.Doctor;
import model.Patient;
import model.RegistrationRequest;

import org.hibernate.dialect.lock.PessimisticEntityLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import dto.ReviewDTO;
import helpers.DateUtil;
import repository.ClinicRepository;
import repository.RegistrationRequestRepository;
import repository.UserRepository;

import java.util.List;

import javax.validation.ValidationException;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;
    
    @Autowired
    private UserRepository userRepository;

    public Clinic findByName(String name) {
        return clinicRepository.findByName(name);
    }
    
    public Clinic findByDoctor(Doctor d) {
        return clinicRepository.findByDoctors(d);
    }
    
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void rateClinicSafe(ReviewDTO dto)
    {
    	Clinic clinic = findByName(dto.getClinicName());
    	Patient patient = (Patient) userRepository.findByEmailAndDeleted(dto.getPatientEmail(), false);
    	
    	if(clinic == null || patient == null)
    	{
    		throw new ValidationException("Clinic or patient not found");
    	}
    	
    	ClinicReview cr = new ClinicReview(dto.getRating(), DateUtil.getInstance().now("dd-MM-yyyy"), patient);
    	clinic.getReviews().add(cr);
    	clinic.calculateRating();
    	
    	save(clinic);
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
