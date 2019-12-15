package filters;

import dto.ClinicFilterDTO;
import model.Clinic;

public class ClinicFilter implements Filter{


	@Override
	public Boolean test(Object o1, Object o2) {
		// TODO Auto-generated method stub
		try
		{
			Clinic c = (Clinic)o1;
			ClinicFilterDTO d = (ClinicFilterDTO)o2;
			
			Boolean flag = true;
			
			if(!d.getName().equals(""))
			{
				if(!c.getName().contains(d.getName()))
				{
					flag = false;
				}				
			}
			
			if(!d.getAddress().equals(""))
			{
				if(!c.getAddress().contains(d.getAddress()))
				{
					flag = false;
				}				
			}
			
			if(!d.getState().equals(""))
			{
				if(!c.getState().contains(d.getState()))
				{
					flag = false;
				}				
			}
			
			if(!d.getCity().equals(""))
			{			
				if(!c.getCity().contains(d.getCity()))
				{
					flag = false;
				}
			}
			
			
			
			if(d.getRating() != 0)
			{
				if(Math.abs(c.calculateRating() - d.getRating()) > 1f)
				{
					flag = false;
				}			
			}
							
			return flag;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		return false;
	}
	
	
}
