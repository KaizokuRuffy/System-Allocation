package Service;

import java.util.List;

import Beans.Computer;
import DAO.ComputerDAO;

public class ComputerService 
{
	private ComputerDAO computerDAO = new ComputerDAO();
	
	public boolean addSystem(Computer comp)
	{	
		if(comp.getWorking().equals(""))
			comp.setWorking("Yes");
		if(comp.getWorking().equals("Yes"))
			comp.setAvailable("Yes");
		if(comp.getWorking().equals("No"))
			comp.setAvailable("No");
		
		if(computerDAO.insertInto(comp) == 1)
			return true;
		
		return false;
	}
	
	public List<Computer> getAllSystems()
	{
		List<Computer> computerList = null;
		
		computerList = computerDAO.selectAll();
		return computerList;
	}
	
	public boolean updateStatus(String comp_Id, String colName, String value)
	{
		
		if(computerDAO.updateStatus(comp_Id, colName, value) == 1)
			return true;
		
		return false;
	}
	
	public List<Computer> getSystems(String shift, String backup, String unallocated){
		
		if("Yes".equals(unallocated))
			return computerDAO.getUnallocateSystems(shift, backup);
		
		else if("No".equals(unallocated))
			return computerDAO.getAllocatedSystems(shift, backup);
		
		return null;
	}
	
	public Computer getSystem(String comp_Id
			)
	{
		return computerDAO.selectRecord(comp_Id);
	}
}
