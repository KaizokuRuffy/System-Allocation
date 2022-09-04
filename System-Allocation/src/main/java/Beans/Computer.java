package Beans;

import java.io.Serializable;

public class Computer implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String comp_Id;
	private String MAC;
	private String comp_Password;
	private String available;
	private String working;
	private String backup;
	private String comp_Loc;
	private String model;
	private Integer year;
	
	public String getComp_Id() {
		return comp_Id;
	}

	public void setComp_Id(String comp_Id) {
		this.comp_Id = comp_Id;
	}

	public String getMAC() {
		return MAC;
	}

	public void setMAC(String mAC) {
		MAC = mAC;
	}

	public String getComp_Password() {
		return comp_Password;
	}

	public void setComp_Password(String comp_Password) {
		this.comp_Password = comp_Password;
	}
	
	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getWorking() {
		return working;
	}

	public void setWorking(String working) {
		this.working = working;
	}

	public String getBackup() {
		return backup;
	}

	public void setBackup(String backup) {
		this.backup = backup;
	}

	public String getComp_Loc() {
		return comp_Loc;
	}

	public void setComp_Loc(String comp_Loc) {
		this.comp_Loc = comp_Loc;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Computer()
	{
		
	}

	@Override
	public String toString() {
		return "Computer [comp_Id=" + comp_Id + ", MAC=" + MAC + ", comp_Password=" + comp_Password + ", available="
				+ available + ", working=" + working + ", backup=" + backup + ", comp_Loc=" + comp_Loc + ", model="
				+ model + ", year=" + year + "]";
	}	
}
