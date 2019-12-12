package filters;

public class FilterFactory {

	private static FilterFactory instance;
	
	
	public Filter get(String filterType)
	{
		if(filterType == null)
		{
	         return null;
	    }	
		
		if(filterType.equalsIgnoreCase("CLINIC"))
		{
			return new ClinicFilter();
		}
		
		return null;
	}
	
	public static FilterFactory getInstance()
	{
		if(instance == null)
		{
			instance = new FilterFactory();
		}
		
		return instance;
	}
	
}
