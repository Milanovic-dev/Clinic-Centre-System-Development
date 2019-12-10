package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Clinic;
import model.Hall;
import model.Priceslist;
import repository.PriceListRepository;


@Service
public class PriceListService {

	@Autowired
	private PriceListRepository priceListRepository;
	
	 public Priceslist findByTypeOfExamination(String typeOfExamination ) 
	 {
	        return priceListRepository.findByTypeOfExamination(typeOfExamination);
	 
	 }
	 public List<Priceslist> findAllByClinic(Clinic c)
	 {
	 	return priceListRepository.findAllByClinic(c);
	 }
	 public List<Priceslist> findAllByPrice(Long price)
	 {
		return priceListRepository.findAllByPrice(price);
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
