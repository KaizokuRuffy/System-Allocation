package Service;

import java.io.File;
import java.util.Map;


import Beans.Admin;
import DAO.AdminDAO;
import Service.Util.Cipher;
import Service.Util.FileUtil;

public class AdminService 
{
	private AdminDAO adminDAO = new AdminDAO();	
    private static  boolean isAdminPresent;
    private static boolean isEmpty = true;
	
	static {
    	isAdminPresent = new AdminService().isAdminPresent();
    	isEmpty = new AdminService().isEmpty();
    	System.out.println(isAdminPresent + " " + isEmpty);
    }

    public static void setAdminPresent(boolean isAdminPresent) {
		AdminService.isAdminPresent = isAdminPresent;
	}

	public static void setEmpty(boolean isEmpty) {
		AdminService.isEmpty = isEmpty;
	}

	
	public static boolean createDatabase()
	{
		int[] res = AdminDAO.createTable();
		int count = 0;
		
		for(int i = 0; i < res.length; i++)
		if(res[i] == 0 || res[i] == 1)
			count++;
		
		if(count == 8)
			return true;
		else 
			return false;
	
	}
	
	public boolean isEmpty()
	{
		if(!isEmpty)
			return false;
		
		if(adminDAO.isDatabaseEmpty()) {
			
			File dir = new File(Cipher.getFilepath());
			
			if(dir.exists())
				System.out.println(FileUtil.deleteDir(dir) ? "Keys deleted successfully" : "Keys couldn't be deleted");
			
			return true;
		}
		
		return false;
	}
	
	public boolean isAdminPresent()
	{
		if(isAdminPresent)
			return true;
		
		if(isEmpty())
			return false;
		else if(adminDAO.noOfRows() == 0)
			return false;
		
		return true;
	}
	
	public Boolean Authenticate(int admin_Id, String Password)
	{
		String admin_Password = null;
		
		try 
		{
			Map<String,String> map = adminDAO.getPassword(admin_Id);

			admin_Password = map.get("password");
			
			Cipher cipher = new Cipher(admin_Password, ((Integer)admin_Id).toString(), map.get("name"), "admin");
			admin_Password = cipher.decrypt();
		}
		catch (NullPointerException e) 
		{
			return null;
		}
		
		if(Password.equals(admin_Password))
			return true;
		
		return false;
	}
	
	public boolean createUser(Admin admin)
	{
		if(admin.getAdmin_Id() == -1)
			admin.setAdmin_Id(adminDAO.noOfRows() + 1);
		
		Cipher cipher = new Cipher(admin.getAdmin_Password(), 
										((Integer)admin.getAdmin_Id()).toString(), admin.getAdmin_Name(),"admin", true);
		
		admin.setAdmin_Password(cipher.encrypt());
		
		if(adminDAO.insertInto(admin) == 1)
			return true;
		
		new File(cipher.getKeysetFilename()).delete();
		
		return false;
	}
	
	public Admin getUser(int admin_Id)
	{
		Admin admin = null;
		
		admin = adminDAO.selectRecord(admin_Id);
		
		if(admin != null)
		{
			Cipher cipher = new Cipher(admin.getAdmin_Password(), 
					((Integer)admin.getAdmin_Id()).toString(), admin.getAdmin_Name(), "admin");
			
			admin.setAdmin_Password(cipher.decrypt());
		}
		
		return admin;
	}
	
	public Admin getUser(String admin_Email)
	{
		Admin admin = null;
		
		admin = adminDAO.selectRecord(admin_Email);
		
		if(admin != null)
		{
			Cipher cipher = new Cipher(admin.getAdmin_Password(), 
					((Integer)admin.getAdmin_Id()).toString(), admin.getAdmin_Name(), "admin");
			
			admin.setAdmin_Password(cipher.decrypt());
		}
		
		return admin;
	}
}