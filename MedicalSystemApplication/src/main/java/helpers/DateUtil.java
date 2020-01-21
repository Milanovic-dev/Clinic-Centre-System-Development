package helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
		
	private static DateUtil instance;
	
	public String getString(Date date,String format)
	{
		DateFormat df = new SimpleDateFormat(format);
		
		return df.format(date);		
	}
	
	
	public Date getDate(String date,String format)
	{
		date = date.strip();
		DateFormat df = new SimpleDateFormat(format);
		
		try {
			return df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Boolean isSameDay(Date date1, Date date2)
	{
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
		                  cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
	}
		
	public static DateUtil getInstance()
	{
		if(instance == null)
		{
			instance = new DateUtil();
		}
		
		return instance;
	}
}
