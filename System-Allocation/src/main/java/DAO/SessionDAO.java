package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Beans.Session;
import DAO.Util.JDBC_Connection;
import DAO.Util.TableName;

public class SessionDAO {
	static enum ColumnName {
		Emp_ID("`Employee ID`"), Comp_ID("`Computer ID`"), LogIn_Date("`LogIn Date`"),
		LogOut_Date("`LogOut Date`"), LogIn_Time("`LogIn Time`"),
		LogOut_Time("`LogOut Time`"), Total_Time("`Total Time`"), Shift, SNO;

		String value;

		ColumnName(String value) {
			this.value = value;
		}

		ColumnName() {
		}
	}

	private static String createTable = "CREATE TABLE IF NOT EXISTS " + TableName.getSession()
			+ " ( " + ColumnName.SNO + " INT AUTO_INCREMENT, "
			+ ColumnName.Emp_ID.value + " INT NOT NULL, "
			+ ColumnName.Comp_ID.value + " varchar(255) NOT NULL, "
			+ ColumnName.LogIn_Date.value + " DATE NOT NULL, "
			+ ColumnName.LogOut_Date.value + " DATE, "
			+ ColumnName.LogIn_Time.value + " varchar(255), "
			+ ColumnName.LogOut_Time.value + " varchar(255), "
			+ ColumnName.Total_Time.value + " varchar(255), "
			+ ColumnName.Shift + " varchar(255), "
			+ "UNIQUE (" + ColumnName.Comp_ID.value + "," + ColumnName.LogIn_Date.value + "," + ColumnName.Shift + "),"
			+ " PRIMARY KEY (" + ColumnName.SNO + "," + ColumnName.Emp_ID.value + ","
			+ ColumnName.Comp_ID.value + "," + ColumnName.LogIn_Date.value + ") )";

