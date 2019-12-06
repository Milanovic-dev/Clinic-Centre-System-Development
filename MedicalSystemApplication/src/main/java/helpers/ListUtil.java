package helpers;

import java.util.ArrayList;
import java.util.List;

import dto.UserDTO;
import model.User;

public class ListUtil {
	
	private static ListUtil instance;
	
	public ArrayList<UserDTO> distinct(ArrayList<UserDTO> list)
	{
		List<Integer> indices = new ArrayList<Integer>();
		
		for(int i = 0 ; i < list.size() ; i++)
		{
			for(int j = 0 ; j < list.size(); j++)
			{
				if(list.get(i).getName() == list.get(j).getName())
				{
					if(i != j)
					{
						indices.add(j);
					}
				}
			}
		}
		
		for(int i = 0 ; i < indices.size() ; i++)
		{
			list.remove(indices.get(i).intValue());
		}
		
		return list;
	}
	
	public static ListUtil getInstance()
	{
		if(instance == null)
		{
			instance = new ListUtil();
		}
		
		return instance;
	}
	
}
