package DAO.Util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC_Connection
{
	private static Connection conn = null;
	
	public static boolean isOpen()
	{
		try {
			if(conn != null)
				synchronized(conn)
				{
					if((!conn.isClosed()) || conn.isValid(2))
						return true;
				}
		} 	
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean close()
	{
		try 
		{
			if(conn != null)
			{
				if(!conn.isClosed())
				{
					try {
						synchronized (conn) {
							if(!conn.isClosed())
								conn.close();
							System.err.println("Connecion closed...");
						}
						
						return true;
					} 
					
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
				else 
					return true;
			}
			else 
				return true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return close();
	}
	
	public static Connection getConnection()
	{
		if(!isOpen())
		{
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/" + 
								TableName.getDatabase() + "?createDatabaseIfNotExist=true";
			String username = "Admin";
			String password = "PCrEq9KaMbAcut";
			
			try 
			{
				Class.forName(driver);
				
				if(conn != null)
					synchronized (conn) {
						conn = DriverManager.getConnection(url,username,password);
					}
				else 
					conn = DriverManager.getConnection(url,username,password);
				
				System.out.println("Connected Successfully");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		return conn;
	}
}