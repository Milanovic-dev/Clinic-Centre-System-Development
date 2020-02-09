package helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.Appointment;

public class DateUtil {
		
	private static DateUtil instance;
	public static final long HOUR_MILLIS = 3600000;
	
	
	public String getString(Date date,String format)
	{
		DateFormat df = new SimpleDateFormat(format);
		
		return df.format(date);		
	}
	
	
	public Date getDate(String date,String format)
	{
		//date = date.strip();
		DateFormat df = new SimpleDateFormat(format);
		
		try {
			return df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Date getDate(long millis, String format)
	{
		Calendar cal = Calendar.getInstance();
		
		cal.setTimeInMillis(millis);
		Date date = cal.getTime();

		return getDate(getString(date,format) , format);
	}
	
	public Boolean overlappingInterval(List<DateInterval> dl1, List<DateInterval> dl2)
	{
		for(DateInterval di1 : dl1)
		{
			for(DateInterval di2 : dl2)
			{
				if(overlappingInterval(di1,di2))
				{
					return true;
				}
			}
		}
		
		return false;
	}
		
	public Boolean overlappingInterval(Date start1, Date end1, Date start2, Date end2)
	{
		return overlappingInterval(new DateInterval(start1,end1), new DateInterval(start2,end2));
	}
	
	public Boolean overlappingInterval(Appointment app1, Appointment app2)
	{
		return overlappingInterval(app1.getDate(),app1.getEndDate(),app2.getDate(),app2.getEndDate());
	}
	
	public Date transformToDay(Date dateDay, Date dateHours)
	{
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(dateDay);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(dateHours);
		
		cal1.set(Calendar.HOUR_OF_DAY, cal2.get(Calendar.HOUR_OF_DAY));
		cal1.set(Calendar.MINUTE, cal2.get(Calendar.MINUTE));
		
		return cal1.getTime();
	}
	
	public Boolean overlappingInterval(DateInterval d1, DateInterval d2)
	{
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		Calendar c3 = Calendar.getInstance();
		Calendar c4 = Calendar.getInstance();
		
		c1.setTime(d1.getStart());
		c2.setTime(d1.getEnd());
		c3.setTime(d2.getStart());
		c4.setTime(d2.getEnd());
							
		return c1.getTime().before(c4.getTime()) && c2.getTime().after(c3.getTime());
	}
	
	
	public Boolean insideInterval(Date testDate,DateInterval di)
	{
		Date startDate = di.getStart();
		Date endDate = di.getEnd();
		
		
		return !(testDate.before(startDate) || testDate.after(endDate));
	}
	
	
	public long getTimeBetween(Date d1, Date d2)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(d1);
		long start = cal.getTimeInMillis();
		cal.clear();
		cal.setTime(d2);
		long end = cal.getTimeInMillis();
		return Math.abs(end - start);
	}
	
	public Boolean isSameDay(Date date1, Date date2)
	{
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
	
		return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && 
	            c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
	            c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
	}
	
	public Date now(String format)
	{
		Calendar cal = Calendar.getInstance();
		
		return getDate(new SimpleDateFormat(format).format(cal.getTime()), format);
	}
		
	public static DateUtil getInstance()
	{
		if(instance == null)
		{
			instance = new DateUtil();
		}
		
		return instance;
	}

	public Date addDays(Date date, int days)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}
}
