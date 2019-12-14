package helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
		
	private static DateUtil instance;
	
	public String GetString(Date date,String format)
	{
		DateFormat df = new SimpleDateFormat(format);
		
		return df.format(date);		
	}
	
	public Date GetDate(String date,String format)
	{
		DateFormat df = new SimpleDateFormat(format);
		
		try {
			return df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
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
