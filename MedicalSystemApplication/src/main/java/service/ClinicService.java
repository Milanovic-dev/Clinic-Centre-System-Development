package service;

import model.Clinic;
import model.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public void save(Clinic clinic)
    {
        clinicRepository.save(clinic);
    }

	public List<Clinic> findAll() {
		return clinicRepository.findAll();
	}
}
