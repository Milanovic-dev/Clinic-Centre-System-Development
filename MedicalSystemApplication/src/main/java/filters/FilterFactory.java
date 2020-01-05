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
		
		if(filterType.equalsIgnoreCase("HALL"))
		{
			return new HallFilter();
		}
		
		if(filterType.equalsIgnoreCase("PATIENT"))
		{
			return new PatientFilter();
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
