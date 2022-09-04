package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Beans.Computer;
import Beans.Employee;
import DAO.Util.JDBC_Connection;
import DAO.Util.TableName;
import DAO.Util.Util;

public class EmployeeDAO {
	public static enum ColumnName {
		ID, Name, Adhaar_ID("`Adhaar ID`"), Email_ID("`Email ID`"), Mobile_No("`Mobile No`"),
		Password, Role, Department, Work_Location("`Work Location`"), Shift, Comp_Id("`Computer ID`");

		String value;

		ColumnName(String value) {
			this.value = value;
		}

		ColumnName() {
		}
	}

	private static String createTable = "CREATE TABLE IF NOT EXISTS " + TableName.getEmployee() +
			" ( " + ColumnName.ID + " INT NOT NULL AUTO_INCREMENT, "
			+ ColumnName.Comp_Id.value + "varchar(255), "
			+ ColumnName.Name + " varchar(255), "
			+ ColumnName.Adhaar_ID.value + " varchar(255) UNIQUE, "
			+ ColumnName.Email_ID.value + " varchar(255) UNIQUE, "
			+ ColumnName.Mobile_No.value + " varchar(255) UNIQUE, "
			+ ColumnName.Password + " varchar(255), "
			+ ColumnName.Shift + " varchar(255), "
			+ ColumnName.Role + " varchar(255), "
			+ ColumnName.Department + " varchar(255), "
			+ ColumnName.Work_Location.value + " varchar(255), "
			+ "UNIQUE (" + ColumnName.Comp_Id.value + "," + ColumnName.Shift + "),"
			+ " PRIMARY KEY (" + ColumnName.ID + "))";

