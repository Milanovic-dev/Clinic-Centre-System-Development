package filters;

import dto.DoctorDTO;
import model.Doctor;

public class DoctorFilter implements Filter{

	@Override
	public Boolean test(Object o1, Object o2) {
		// TODO Auto-generated method stub
		
		try 
		{
			Boolean flag = true;
			Doctor d = (Doctor)o1;
			DoctorDTO dto = (DoctorDTO)o2;
			
			if(!dto.getUser().getName().equals(""))
			{
				if(!d.getName().toLowerCase().contains(dto.getUser().getName().toLowerCase()))
				{
					flag = false;
				}
			}
			
			if(!dto.getUser().getSurname().equals(""))
			{
				if(!d.getSurname().toLowerCase().contains(dto.getUser().getSurname().toLowerCase()))
				{
					flag = false;
				}
			}
			
			if(dto.getAvarageRating() != 0)
			{
				if(Math.abs(d.getAvarageRating() - dto.getAvarageRating()) > 1f)
				{
					flag = false;
				}			
			}
			
			if(!dto.getType().equals(""))
			{
				if(!d.getType().toLowerCase().contains(dto.getType().toLowerCase()))
				{
					flag = false;
				}
			}
			
			return flag;
			
		}
		catch(Exception e) 
		{
			return false;
		}
		
	}

}
