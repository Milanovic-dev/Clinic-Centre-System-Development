package helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.Appointment;
import model.Doctor;
import model.Hall;

public class Scheduler {

	
	public static List<DateInterval> getFreeIntervals(Doctor doctor, Date day)
	{
		List<DateInterval> intervals = new ArrayList<DateInterval>();
		List<Appointment> apps = doctor.getAppointments();
		List<Date> datesList = new ArrayList<Date>();
		
		for(Appointment app : apps)
		{
			if(!DateUtil.getInstance().isSameDay(app.getDate(), day)) continue;
			
			datesList.add(app.getDate());
			datesList.add(app.getEndDate());
		}
		
		if(datesList.size() == 0)
		{
			intervals.add(new DateInterval(doctor.getShiftStart(),doctor.getShiftEnd()));
			return intervals;
		}
		
		Date[] dates = new Date[datesList.size()];
		dates = datesList.toArray(dates);
		
		Arrays.sort(dates);
		
		Date minDate = doctor.getShiftStart();
		Date maxDate = doctor.getShiftEnd();
		
		return makeIntervals(dates, minDate, maxDate);
	}
	
	public static List<DateInterval> getBusyIntervals(Doctor doctor, Date day)
	{
		List<DateInterval> intervals = new ArrayList<DateInterval>();
		List<Appointment> apps = doctor.getAppointments();
		
		for(Appointment app : apps)
		{
			if(!DateUtil.getInstance().isSameDay(app.getDate(), day)) continue;
			
			intervals.add(new DateInterval(app.getDate(), app.getEndDate()));
		}
			
		return intervals;
	}
	
	public static List<DateInterval> getFreeIntervals(List<Appointment> apps, Date day)
	{
		List<DateInterval> intervals = new ArrayList<DateInterval>();
		List<Date> datesList = new ArrayList<Date>();
		Date minDate = getStartOfDay(day);
		Date maxDate = getEndOfDay(day);
		
		for(Appointment app : apps)
		{		
			if(!DateUtil.getInstance().isSameDay(app.getDate(), day)) continue;

			datesList.add(app.getDate());
			datesList.add(app.getEndDate());
		}
		
		if(datesList.size() == 0)
		{
			intervals.add(new DateInterval(minDate,maxDate));
			return intervals;
		}
		
		Date[] dates = new Date[datesList.size()];
		dates = datesList.toArray(dates);
		
		Arrays.sort(dates);
		
		
		return makeIntervals(dates, minDate, maxDate);
	}
	
	public static List<DateInterval> getBusyIntervals(List<Appointment> apps, Date day)
	{
		List<DateInterval> intervals = new ArrayList<DateInterval>();
	
		for(Appointment app : apps)
		{
			if(!DateUtil.getInstance().isSameDay(app.getDate(), day)) continue;
			
			intervals.add(new DateInterval(app.getDate(), app.getEndDate()));
		}
			
		return intervals;
	}
	
	private static List<DateInterval> makeIntervals(Date[] dates, Date minDate, Date maxDate)
	{
		List<DateInterval> intervals = new ArrayList<DateInterval>();
		
		for(int i = 0 ; i < dates.length ; i++)
		{
			if(i == dates.length - 1)
			{			
				intervals.add(new DateInterval(dates[dates.length - 1], maxDate));
				break;
			}
			
			Date endDate = dates[i];
			Date startDate = dates[i + 1];
			
			
			if(i == 0)
			{
				intervals.add(new DateInterval(minDate, dates[0]));
				continue;
			}
			
			
			if(i < dates.length - 1)
			{
				if(DateUtil.getInstance().getTimeBetween(endDate, startDate) >= 60000)
				{
					intervals.add(new DateInterval(endDate, startDate));
					i++;
				}
			}		
		}
		
		return intervals;
	}
	
	private static Date getStartOfDay(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    int year = calendar.get(Calendar.YEAR);
	    int month = calendar.get(Calendar.MONTH);
	    int day = calendar.get(Calendar.DATE);
	    calendar.set(year, month, day, 0, 0, 0);
	    return calendar.getTime();
	}

	private static Date getEndOfDay(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    int year = calendar.get(Calendar.YEAR);
	    int month = calendar.get(Calendar.MONTH);
	    int day = calendar.get(Calendar.DATE);
	    calendar.set(year, month, day, 23, 59, 59);
	    return calendar.getTime();
	}

	public static Date addHoursToJavaUtilDate(Date date, int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hours);
		return calendar.getTime();
	}

	public static List<DateInterval> getFreeIntervalsForSurgery(List<Appointment> apps, Date day)
	{
		List<DateInterval> intervals = new ArrayList<DateInterval>();
		List<Date> datesList = new ArrayList<Date>();

		for(Appointment app : apps)
		{
			if(!DateUtil.getInstance().isSameDay(app.getDate(), day)) continue;

			datesList.add(app.getDate());
			datesList.add(app.getEndDate());
		}

		if(datesList.size() == 0)
		{
			return intervals;
		}

		Date[] dates = new Date[datesList.size()];
		dates = datesList.toArray(dates);

		Arrays.sort(dates);

		Date minDate = addHoursToJavaUtilDate(getStartOfDay(day),7);
		Date maxDate = addHoursToJavaUtilDate(getStartOfDay(day),20);

		return makeIntervals(dates, minDate, maxDate);
	}
}
