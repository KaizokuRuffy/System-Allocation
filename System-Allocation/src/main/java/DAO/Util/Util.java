package DAO.Util;

import java.util.Map;
import java.util.Set;

public class Util {
	
	public String replaceQuestionMark(String query, String[] replaceWith) {
	
		String[] temp = query.split("\\?");
		
		String Query = "";
		
		for(int i = 0; i < temp.length; i++)
		{
			if(i < replaceWith.length)
				Query += temp[i] + replaceWith[i];
			else
				Query += temp[i];
		}
	
		return Query;	
	}
	
	public String updateQuery(Map<String, Object> update) {
		
		Set<String> keys = update.keySet();
		String set = "";
		
		for(String key : keys) {
			
			if(update.get(key) instanceof Number)
				set += key + " = " + (Number)update.get(key) + " ,";
			else 
				set += key + " = '" + (String)update.get(key) + "' ,";
		}
		set.substring(0, set.length() - 1);
		return set;
	}
	
}
