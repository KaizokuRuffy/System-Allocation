package Controller.Util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import Beans.Employee;
import DAO.EmployeeDAO;

public class Param {
	
	public <T> T toPojo(T obj, HttpServletRequest request){
		
		if(obj instanceof Employee) {
			((Employee) obj).setEmp_Id(Integer.parseInt(request.getParameter(Employee.varName[0])));
			((Employee) obj).setComp_Id(request.getParameter(Employee.varName[1]));
			((Employee) obj).setEmp_Name(request.getParameter(Employee.varName[2])); 
			((Employee) obj).setAdhaarId(request.getParameter(Employee.varName[3])); 
			((Employee) obj).setEmp_Email(request.getParameter(Employee.varName[4])); 
			((Employee) obj).setEmp_MobileNo(request.getParameter(Employee.varName[5])); 
			((Employee) obj).setEmp_Password(request.getParameter(Employee.varName[6])); 
			((Employee) obj).setEmp_Shift(request.getParameter(Employee.varName[7])); 
			((Employee) obj).setEmp_Role(request.getParameter(Employee.varName[8])); 
			((Employee) obj).setEmp_Dept(request.getParameter(Employee.varName[9])); 
			((Employee) obj).setEmp_WorkLoc(request.getParameter(Employee.varName[10])); 
		}
		
		return obj;
	}
	
	public <T> Map<String, Object> pojoToMap(T obj) {
		
		Map<String, Object> update = new HashMap<>();
		
		if(obj instanceof Employee) {
			update.put(EmployeeDAO.ColumnName.ID.toString(), ((Employee) obj).getEmp_Id());
			update.put(EmployeeDAO.ColumnName.Adhaar_ID.toString(), ((Employee) obj).getAdhaarId());
			update.put(EmployeeDAO.ColumnName.Comp_Id.toString(), ((Employee) obj).getComp_Id());
			update.put(EmployeeDAO.ColumnName.Department.toString(), ((Employee) obj).getEmp_Dept());
			update.put(EmployeeDAO.ColumnName.Email_ID.toString(), ((Employee) obj).getEmp_Email());
			update.put(EmployeeDAO.ColumnName.Mobile_No.toString(), ((Employee) obj).getEmp_MobileNo());
			update.put(EmployeeDAO.ColumnName.Name.toString(), ((Employee) obj).getEmp_Name());
			update.put(EmployeeDAO.ColumnName.Password.toString(), ((Employee) obj).getEmp_Password());
			update.put(EmployeeDAO.ColumnName.Role.toString(), ((Employee) obj).getEmp_Role());
			update.put(EmployeeDAO.ColumnName.Shift.toString(), ((Employee) obj).getEmp_Shift());
			update.put(EmployeeDAO.ColumnName.Work_Location.toString(), ((Employee) obj).getEmp_WorkLoc());
		}
		
		return update;
	}
	
}