	public int noOfRows() {
		String query = "select count(*)"
				+ "from " + TableName.getEmployee();
		
		Connection conn = JDBC_Connection.getConnection();

		try (Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query)) {
			int count = 0;

			if (rs.next()) {
				count = Integer.parseInt(rs.getString(1));
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public Map<String, String> getPassword(int emp_Id) {

		String query = " SELECT " + ColumnName.Password + ", " + ColumnName.Name
				+ " FROM " + TableName.getEmployee()
				+ " WHERE " + ColumnName.ID + " = " + emp_Id;

		Connection conn = JDBC_Connection.getConnection();

		try (PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				String emp_Password = new String(rs.getString(ColumnName.Password.toString()));
				String emp_Name = rs.getString(ColumnName.Name.toString());

				Map<String, String> map = new HashMap<>();
				map.put("name", emp_Name);
				map.put("password", emp_Password);

				return map;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public int insertInto(Employee emp) {

		String query = " INSERT INTO " + TableName.getEmployee() +
				" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Connection conn = JDBC_Connection.getConnection();

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			if (emp.getEmp_Id() != 0)
				ps.setInt(1, emp.getEmp_Id());
			else
				ps.setString(1, null);
			
			System.out.println(emp.getComp_Id());
			if(emp.getComp_Id() == null || emp.getComp_Id().equals(""))
			{
				List<Computer> computerList = new ComputerDAO().getUnallocateSystems(emp.getEmp_Shift(), "No");
				if(computerList.size() == 0)
					return 0;
				else
				emp.setComp_Id(computerList.get(0).getComp_Id());
			}

			ps.setString(2, emp.getComp_Id());
			ps.setString(3, emp.getEmp_Name());
			ps.setString(4, emp.getAdhaarId());
			ps.setString(5, emp.getEmp_Email());
			ps.setString(6, emp.getEmp_MobileNo());
			ps.setString(7, emp.getEmp_Password());
			ps.setString(8, emp.getEmp_Shift());
			ps.setString(9, emp.getEmp_Role());
			ps.setString(10, emp.getEmp_Dept());
			ps.setString(11, emp.getEmp_WorkLoc());

			int rs = ps.executeUpdate();

			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public Employee selectRecord(int emp_Id) {

		String query = " SELECT * "
				+ " FROM " + TableName.getEmployee()
				+ " WHERE " + ColumnName.ID + " = " + emp_Id;

		Connection conn = JDBC_Connection.getConnection();

		try (PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				Employee emp = new Employee();
				emp.setEmp_Id(rs.getInt(ColumnName.ID.toString()));
				emp.setComp_Id(rs.getString(ColumnName.Comp_Id.value.replaceAll("`", "")));
				emp.setEmp_Name(rs.getString(ColumnName.Name.toString()));
				emp.setAdhaarId(rs.getString(ColumnName.Adhaar_ID.value.replaceAll("`", "")));
				emp.setEmp_Email(rs.getString(ColumnName.Email_ID.value.replaceAll("`", "")));
				emp.setEmp_MobileNo(rs.getString(ColumnName.Mobile_No.value.replaceAll("`", "")));
				emp.setEmp_Password(rs.getString(ColumnName.Password.toString()));
				emp.setEmp_Shift(rs.getString(ColumnName.Shift.toString()));
				emp.setEmp_Role(rs.getString(ColumnName.Role.toString()));
				emp.setEmp_Dept(rs.getString(ColumnName.Department.toString()));
				emp.setEmp_WorkLoc(rs.getString(ColumnName.Work_Location.value.replaceAll("`", "")));

				return emp;
			}

		} catch (SQLException e) {
			JDBC_Connection.close();
			e.printStackTrace();
		}
		return null;
	}

	public Employee selectRecord(String emp_Email) {

		String query = " SELECT * "
				+ " FROM " + TableName.getEmployee()
				+ " WHERE " + ColumnName.Email_ID.value + " = '" + emp_Email + "'";

		Connection conn = JDBC_Connection.getConnection();

		try (PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				Employee emp = new Employee();
				emp.setEmp_Id(rs.getInt(ColumnName.ID.toString()));
				emp.setComp_Id(rs.getString(ColumnName.Comp_Id.value.replaceAll("`", "")));
				emp.setEmp_Name(rs.getString(ColumnName.Name.toString()));
				emp.setAdhaarId(rs.getString(ColumnName.Adhaar_ID.value.replaceAll("`", "")));
				emp.setEmp_Email(rs.getString(ColumnName.Email_ID.value.replaceAll("`", "")));
				emp.setEmp_MobileNo(rs.getString(ColumnName.Mobile_No.value.replaceAll("`", "")));
				emp.setEmp_Password(rs.getString(ColumnName.Password.toString()));
				emp.setEmp_Shift(rs.getString(ColumnName.Shift.toString()));
				emp.setEmp_Role(rs.getString(ColumnName.Role.toString()));
				emp.setEmp_Dept(rs.getString(ColumnName.Department.toString()));
				emp.setEmp_WorkLoc(rs.getString(ColumnName.Work_Location.value.replaceAll("`", "")));

				return emp;
			}

		} catch (SQLException e) {
			JDBC_Connection.close();
			e.printStackTrace();
		}
		return null;
	}

	public List<Employee> selectAll() {

		String query = " SELECT * "
				+ " FROM " + TableName.getEmployee();

		Connection conn = JDBC_Connection.getConnection();

		try (PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			List<Employee> employeeList = new ArrayList<>();

			while (rs.next()) {
				Employee emp = new Employee();
				emp.setEmp_Id(rs.getInt(ColumnName.ID.toString()));
				emp.setComp_Id(rs.getString(ColumnName.Comp_Id.value.replaceAll("`", "")));
				emp.setEmp_Name(rs.getString(ColumnName.Name.toString()));
				emp.setAdhaarId(rs.getString(ColumnName.Adhaar_ID.value.replaceAll("`", "")));
				emp.setEmp_Email(rs.getString(ColumnName.Email_ID.value.replaceAll("`", "")));
				emp.setEmp_MobileNo(rs.getString(ColumnName.Mobile_No.value.replaceAll("`", "")));
				emp.setEmp_Password(new String(rs.getString(ColumnName.Password.toString())));
				emp.setEmp_Shift(rs.getString(ColumnName.Shift.toString()));
				emp.setEmp_Role(rs.getString(ColumnName.Role.toString()));
				emp.setEmp_Dept(rs.getString(ColumnName.Department.toString()));
				emp.setEmp_WorkLoc(rs.getString(ColumnName.Work_Location.value.replaceAll("`", "")));

				employeeList.add(emp);
			}

			return employeeList;
		} catch (SQLException e) {
			JDBC_Connection.close();
			e.printStackTrace();
		}

		return null;
	}

	public int updateRecord(int emp_Id, Map<String, Object> update) {
		Util util = new Util();
		String query = "UPDATE " + TableName.getEmployee()
				+ "SET " + util.updateQuery(update)
				+ " WHERE " + ColumnName.ID + " = " + emp_Id;
		
		Connection conn = JDBC_Connection.getConnection();

		try (PreparedStatement ps = conn.prepareStatement(query)) 
		{
			int rs = ps.executeUpdate();
			
			return rs;
		}
		catch (SQLException e) 
		{
			JDBC_Connection.close();
			e.printStackTrace();
		}

		return 0;
	}

	@Deprecated
	public void deleteRecord(String emp_Id) {
		String query = "DELETE FROM ?"
				+ "WHERE ? = ? ";
	}

	public static String getCreateTable() {
		return createTable;
	}

	public static void setCreateTable(String createTable) {
		EmployeeDAO.createTable = createTable;
	}

}
