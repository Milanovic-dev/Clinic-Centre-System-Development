package filters;

import dto.UserDTO;
import model.Patient;

public class PatientFilter implements Filter{

	@Override
	public Boolean test(Object o1, Object o2) {
		// TODO Auto-generated method stub
		try {
			Patient p = (Patient)o1;
			UserDTO d = (UserDTO)o2;
			Boolean flag = true;
			if(!d.getName().equals(""))
			{
				if(!p.getName().contains(d.getName()))
				{
					flag = false;
				}
			}
			if(!d.getSurname().equals(""))
			{
				if(!p.getSurname().contains(d.getSurname()))
				{
					flag = false;
				}				
			}
			if(!d.getInsuranceId().equals(""))
			{
				if(!p.getInsuranceId().contains(d.getInsuranceId()))
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
