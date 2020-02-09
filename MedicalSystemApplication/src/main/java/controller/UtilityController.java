package controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.DateIntervalDTO;
import dto.MonthDTO;
import dto.WeekDTO;
import helpers.DateInterval;
import helpers.DateUtil;
import helpers.Scheduler;
import model.Appointment;
import model.Clinic;
import model.Doctor;
import model.Hall;
import service.AppointmentService;
import service.ClinicService;
import service.HallService;
import service.UserService;

@RestController
@RequestMapping(value = "api/utility")
public class UtilityController 
{
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value="/date/getWeekInfo")
	public ResponseEntity<WeekDTO> getWeekInfo()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		Date weekStart = cal.getTime();
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		Date weekEnd = cal.getTime();
			
		return new ResponseEntity<WeekDTO>(new WeekDTO(weekStart, weekEnd), HttpStatus.OK);
	}
	
	@GetMapping(value="/date/getMonthInfo")
	public ResponseEntity<MonthDTO> getMonthInfo()
	{
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date monthStart = cal.getTime();
		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, 1);  
        cal.set(Calendar.DAY_OF_MONTH, 1);  
        cal.add(Calendar.DATE, -1);  
	
		Date monthEnd = cal.getTime();
		return new ResponseEntity<>(new MonthDTO(monthStart, monthEnd), HttpStatus.OK);
	}
	
	@GetMapping(value="/testDate/{date1}/{date2}/{date3}/{date4}")
	public ResponseEntity<Void> testDate(@PathVariable("date1") String d1, @PathVariable("date2") String d2,@PathVariable("date3") String d3, @PathVariable("date4") String d4)
	{
		DateUtil util = DateUtil.getInstance();
		Date date1 = util.getDate(d1,"dd-MM-yyyy HH:mm");
		Date date2 = util.getDate(d2, "dd-MM-yyyy HH:mm");
		Date date3 = util.getDate(d3,"dd-MM-yyyy HH:mm");
		Date date4 = util.getDate(d4, "dd-MM-yyyy HH:mm");
		
		DateInterval di1 = new DateInterval(date1,date2);
		DateInterval di2 = new DateInterval(date3,date4);
		
		if(util.overlappingInterval(di1,di2))
		{
			return new ResponseEntity<>(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/getBusyTime/{doctor}")
	public ResponseEntity<List<DateIntervalDTO>> getBusy(@PathVariable("doctor") String doctorEmail)
	{
		Doctor d = (Doctor) userService.findByEmailAndDeleted(doctorEmail, false);
		
		List<DateInterval> intervals =  Scheduler.getFreeIntervals(d, DateUtil.getInstance().getDate("21-01-2020", "dd-mm-yyyy"));
		List<DateIntervalDTO> dtos = new ArrayList<DateIntervalDTO>();
		
		for(DateInterval di : intervals)
		{
			dtos.add(new DateIntervalDTO(di, "HH:mm"));
		}
		
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
	
	@Autowired
	private HallService hallService;
	
	@Autowired
	private AppointmentService apService;
	
	@Autowired
	private ClinicService clinicService;
	
	@GetMapping(value="/hall/getBusyTime/{hallNumber}/{date}/{clinicName}")
	public ResponseEntity<List<DateIntervalDTO>> getBusyHall(@PathVariable("hallNumber") int num, @PathVariable("date") String date,@PathVariable("clinicName") String clinicName)
	{
		Clinic clinic = clinicService.findByName(clinicName);
		
		if(clinic == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
		
		Hall hall = hallService.findByNumberAndClinic(num, clinic);
		
		List<Appointment> apps = apService.findAllByHall(hall);
		
		List<DateInterval> intervals = Scheduler.getBusyIntervals(apps, DateUtil.getInstance().getDate(date, "dd-mm-yyyy"));
		List<DateIntervalDTO> dtos = new ArrayList<DateIntervalDTO>();
		
		for(DateInterval di : intervals)
		{
			dtos.add(new DateIntervalDTO(di, "HH:mm"));
		}
		
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
}
