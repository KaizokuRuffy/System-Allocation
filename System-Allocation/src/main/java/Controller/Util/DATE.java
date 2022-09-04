package Controller.Util;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class DATE 
{
	public static Date getDate(int hh, int mm, int ss)
	{
		Calendar calender = Calendar.getInstance();
		calender.set(Calendar.HOUR_OF_DAY, hh);
		calender.set(Calendar.MINUTE, mm);
		calender.set(Calendar.SECOND, ss);
		
		return calender.getTime();
	}
	
	public static String getShift() {
		LocalTime date = LocalTime.now();
		String time = (date.getHour() < 10 ? "0" + date.getHour() : "" + date.getHour() )
							+ ":" + (date.getMinute() < 10 ? "0" + date.getMinute() : "" + date.getMinute());
		
		return ((time.compareTo("08:00") >= 0) && 
				(time.compareTo("16:00") <= 0)) ? "Morning" : 
				((time.compareTo("16:00") >= 0) && 
			    (time.compareTo("23:59") <= 0)) ? "Evening" : "Night";
	}
	public static String getShift(String time) {
		
		return ((time.compareTo("08:00") >= 0) && 
				(time.compareTo("16:00") <= 0)) ? "Morning" : 
				((time.compareTo("16:00") >= 0) && 
			    (time.compareTo("23:59") <= 0)) ? "Evening" : "Night";
	}
	
}
