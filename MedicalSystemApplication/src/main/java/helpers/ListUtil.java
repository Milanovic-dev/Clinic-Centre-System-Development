package helpers;

import java.util.ArrayList;
import model.*;
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
	
	public Boolean containsWithEmail(ArrayList<UserDTO> list,String email)
	{
		for(int i = 0 ; i < list.size() ; i++)
		{
			if(list.get(i).getEmail().equals(email))
			{
				return true;
			}
		}
		
		return false;		
	}
	
	public int containsWithType(List<Priceslist> list, String type)
	{
		for(int i = 0 ; i < list.size() ; i++)
		{
			if(list.get(i).getTypeOfExamination().equals(type))
			{
				return i;
			}
		}
		
		return -1;
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
