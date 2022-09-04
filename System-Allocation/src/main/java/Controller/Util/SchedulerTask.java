package Controller.Util;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SchedulerTask
{
	public void schedule(TimerTask t, Long delay)
	{
		 Timer timer = new Timer();
		 timer.schedule(t, delay); 
	}
	public void schedule(TimerTask t, Date date)
	{
		 Timer timer = new Timer();
		 timer.schedule(t, date); 
	}
}