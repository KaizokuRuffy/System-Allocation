package Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Beans.Session;
import DAO.SessionDAO;

public class SessionService 
{
	private SessionDAO sessionDAO = new SessionDAO();
	
	public boolean addSession(Session session)
	{
		
		session.setShift
		(
				((session.getLogIn_Time().compareTo("08:00") >= 0) && 
				(session.getLogIn_Time().compareTo("16:00") <= 0)) ? "Morning" : 
				((session.getLogIn_Time().compareTo("16:00") >= 0) && 
			    (session.getLogIn_Time().compareTo("23:59") <= 0)) ? "Evening" : "Night"
		);
		
		if(sessionDAO.insertInto(session) == 1)
			return true;
		
		return false;
	}
	
	public List<Session> getAllSessions()
	{
		List<Session> sessionList = null;
		
		sessionList = sessionDAO.selectAll();
		return sessionList;
	}
	
	public List<Session> getEmpSession(int emp_Id)
	{
		List<Session> sessionList = null;
		
		sessionList = sessionDAO.select(emp_Id);
		return sessionList;
	}
	
	public boolean updateSession(Session session)
	{
		List<Session> temp = sessionDAO.select(session.getEmp_Id());
		
		for(Session s : temp)
			if(s.getLogIn_Date().equals(session.getLogIn_Date()) && s.getComp_Id().equals(session.getComp_Id()) )
			{
				session.setLogIn_Time(s.getLogIn_Time());
				break;
			}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat SDF = new SimpleDateFormat("yyyy-mm-dd HH:mm");

		Date d1 = null;
		try 
		{
			d1 = SDF.parse(sdf.format(session.getLogIn_Date()) + " " + session.getLogIn_Time());
		} 
		catch (ParseException e1) 
		{
			e1.printStackTrace();
		}
		
		Date d2 = null;
		try 
		{
			d2 = SDF.parse(sdf.format (session.getLogOut_Date()) + " " + session.getLogOut_Time());
		} 
		catch (ParseException e1) 
		{
			e1.printStackTrace();
		}
	
		long diff = (d2.getTime() - d1.getTime());
		session.setTotal_Time((diff/(1000*60*60)) + " Hrs "+ 
				             ((diff%(1000*60*60))/(60000) + " mins"));
		
		if(sessionDAO.updateRecord(session) == 1)
			return true;
		
		return false;
	}
}
