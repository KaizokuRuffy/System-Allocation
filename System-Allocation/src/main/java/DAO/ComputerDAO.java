package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Beans.Computer;
import DAO.Util.JDBC_Connection;
import DAO.Util.TableName;
import DAO.Util.Util;

public class ComputerDAO {
	static enum ColumnName {
		ID, MAC, Password, Available, Working, Location, Model, Year, Backup;
	}

	private static String createTable = "CREATE TABLE IF NOT EXISTS " + TableName.getComputer() +
			" ( " + ColumnName.ID + " varchar(255), "
			+ ColumnName.MAC + " varchar(255) UNIQUE, "
			+ ColumnName.Password + " varchar(255), "
			+ ColumnName.Available + " varchar(255), "
			+ ColumnName.Working + " varchar(255), "
			+ ColumnName.Backup + " varchar(255), "
			+ ColumnName.Location + " varchar(255), "
			+ ColumnName.Model + " varchar(255), "
			+ ColumnName.Year + " INT, "
			+ " PRIMARY KEY (" + ColumnName.ID + "))";

	public int noOfRows() {
		String query = "select count(*)"
				+ "from " + TableName.getComputer();

		Connection conn = JDBC_Connection.getConnection();

		try (Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query)) {
			int count = 0;

			if (rs.next()) {
				count = Integer.parseInt(rs.getString(1));
				return count;
			}
		} catch (SQLException e) {
			JDBC_Connection.close();
			e.printStackTrace();
		}

