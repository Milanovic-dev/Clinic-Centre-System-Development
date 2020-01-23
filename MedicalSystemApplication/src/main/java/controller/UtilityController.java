package controller;

import java.util.Calendar;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.MonthDTO;
import dto.WeekDTO;

@RestController
@RequestMapping(value = "api/utility")
public class UtilityController 
{
	
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
	
	
}
