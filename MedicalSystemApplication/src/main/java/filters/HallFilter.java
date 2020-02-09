package filters;

import dto.HallDTO;
import model.Hall;

public class HallFilter implements Filter{

	@Override
	public Boolean test(Object o1, Object o2) {
		// TODO Auto-generated method stub
		
		Hall h = (Hall)o1;
		HallDTO hdto = (HallDTO)o2;
		
		Boolean flag = true;
		
		if(hdto.getName() != null)
		{
			if(!hdto.getName().equals(""))
			{
				if(!h.getName().toLowerCase().contains(hdto.getName().toLowerCase()))
				{
					flag = false;
				}
			}		
		}
		
		if(hdto.getNumber() != 0)
		{
			if(h.getNumber() != hdto.getNumber())
			{
				flag = false;
			}
		}
		
		return flag;
	}

}
