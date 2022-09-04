package Beans;

import java.io.Serializable;

public class Admin implements Serializable {
	private static final long serialVersionUID = 1L;
	private int admin_Id;
	private String admin_Name;
	private String admin_Email;
	private String admin_ContactNo;
	private String admin_Password;

	public int getAdmin_Id() {
		return admin_Id;
	}

	public void setAdmin_Id(int admin_Id) {
		this.admin_Id = admin_Id;
	}

	public String getAdmin_Name() {
		return admin_Name;
	}

	public void setAdmin_Name(String admin_Name) {
		this.admin_Name = admin_Name;
	}

	public String getAdmin_Email() {
		return admin_Email;
	}

	public void setAdmin_Email(String admin_Email) {
		this.admin_Email = admin_Email;
	}

	public String getAdmin_ContactNo() {
		return admin_ContactNo;
	}

	public void setAdmin_ContactNo(String admin_ContactNo) {
		this.admin_ContactNo = admin_ContactNo;
	}

	public String getAdmin_Password() {
		return new String(admin_Password);
	}

	public void setAdmin_Password(String admin_Password) {
		this.admin_Password = new String(admin_Password);
	}

	public Admin() {

	}

	public Admin(String admin_Name, String admin_Email, String admin_ContactNo, String admin_Password) {
		super();
		this.admin_Name = admin_Name;
		this.admin_Email = admin_Email;
		this.admin_ContactNo = admin_ContactNo;
		this.admin_Password = new String(admin_Password);
	}

	public Admin(int admin_Id, String admin_Name, String admin_Email, String admin_ContactNo, String admin_Password) {
		super();
		this.admin_Id = admin_Id;
		this.admin_Name = admin_Name;
		this.admin_Email = admin_Email;
		this.admin_ContactNo = admin_ContactNo;
		this.admin_Password = new String(admin_Password);
	}

	@Override
	public String toString() {
		return "Admin [Id=" + admin_Id + ", Name=" + admin_Name + ", Email=" + admin_Email
				+ ", Contact No=" + admin_ContactNo + ", Password=" + admin_Password + "]";
	}

}