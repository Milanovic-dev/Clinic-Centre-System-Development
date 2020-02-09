package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Clinic;
import model.Hall;
import model.Priceslist;
import repository.ClinicRepository;
import repository.PriceListRepository;


@Service
public class PriceListService {

	@Autowired
	private PriceListRepository priceListRepository;
	@Autowired
	private ClinicRepository clinicRepository;
	
	 public Priceslist findByTypeOfExaminationAndClinic(String typeOfExamination, String clinicName) 
	 {		 
		 Clinic clinic = clinicRepository.findByName(clinicName);
		 return priceListRepository.findByTypeOfExaminationAndClinicAndDeleted(typeOfExamination,clinic,false);	 
	 }
	 
	 public Priceslist findByTypeOfExaminationAndClinic(String typeOfExamination, Clinic clinic) 
	 {		 
		 return priceListRepository.findByTypeOfExaminationAndClinicAndDeleted(typeOfExamination,clinic,false);	 
	 }
	 public List<Priceslist> findAllByClinic(Clinic c)
	 {
	 	return priceListRepository.findAllByClinic(c);
	 }
	 public List<Priceslist> findAllByPrice(Long price)
	 {
		return priceListRepository.findAllByPrice(price);
	 }
	 
	 public List<Priceslist> findAllByTypeOfExamination(String typeOfExamination)
	 {
		 return priceListRepository.findAllByTypeOfExamination(typeOfExamination);
	 }
	 
	public Priceslist findByTypeOfExaminationAndDeleted(String typeOfExamination, Boolean deleted)
	{
		return priceListRepository.findByTypeOfExaminationAndDeleted(typeOfExamination, deleted);
	}

    public void save(Priceslist pricesList)
    {
    	priceListRepository.save(pricesList);
    }
    
    public void delete(Priceslist pricesList)
    {
    	priceListRepository.delete(pricesList);
    }
    

	public List<Priceslist> findAll() {
		return priceListRepository.findAll();
	}
}