	public static int noOfRows() {
		String query = "select count(*)"
				+ "from " + TableName.getSession();

		try (Connection conn = JDBC_Connection.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query)) {
			int count = 0;

			if (rs.next()) {
				count = Integer.parseInt(rs.getString(0));
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public int insertInto(Session session) {
		String query = "INSERT INTO " + TableName.getSession() + " VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = JDBC_Connection.getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, session.getEmp_Id());
			ps.setString(2, session.getComp_Id());
			ps.setObject(3, session.getLogIn_Date());
			ps.setDate(4, null);
			ps.setString(5, session.getLogIn_Time());
			ps.setString(6, null);
			ps.setString(7, null);
			ps.setString(8, session.getShift());

			int rs = ps.executeUpdate();

			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public List<Session> selectAll() {

		System.out.println("Fetching all(Session) details");

		String query = "SELECT " + TableName.getSession() + ".*, "
				+ TableName.getEmployee() + "." + EmployeeDAO.ColumnName.Name.toString()
				+ " FROM " + TableName.getSession() + " , " + TableName.getEmployee()
				+ " WHERE " + TableName.getSession() + "." + ColumnName.Emp_ID.value
				+ " = " + TableName.getEmployee() + "." + EmployeeDAO.ColumnName.ID.toString();

		try (Connection conn = JDBC_Connection.getConnection();
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			List<Session> sessionList = new ArrayList<>();

			while (rs.next()) {
				Session session = new Session();
				session.setEmp_Id(rs.getInt(ColumnName.Emp_ID.value.replaceAll("`", "")));
				session.setEmp_Name(rs.getString(EmployeeDAO.ColumnName.Name.toString()));
				session.setComp_Id(rs.getString(ColumnName.Comp_ID.value.replaceAll("`", "")));
				session.setLogIn_Date(rs.getDate(ColumnName.LogIn_Date.value.replaceAll("`", "")));
				session.setLogOut_Date(rs.getDate(ColumnName.LogOut_Date.value.replaceAll("`", "")));
				session.setLogIn_Time(rs.getString(ColumnName.LogIn_Time.value.replaceAll("`", "")));
				session.setLogOut_Time(rs.getString(ColumnName.LogOut_Time.value.replaceAll("`", "")));
				session.setTotal_Time(rs.getString(ColumnName.Total_Time.value.replaceAll("`", "")));
				session.setShift(rs.getString(ColumnName.Shift.toString()));

				sessionList.add(session);
			}

			return sessionList;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Session> select(int emp_Id) {

		System.out.println("Fetching Employee '" + emp_Id + "' session details");

		String query = "SELECT " + TableName.getSession() + ".*, "
				+ TableName.getEmployee() + "." + EmployeeDAO.ColumnName.Name.toString()
				+ " FROM " + TableName.getSession() + " , " + TableName.getEmployee()
				+ " WHERE " + TableName.getSession() + "." + ColumnName.Emp_ID.value
				+ " = " + TableName.getEmployee() + "." + EmployeeDAO.ColumnName.ID.toString()
				+ " AND " + TableName.getEmployee() + "." + EmployeeDAO.ColumnName.ID.toString()
				+ " = '" + emp_Id + "' ";

		Connection conn = JDBC_Connection.getConnection();

		try (PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			List<Session> sessionList = new ArrayList<>();

			while (rs.next()) {
				Session session = new Session();
				session.setEmp_Id(rs.getInt(ColumnName.Emp_ID.value.replaceAll("`", "")));
				session.setEmp_Name(rs.getString(EmployeeDAO.ColumnName.Name.toString()));
				session.setComp_Id(rs.getString(ColumnName.Comp_ID.value.replaceAll("`", "")));
				session.setLogIn_Date(rs.getDate(ColumnName.LogIn_Date.value.replaceAll("`", "")));
				session.setLogOut_Date(rs.getDate(ColumnName.LogOut_Date.value.replaceAll("`", "")));
				session.setLogIn_Time(rs.getString(ColumnName.LogIn_Time.value.replaceAll("`", "")));
				session.setLogOut_Time(rs.getString(ColumnName.LogOut_Time.value.replaceAll("`", "")));
				session.setTotal_Time(rs.getString(ColumnName.Total_Time.value.replaceAll("`", "")));
				session.setShift(rs.getString(ColumnName.Shift.toString()));

				sessionList.add(session);

			}

			return sessionList;
		} catch (SQLException e) {
			JDBC_Connection.close();
			e.printStackTrace();
		}

		return null;
	}

	public int updateRecord(Session session) {

		String query = "UPDATE " + TableName.getSession()
				+ "SET " + ColumnName.LogOut_Date.value + " = ? , "
				+ ColumnName.LogOut_Time.value + " = ?, " + ColumnName.Total_Time.value + " = ? "
				+ " WHERE " + ColumnName.Emp_ID.value + " = ? "
				+ " AND " + ColumnName.Comp_ID.value + " = ? "
				+ " AND " + ColumnName.LogIn_Date.value + " = ? ";


		try (Connection conn = JDBC_Connection.getConnection();
				PreparedStatement ps = conn.prepareStatement(query);) {


			ps.setObject(1, session.getLogOut_Date());

			ps.setString(2, session.getLogOut_Time());

			ps.setString(3, session.getTotal_Time());

			ps.setInt(4, session.getEmp_Id());

			ps.setString(5, session.getComp_Id());
			ps.setObject(6, session.getLogIn_Date());

			int rs = ps.executeUpdate();


			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Deprecated
	public void selectRecord(int emp_Id, int comp_Id, Date logIn_Date) throws SQLException {

		System.out.println("Fetching " + comp_Id + " " + emp_Id + " "
				+ logIn_Date + " (Session) details : ");

		String query = "SELECT " + TableName.getSession() + ".*, "
				+ TableName.getEmployee() + "." + EmployeeDAO.ColumnName.Name.toString()
				+ " FROM " + TableName.getSession() + " , " + TableName.getEmployee()
				+ " WHERE " + TableName.getSession() + "." + ColumnName.Emp_ID.value
				+ " = " + TableName.getEmployee() + "." + EmployeeDAO.ColumnName.ID.toString()
				+ " AND " + ColumnName.Emp_ID.value + " = " + emp_Id
				+ " AND " + ColumnName.Comp_ID.value + " = '" + comp_Id + "' "
				+ " AND " + ColumnName.LogIn_Date.value + " = '" + logIn_Date + "'";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Session session = new Session();
		try {
			conn = JDBC_Connection.getConnection();
			ps = conn.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {
				session.setEmp_Id(rs.getInt(ColumnName.Emp_ID.value.replaceAll("`", "")));
				session.setEmp_Name(rs.getString(EmployeeDAO.ColumnName.Name.toString()));
				session.setComp_Id(rs.getString(ColumnName.Comp_ID.value.replaceAll("`", "")));
				session.setLogIn_Date(rs.getDate(ColumnName.LogIn_Date.value.replaceAll("`", "")));
				session.setLogOut_Date(rs.getDate(ColumnName.LogOut_Date.value.replaceAll("`", "")));
				session.setLogIn_Time(rs.getString(ColumnName.LogIn_Time.value.replaceAll("`", "")));
				session.setLogOut_Time(rs.getString(ColumnName.LogOut_Time.value.replaceAll("`", "")));
				session.setTotal_Time(rs.getString(ColumnName.Total_Time.value.replaceAll("`", "")));
				session.setShift(rs.getString(ColumnName.Shift.toString()));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			ps.close();
			conn.close();
		}
	}

	public static String getCreateTable() {
		return createTable;
	}

	public static void setCreateTable(String createTable) {
		SessionDAO.createTable = createTable;
	}
}
