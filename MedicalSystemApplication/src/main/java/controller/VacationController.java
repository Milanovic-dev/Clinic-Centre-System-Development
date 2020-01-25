package controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.VacationDTO;
import helpers.DateUtil;
import model.Appointment;
import model.Doctor;
import model.Nurse;
import model.User;
import model.VacationRequest;
import service.AppointmentService;
import service.ClinicService;
import service.UserService;
import service.VacationRequestService;

@RestController
@RequestMapping(value = "api/vacation")
public class VacationController {
	
	
	@Autowired
    private UserService userService;
    
    @Autowired
    private ClinicService clinicService;
    
	@Autowired
    private VacationRequestService vacationRequestService;
	
	@Autowired 
	private AppointmentService appointmentService;

	@PostMapping(value ="/checkAvailability/{clinicName}")
	public ResponseEntity<Boolean> checkAvailabillity(@RequestBody VacationDTO vdto, @PathVariable("clinicName") String clinicName)
	{
		User user = userService.findByEmailAndDeleted(vdto.getUserEmail(), false);

    	if(user == null)
    	{
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);		
    	}
    	
    	Date vacationStart = DateUtil.getInstance().getDate(vdto.getStartDate(), "dd-MM-yyyy");
		Date vacationEnd = DateUtil.getInstance().getDate(vdto.getEndDate(), "dd-MM-yyyy");
    	
    	List<VacationRequest> requests  = vacationRequestService.findAllByUser(user);
  	 	
    	if(requests != null)
    	{
    		for(VacationRequest request : requests)
    		{
    			Boolean flag = true;
    			
    			if(request.getStartDate().equals(vacationStart) && request.getEndDate().equals(vacationEnd))
    			{
    				flag = false;
    			}
    			
    			if(vacationStart.after(request.getStartDate()) && vacationStart.before(request.getEndDate()))
    			{
    				flag = false;
    			}
    			
    			if(vacationEnd.after(request.getStartDate()) && vacationEnd.before(request.getEndDate()))
    			{
    				flag = false;
    			}
    			
    			if(vacationStart.before(request.getStartDate()) && vacationEnd.after(request.getEndDate()))
    			{
    				flag = false;
    			}
    			
    			return new ResponseEntity<>(flag, HttpStatus.OK);
    		}
    	}
    	
    	if(user instanceof Doctor)
    	{
    		Doctor doctor = (Doctor)user;
    		
    		List<Appointment> appointments = doctor.getAppointments();
    		
    		for(Appointment app : appointments)
    		{
    			Date date = app.getDate();
    			    			 			
    			if(date.after(vacationStart) && date.before(vacationEnd))
    			{
    				return new ResponseEntity<>(false, HttpStatus.OK);
    			}
    		}
    	}
    	else if(user instanceof Nurse)
    	{
    		//TODO
    	}
    	
    	return new ResponseEntity<>(true, HttpStatus.OK);
	}
	
	@PostMapping(value="/makeVacationRequest/{clinicName}")
    public ResponseEntity<Void> makeVacationRequest(@RequestBody VacationDTO vdto,@PathVariable ("clinicName") String clinicName)
    {
    	User user = userService.findByEmailAndDeleted(vdto.getUserEmail(), false);

    	if(user == null)
    	{
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);		
    	}
    	    	
    	VacationRequest vr = new VacationRequest();
    	vr.setStartDate(DateUtil.getInstance().getDate(vdto.getStartDate(), "dd-MM-yyyy"));
    	vr.setEndDate(DateUtil.getInstance().getDate(vdto.getEndDate(), "dd-MM-yyyy"));
    	vr.setUser(user);
    	   	
    	vacationRequestService.save(vr);
		return new ResponseEntity<>(HttpStatus.CREATED);  	
    }
	
}
