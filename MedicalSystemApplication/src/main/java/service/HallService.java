package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Clinic;
import model.Hall;
import repository.ClinicRepository;
import repository.HallRepository;

@Service
public class HallService {
	
	@Autowired
	private HallRepository hallRepository;
	
	 public Hall findByNumberAndClinic(int number, Clinic clinic) {
	        return hallRepository.findByNumberAndClinicAndDeleted(number, clinic, false);
	 
	    }

	    public void save(Hall hall)
	    {
	        hallRepository.save(hall);
	    }
	    
	    public void delete(Hall hall)
	    {
	    	hallRepository.delete(hall);
	    }
	    

		public List<Hall> findAll() {
			return hallRepository.findAll();
		}
		public List<Hall> findAllByClinic(Clinic c) {
			return hallRepository.findByClinic(c);
		}
}
