package Beans;

import java.io.Serializable;
import java.util.Date;

public class Session implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int emp_Id;
	private String emp_Name;
	private String comp_Id;
	private Date logIn_Date;
	private Date logOut_Date;
	private String logIn_Time;
	private String logOut_Time;
	private String total_Time;
	private String shift;
	
	public Date getLogIn_Date() {
		return logIn_Date;
	}

	public void setLogIn_Date(Date logIn_Date) {
		this.logIn_Date = logIn_Date;
	}

	public Date getLogOut_Date() {
		return logOut_Date;
	}

	public void setLogOut_Date(Date logOut_Date) {
		this.logOut_Date = logOut_Date;
	}

	public String getComp_Id() {
		return comp_Id;
	}

	public void setComp_Id(String comp_Id) {
		this.comp_Id = comp_Id;
	}

	public int getEmp_Id() {
		return emp_Id;
	}

	public void setEmp_Id(int emp_Id) {
		this.emp_Id = emp_Id;
	}

	public String getEmp_Name() {
		return emp_Name;
	}

	public void setEmp_Name(String emp_Name) {
		this.emp_Name = emp_Name;
	}

	public String getLogIn_Time() {
		return logIn_Time;
	}

	public void setLogIn_Time(String logIn_Time) {
		this.logIn_Time = logIn_Time;
	}

	public String getLogOut_Time() {
		return logOut_Time;
	}

	public void setLogOut_Time(String logOut_Time) {
		this.logOut_Time = logOut_Time;
	}

	public String getTotal_Time() {
		return total_Time;
	}

	public void setTotal_Time(String total_Time) {
		this.total_Time = total_Time;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public Session()
	{
		
	}
	
	public Session(int emp_Id, String comp_Id, Date logIn_Date, 
										String logIn_Time) {
		super();
		this.emp_Id = emp_Id;
		this.comp_Id = comp_Id;
		this.logIn_Date = logIn_Date;
		this.logIn_Time = logIn_Time;
	}

	public Session(int emp_Id, String emp_Name, String comp_Id, Date logIn_Date, Date logOut_Date, String logIn_Time,
			String logOut_Time, String total_Time, String shift) {
		super();
		this.emp_Id = emp_Id;
		this.emp_Name = emp_Name;
		this.comp_Id = comp_Id;
		this.logIn_Date = logIn_Date;
		this.logOut_Date = logOut_Date;
		this.logIn_Time = logIn_Time;
		this.logOut_Time = logOut_Time;
		this.total_Time = total_Time;
		this.shift = shift;
	}

	@Override
	public String toString() {
		return "Session [emp_Id=" + emp_Id + ", emp_Name=" + emp_Name + ", comp_Id=" + comp_Id + ", logIn_Date="
				+ logIn_Date + ", logOut_Date=" + logOut_Date + ", logIn_Time=" + logIn_Time + ", logOut_Time="
				+ logOut_Time + ", total_Time=" + total_Time + ", shift=" + shift + "]";
	}
}
