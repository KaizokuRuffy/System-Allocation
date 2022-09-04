 package Beans;

import java.io.Serializable;

public class Employee implements Serializable
{
	public static final String[] varName = {"emp_Id", "comp_Id", "emp_Name", "emp_AdhaarId", "emp_Email", "emp_MobileNo",
									"emp_Password", "emp_Shift", "emp_Role", "emp_Dept", "emp_WorkLoc"};
	
	private static final long serialVersionUID = 1L;
	private int emp_Id;
	private String comp_Id;
	private String emp_Name;
	private String emp_AdhaarId;
	private String emp_Email;
	private String emp_MobileNo;
	private String emp_Password;
	private String emp_Shift;
	private String emp_Role;
	private String emp_Dept;
	private String emp_WorkLoc;
	
	
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

	public String getAdhaarId() {
		return emp_AdhaarId;
	}

	public void setAdhaarId(String adhaarId) {
		emp_AdhaarId = adhaarId;
	}

	public String getEmp_Email() {
		return emp_Email;
	}

	public void setEmp_Email(String emp_Email) {
		this.emp_Email = emp_Email;
	}

	public String getEmp_MobileNo() {
		return emp_MobileNo;
	}

	public void setEmp_MobileNo(String emp_MobileNo) {
		this.emp_MobileNo = emp_MobileNo;
	}

	public String getEmp_Password() {
		return new String(emp_Password);
	}

	public void setEmp_Password(String emp_Password) {
		this.emp_Password = new String(emp_Password);
	}

	public String getEmp_Role() {
		return emp_Role;
	}

	public void setEmp_Role(String emp_Role) {
		this.emp_Role = emp_Role;
	}

	public String getEmp_Dept() {
		return emp_Dept;
	}

	public void setEmp_Dept(String emp_Dept) {
		this.emp_Dept = emp_Dept;
	}

	public String getEmp_WorkLoc() {
		return emp_WorkLoc;
	}

	public void setEmp_WorkLoc(String emp_WorkLoc) {
		this.emp_WorkLoc = emp_WorkLoc;
	}

	public String getComp_Id() {
		return comp_Id;
	}

	public void setComp_Id(String comp_Id) {
		this.comp_Id = comp_Id;
	}

	@Override
	public String toString() {
		return "Employee [emp_Id=" + emp_Id + ", comp_Id=" + comp_Id + ", emp_Name=" + emp_Name + ", emp_AdhaarId="
				+ emp_AdhaarId + ", emp_Email=" + emp_Email + ", emp_MobileNo=" + emp_MobileNo + ", emp_Password="
				+ emp_Password + ", emp_shift=" + emp_Shift + ", emp_Role=" + emp_Role + ", emp_Dept=" + emp_Dept
				+ ", emp_WorkLoc=" + emp_WorkLoc + "]";
	}

	public Employee()
	{
		
	}

	public String getEmp_Shift() {
		return emp_Shift;
	}

	public void setEmp_Shift(String emp_Shift) {
		this.emp_Shift = emp_Shift;
	}


	
}
