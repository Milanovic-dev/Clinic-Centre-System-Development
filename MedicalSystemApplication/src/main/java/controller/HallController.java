package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.ClinicDTO;
import dto.HallDTO;
import filters.FilterFactory;
import filters.HallFilter;
import helpers.DateUtil;
import model.Appointment;
import model.Clinic;
import model.Hall;
import model.RegistrationRequest;
import model.User;
import service.AppointmentService;
import service.ClinicService;
import service.HallService;

@RestController
@RequestMapping(value = "api/hall")
public class HallController {
	@Autowired
    private HallService hallService;
	
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private ClinicService clinicService;
	
	@GetMapping(value = "/getHallBusyDays/{clinicName}/{hallNumber}")
	public ResponseEntity<List<Date>> getHallBusyFromHallAndClinic(@PathVariable("clinicName") String clinicName,@PathVariable ("hallNumber") int hallNumber)
	{
		Clinic c = clinicService.findByName(clinicName);
		Hall h = hallService.findByNumber(hallNumber);
		List<Appointment> app = appointmentService.findAllByHallAndClinic(h, c);
		List<Date> busyHall = new ArrayList<Date>();
		
		if(c == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		if(h == null)
		{
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		if(app == null)
		{
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		for(Appointment a : app)
		{
			busyHall.add(a.getDate());
		}
		return new ResponseEntity<>(busyHall,HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/getAll")
	public ResponseEntity<List<HallDTO>> getHalls()
	{
	   List<Hall> halls = hallService.findAll();
	   List<HallDTO> ret = new ArrayList<HallDTO>();
	   if(halls == null)
	   {
		   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	   }
	   
	   for(Hall hall : halls)
	   {
		   if(!hall.getDeleted())
		   {
			   HallDTO dto = new HallDTO(hall);
			   ret.add(dto);
		   }		   
	   }

	   return new ResponseEntity<>(ret,HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/getAllByFilter",consumes = "application/json")
	public ResponseEntity<List<HallDTO>> getHallsFilter(@RequestBody HallDTO dto)
	{

		HttpHeaders header = new HttpHeaders();
		Clinic c = clinicService.findByName(dto.getClinicName());		
		
		if(c == null)
		{
				header.set("responseText", "clinic");
			    return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);			
		}	
		
		List<HallDTO> ret = new ArrayList<HallDTO>();
		HallFilter filter = (HallFilter) FilterFactory.getInstance().get("hall");
		
		Date startDate = DateUtil.getInstance().getDate(dto.getDate(), "dd-MM-yyyy");
		
		List<Appointment> appointments = appointmentService.findAllByDate(startDate);
		
		for(Hall hall : c.getHalls())
		{
			if(dto.getDate() != null)
			{
				long hours = 0; //miliisecs
				
				for(Appointment app : appointments)
				{
					hours += DateUtil.getInstance().getHoursBetween(app.getDate(), app.getEndDate());
				}
				
				
				if(hours > 23 * DateUtil.HOUR_MILLIS)
				{
					continue;
				}			
			}
			
			if(!hall.getDeleted())
			{
				if(filter.test(hall, dto))
				{
					ret.add(new HallDTO(hall));
				}				
			}
		}
		
		
		
		return new ResponseEntity<>(ret,HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllByClinic/{clinicName}")
	public ResponseEntity<List<HallDTO>> getHalls(@PathVariable("clinicName") String clinicName)
	{
		HttpHeaders header = new HttpHeaders();
		Clinic c = clinicService.findByName(clinicName);
		
		if(c == null)
		{
				header.set("responseText", "clinic");
			    return new ResponseEntity<>(header,HttpStatus.NOT_FOUND);			
		}	
	   
	   List<HallDTO> ret = new ArrayList<HallDTO>();	   
	   for(Hall hall : c.getHalls())
	   {
		   if(!hall.getDeleted())
		   {
			   HallDTO dto = new HallDTO(hall);
			   ret.add(dto);
		   }		   
	   }

	   return new ResponseEntity<>(ret,HttpStatus.OK);
	}
	
	@DeleteMapping(value="/deleteHall/{number}")
	public ResponseEntity<Void> deleteHall(@PathVariable ("number") int number)
	{
		Hall hall = hallService.findByNumber(number);
		
		if(hall == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<Appointment> list = appointmentService.findAllByHall(hall);
		
		if(list != null)
		{
			if(list.size() > 0)
			{
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}		
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		hall.setDeleted(true);
		hallService.save(hall);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@PutMapping(value="/changeHall/{oldNumber}/{newNumber}/{newName}")
	public ResponseEntity<Void> changeHall(@PathVariable("oldNumber") int oldNumber,@PathVariable("newNumber") int newNumber,@PathVariable("newName") String newName)
	{
		Hall hall = hallService.findByNumber(oldNumber);
		
		if(hall == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		hall.setNumber(newNumber);
		hall.setName(newName);
		hallService.save(hall);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	 @GetMapping(value="/get/{number}")
	    public ResponseEntity<HallDTO> getHallByNumber(@PathVariable("number") int number)
	    {
	    	Hall hall= hallService.findByNumber(number);
	    	if(hall == null)
	    	{
	    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    	}
	    	
	    	if(hall.getDeleted())
	    	{
	    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    	}
	    	
	    	return new ResponseEntity<>(new HallDTO(hall),HttpStatus.OK);
	    }
	 
	    
	  @PostMapping(value ="/addHall", consumes = "application/json")
	    public ResponseEntity<Void> add(@RequestBody HallDTO hall)
	    {
	        HttpHeaders header = new HttpHeaders();

	        Hall h = hallService.findByNumber(hall.getNumber());
	        Clinic clinic = clinicService.findByName(hall.getClinicName());

	        if(clinic == null)
	        {
	        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        		
	        if(h == null) {
	            Hall newHall = new Hall(clinic,hall.getNumber(),hall.getName());
	            hallService.save(newHall);
	            clinic.getHalls().add(newHall);
	            clinicService.save(clinic);
	            
	        } else {
	            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
	        }


	        return new ResponseEntity<>(HttpStatus.OK);
	    }
}
