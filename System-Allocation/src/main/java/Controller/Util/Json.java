package Controller.Util;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import Beans.Admin;

@SuppressWarnings("unused")
public class Json 
{	
	public <T> String toJSON(T obj)
	{
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		return json;
	}
	
	public <T>	String toJSON(List<T> list)
	{
		Gson gson = new Gson();
		String json = gson.toJson(list);
		return json;
	}
	
	public <T> T toPojo(HttpServletRequest request, Class<T> clazz)
	{
		String json = null;
		
		try 
		{
			json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		Gson gson = new Gson();
		T t = null;
		
		try {
			t = gson.fromJson(json, clazz);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return t;
	}
	
	public <T> T toPojo(String json, Class<T> clazz)
	{
		
		Gson gson = new Gson();
		T t = gson.fromJson(json, clazz);
		
		return t;
	}
}
