package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Patient;
import repository.PatientRepository;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepository repo;
	
	public Patient save(Patient patient)
	{
		return repo.save(patient);
	}
}
