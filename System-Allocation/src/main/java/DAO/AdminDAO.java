package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Beans.Admin;
import DAO.Util.JDBC_Connection;
import DAO.Util.Shift;
import DAO.Util.TableName;

public class AdminDAO 
{	
	enum ColumnName
	{
		ID, Name, Email_ID("`Email ID`"), Contact_No("`Contact No`"), Password;
		
		String value;
		
		ColumnName(String value) {
			this.value = value;
		}

		ColumnName() {
		}  
	}
		
	private static String createTable = "CREATE TABLE IF NOT EXISTS " + TableName.getAdmin() +
			 " ( " + ColumnName.ID + " INT NOT NULL AUTO_INCREMENT, "
			 +  ColumnName.Name + " varchar(255), "
			 +  ColumnName.Email_ID.value + " varchar(255) UNIQUE, "
			 +  ColumnName.Contact_No.value + " varchar(255) UNIQUE, "
			 +  ColumnName.Password + " varchar(255),"
			+ " PRIMARY KEY (" + ColumnName.ID + ") )" ;

    
	public static int[] createTable()  
	{
		Connection conn = JDBC_Connection.getConnection();
		
		String shift = "CREATE TABLE IF NOT EXISTS " + TableName.getShift() +  " ( SNO INT AUTO_INCREMENT, Shift VARCHAR(255), PRIMARY KEY(SNO))";
		
		try (Statement st = conn.createStatement())
		{
			int[] result = null;
			st.addBatch(AdminDAO.getCreateTable());
			st.addBatch(EmployeeDAO.getCreateTable());
			st.addBatch(ComputerDAO.getCreateTable());
			st.addBatch(SessionDAO.getCreateTable());
			st.addBatch(shift);
			st.addBatch("INSERT INTO `data`." + TableName.getShift() + " (`Shift`) VALUES ('" + Shift.Morning +"')");
			st.addBatch("INSERT INTO `data`." + TableName.getShift() + " (`Shift`) VALUES ('" + Shift.Evening +"')");
			st.addBatch("INSERT INTO `data`." + TableName.getShift() + " (`Shift`) VALUES ('" + Shift.Night +"')");
			
			result = st.executeBatch();
			
			System.out.println(Arrays.toString(result));
		
			return result;
		}
		catch(Exception e)
		{
			JDBC_Connection.close();
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean isDatabaseEmpty()
	{
		String show = "SELECT count(*) "
				+ " FROM INFORMATION_SCHEMA.TABLES "
				+ " WHERE TABLE_SCHEMA = '" + TableName.getDatabase() +"'"  ;
		
		Connection conn = JDBC_Connection.getConnection();
		 
		try (Statement st = conn.createStatement();
			 ResultSet rs = st.executeQuery(show))
		{
			
			int count = 0;
			
			if(rs.next())
				count = Integer.parseInt(rs.getString(1));
			
			if(count == 5)
				return false;
		} 
		catch (SQLException e) 
		{
			JDBC_Connection.close();
			e.printStackTrace();
		}
		
		return true;
	}
	
	public int noOfRows()
	{
		String query = "select count(*)"
					 + "from " + TableName.getAdmin();
		
		Connection conn = JDBC_Connection.getConnection();
		 
		try (Statement st = conn.createStatement();
				 ResultSet rs = st.executeQuery(query))
			{
				int count = 0;
				
				if(rs.next())
				{
					count = Integer.parseInt(rs.getString(1));
					return count;
				}
			} 
			catch (SQLException e) 
			{
				JDBC_Connection.close();
				e.printStackTrace();
			}
		
		return 0;
	}
	
	public Map<String,String> getPassword(int admin_ID) {
		String query = "SELECT " + ColumnName.Password + ", " + ColumnName.Name
					 + " FROM " + TableName.getAdmin()
					 + " WHERE " + ColumnName.ID + " = " + admin_ID;
		
		Connection conn = JDBC_Connection.getConnection();
		 
		try  (PreparedStatement ps = conn.prepareStatement(query);
		      ResultSet rs = ps.executeQuery())
		{
			if(rs.next())
			{
				String admin_Password = new String(rs.getString(ColumnName.Password.toString()));
				String admin_Name = rs.getString(ColumnName.Name.toString());
				
				Map<String,String> map = new HashMap<>();
				map.put("name", admin_Name);
				map.put("password", admin_Password);
				
				return map;
			}
		} 
		catch (SQLException e) 
		{
			JDBC_Connection.close();
			e.printStackTrace();
		}
		return null;
	}
	
	public int insertInto(Admin admin) {
		
		System.out.println("Creating Admin account");
		String query = "INSERT INTO " + TableName.getAdmin() + 
											" VALUES (?, ?, ?, ?, ?)";

		Connection conn = JDBC_Connection.getConnection();
		 
		try (PreparedStatement ps = conn.prepareStatement(query))
		{

			if(admin.getAdmin_Id() != 0)
				ps.setInt(1, admin.getAdmin_Id());
			else
				ps.setString(1, null);
			
			ps.setString(2, admin.getAdmin_Name());
			ps.setString(3, admin.getAdmin_Email());
			ps.setString(4, admin.getAdmin_ContactNo());
			ps.setString(5, admin.getAdmin_Password());

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
	
	public Admin selectRecord(int admin_ID) {

		String query = "SELECT * "
					 + " FROM " + TableName.getAdmin()
					 + " WHERE " + ColumnName.ID + " = " + admin_ID;
		
		Connection conn = JDBC_Connection.getConnection();
		
		try(PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery())
		{
			if(rs.next())
			{
				Admin admin = new Admin();
				admin.setAdmin_Id(rs.getInt(ColumnName.ID.toString()));
				admin.setAdmin_Name(rs.getString(ColumnName.Name.toString()));
				admin.setAdmin_Email(rs.getString(ColumnName.Email_ID.value.replaceAll("`", "")));
				admin.setAdmin_ContactNo(rs.getString(ColumnName.Contact_No.value.replaceAll("`", "")));
				admin.setAdmin_Password(rs.getString(ColumnName.Password.toString()));
				
				return admin;
			}
		} 
		catch (SQLException e) 
		{
			JDBC_Connection.close();
			e.printStackTrace();
		}
	
		return null;
	}
	
public Admin selectRecord(String admin_Email) {

		String query = "SELECT * "
					 + " FROM " + TableName.getAdmin()
					 + " WHERE " + ColumnName.Email_ID.value + " = '" + admin_Email + "'";
		Connection conn = JDBC_Connection.getConnection();
		
		try(PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery())
		{
			
			if(rs.next())
			{
				Admin admin = new Admin();
				admin.setAdmin_Id(rs.getInt(ColumnName.ID.toString()));
				admin.setAdmin_Name(rs.getString(ColumnName.Name.toString()));
				admin.setAdmin_Email(rs.getString(ColumnName.Email_ID.value.replaceAll("`", "")));
				admin.setAdmin_ContactNo(rs.getString(ColumnName.Contact_No.value.replaceAll("`", "")));
				admin.setAdmin_Password(rs.getString(ColumnName.Password.toString()));
				
				return admin;
			}
		} 
		catch (SQLException e) 
		{
			JDBC_Connection.close();
			e.printStackTrace();
		}
	
		return null;
	}

	@Deprecated
	public void selectAll() {
		
	}
	
	@Deprecated
	public void updateRecord() {
		String query = "UPDATE ? "
				 + "SET ? = ? "
				 + "WHERE ? = ? ";
	}
	
	@Deprecated
	public static boolean createDatabase() throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		try 
		{
			conn = JDBC_Connection.getConnection();
			ps = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS " + TableName.getDatabase());
			
			if(ps.executeUpdate() == 1)
				System.out.printf("Database - %s Created!!!", TableName.getDatabase());
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			ps.close();
			conn.close();
			System.out.println("\nResource closed...");
		}
		return true;
	}

	
	public static String getCreateTable() {
		return createTable;
	}

	public static void setCreateTable(String createTable) {
		AdminDAO.createTable = createTable;
	}
	
}
