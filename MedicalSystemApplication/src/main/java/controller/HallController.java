package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.ClinicDTO;
import model.Clinic;
import model.Hall;
import service.ClinicService;
import service.HallService;

@RestController
@RequestMapping(value = "api/hall")
public class HallController {
	@Autowired
    private HallService hallService;
	
	
	
	
	@GetMapping(value = "/getAll")
	public ResponseEntity<List<Hall>> getHalls()
	{
	   List<Hall> halls = hallService.findAll();
	    	
	    if(halls == null)
	    {
	    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }

	    return new ResponseEntity<>(halls,HttpStatus.OK);
	}
	
	 @GetMapping(value="/{number}")
	    public ResponseEntity<Hall> getHallByNumber(@PathVariable("number") int number)
	    {
	    	Hall hall= hallService.findByNumber(number);
	    	if(hall == null)
	    	{
	    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    	}
	    	
	    	
	    	return new ResponseEntity<>(hall,HttpStatus.OK);
	    }
	 @DeleteMapping(value="/delete/{number}")
	 public ResponseEntity<Void> deleteHallByNumber(@PathVariable("number") int number)
	 {
		Hall hall = hallService.findByNumber(number);
		if(hall == null )
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		hallService.delete(hall);
		
		return new ResponseEntity<>(HttpStatus.OK);
	 }
	    
	  @PostMapping(value ="/addHall", consumes = "application/json")
	    public ResponseEntity<Void> add(@RequestBody Hall hall, HttpServletRequest request)
	    {
	        HttpHeaders header = new HttpHeaders();

	        Hall h = hallService.findByNumber(hall.getNumber());

	        if(h == null) {
	            Hall newHall = new Hall(hall.getClinic(),hall.getNumber());
	            hallService.save(newHall);
	            
	        } else {
	            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
	        }


	        return new ResponseEntity<>(HttpStatus.OK);
	    }
}