		return 0;
	}

	public int insertInto(Computer comp) {

		String query = " INSERT INTO " + TableName.getComputer()
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Connection conn = JDBC_Connection.getConnection();

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, comp.getComp_Id());
			ps.setString(2, comp.getMAC());
			ps.setString(3, comp.getComp_Password());
			ps.setString(4, comp.getAvailable());
			ps.setString(5, comp.getWorking());
			ps.setString(6, comp.getBackup());
			ps.setString(7, comp.getComp_Loc());
			ps.setString(8, comp.getModel());
			ps.setInt(9, comp.getYear());

			int rs = ps.executeUpdate();

			return rs;
		} catch (SQLException e) {
			JDBC_Connection.close();
			e.printStackTrace();
		}
		return 0;
	}

	public List<Computer> selectAll() {

		System.out.println("Fetching All(Computer) details ");

		String query = "SELECT * "
				+ "FROM " + TableName.getComputer();

		Connection conn = JDBC_Connection.getConnection();

		try (PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			List<Computer> computerList = new ArrayList<>();

			while (rs.next()) {
				Computer comp = new Computer();
				comp.setComp_Id(rs.getString(ColumnName.ID.toString()));
				comp.setMAC(rs.getString(ColumnName.MAC.toString()));
				comp.setComp_Password(rs.getString(ColumnName.Password.toString()));
				comp.setAvailable(rs.getString(ColumnName.Available.toString()));
				comp.setWorking(rs.getString(ColumnName.Working.toString()));
				comp.setBackup(rs.getString(ColumnName.Backup.toString()));
				comp.setComp_Loc(rs.getString(ColumnName.Location.toString()));
				comp.setModel(rs.getString(ColumnName.Model.toString()));
				comp.setYear(rs.getInt(ColumnName.Year.toString()));
				computerList.add(comp);
			}
			return computerList;
		} catch (SQLException e) {
			JDBC_Connection.close();
			e.printStackTrace();
		}

		return null;
	}
	
	public List<Computer> getUnallocateSystems(String shift, String backup){
		
		Util util = new Util();
		String ColID = ColumnName.ID.toString();
		String ColBackup = ColumnName.Backup.toString();
		String EmpColCID = EmployeeDAO.ColumnName.Comp_Id.value;
		String EmpColShift =  EmployeeDAO.ColumnName.Shift.toString();
		String EmpColEID = EmployeeDAO.ColumnName.ID.toString();
		
		String System_Shift = "( SELECT R.?, " + (backup == null ? ("R." + ColBackup + ",") : "") +  
						 " ? FROM ? R, ? " + (backup == null ? "": 
						(" WHERE R." + ColBackup + " = '" + backup + "'" )) + " ORDER BY R.? )" ;
		
		System_Shift = util.replaceQuestionMark(System_Shift, new String[] { ColID, "Shift", 
														TableName.getComputer(), "shift", ColID});
		
		String Employee = "(SELECT E.?, E.?, E.? FROM ? E)";
		Employee = util.replaceQuestionMark(Employee, new String[] 
									{ EmpColEID , EmpColCID, EmpColShift, TableName.getEmployee()});
		
		String query = "SELECT R.? " + (backup == null ? (", R." + ColBackup ) : "") 
						+ " FROM " + System_Shift + " AS R LEFT JOIN " + Employee + " AS E ON R.? = E.? "
						+ " AND R.? = E.? WHERE " 
							+ (shift == null ? "" : (" R." + EmpColShift + " = '" + shift + "'" )) 
							+ " AND E." + EmpColEID + " IS NULL " + " ORDER BY R.? ";
		
		query = util.replaceQuestionMark(query, new String[] {ColID, ColID, EmpColCID, 
									EmpColShift, EmpColShift, ColID});
		
		Connection conn = JDBC_Connection.getConnection();

		try (PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			List<Computer> computerList = new ArrayList<>();

			while (rs.next()) {
				Computer comp = new Computer();
				comp.setComp_Id(rs.getString(ColumnName.ID.toString()));
				if(backup == null)
				comp.setBackup(rs.getString(ColumnName.Backup.toString()));
				computerList.add(comp);
			}
			return computerList;
		} catch (SQLException e) {
			JDBC_Connection.close();
			e.printStackTrace();
		}

		return null;
	}
	
	public List<Computer> getAllocatedSystems(String shift, String backup){
		
		Util util = new Util();
		
		String query = "SELECT E.`Computer ID` from Employee E where E.Shift = 'Night'" + (backup != null ? 
				(" WHERE R." + ColumnName.Backup + " = '" + backup + "' " + (shift != null ? " AND " : "") ) : 
					(shift != null ? " WHERE " : "")) +	(shift == null ? "" : 
					(" R." + EmployeeDAO.ColumnName.Shift + " = '" + shift + "'" )) ;
		
		System.out.println(query);
		
		Connection conn = JDBC_Connection.getConnection();

		try (PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			List<Computer> computerList = new ArrayList<>();

			while (rs.next()) {
				Computer comp = new Computer();
				comp.setComp_Id(rs.getString(ColumnName.ID.toString()));
				computerList.add(comp);
			}
			return computerList;
		} catch (SQLException e) {
			JDBC_Connection.close();
			e.printStackTrace();
		}

		return null;
	}

	public int updateStatus(String comp_Id, String ColName, String value) {

		String yes = "Yes";
		String no = "No";

		String query = " UPDATE " + TableName.getComputer()
				+ " SET " + ColName + " = ? "
				+ " WHERE " + ColumnName.ID.toString() + " = '" + comp_Id + "'";

		if (ColName.equalsIgnoreCase(ColumnName.Working.toString()) && value.equalsIgnoreCase(no))
			query = " UPDATE " + TableName.getComputer()
					+ " SET " + ColName + " = ? "
					+ ", " + ColumnName.Available + " = '" + no + "' "
					+ " WHERE " + ColumnName.ID.toString() + " = '" + comp_Id + "'";

		else if (ColName.equalsIgnoreCase(ColumnName.Working.toString()) && value.equalsIgnoreCase(yes))
			query = " UPDATE " + TableName.getComputer()
					+ " SET " + ColName + " = ? "
					+ ", " + ColumnName.Available + " = '" + yes + "' "
					+ " WHERE " + ColumnName.ID.toString() + " = '" + comp_Id + "'";

		Connection conn = JDBC_Connection.getConnection();

		try (PreparedStatement ps = conn.prepareStatement(query);) {

			ps.setString(1, value);

			int rs = ps.executeUpdate();


			return rs;
		} catch (SQLException e) {
			JDBC_Connection.close();
			e.printStackTrace();
		}

		return 0;
	}

	public Computer selectRecord(String comp_Id) {
		String query = "SELECT *"
				+ "FROM " + TableName.getComputer()
				+ "WHERE " + ColumnName.ID + " =  '" + comp_Id + "'";

		Connection conn = JDBC_Connection.getConnection();

		try (PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				Computer comp = new Computer();
				comp.setComp_Id(rs.getString(ColumnName.ID.toString()));
				comp.setMAC(rs.getString(ColumnName.MAC.toString()));
				comp.setAvailable(rs.getString(ColumnName.Available.toString()));
				comp.setComp_Loc(rs.getString(ColumnName.Location.toString()));
				comp.setComp_Password(rs.getString(ColumnName.Password.toString()));
				comp.setModel(rs.getString(ColumnName.Model.toString()));
				comp.setWorking(rs.getString(ColumnName.Working.toString()));
				comp.setBackup(rs.getString(ColumnName.Backup.toString()));
				comp.setYear(rs.getInt(ColumnName.Year.toString()));

				return comp;
			}
		} catch (SQLException e) {
			JDBC_Connection.close();
			e.printStackTrace();
		}
		return null;
	}

	@Deprecated
	public void deleteRecord(String comp_Id) {
		String query = "DELETE FROM ?"
				+ "WHERE ? = ? ";
	}

	public static String getCreateTable() {
		return createTable;
	}

	public static void setCreateTable(String createTable) {
		ComputerDAO.createTable = createTable;
	}

}