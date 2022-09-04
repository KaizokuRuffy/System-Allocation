package Controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Beans.Computer;
import Controller.Util.DATE;
import Controller.Util.Json;
import Controller.Util.Message;
import Controller.Util.SchedulerTask;
import Service.ComputerService;

@WebServlet(urlPatterns = {"/SystemController/*"})
public class ComputerController extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private ComputerService computerService = new ComputerService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {

		final String CRUD = request.getPathInfo();
		
		switch(CRUD)
		{
			case "/getAllSystems":
				
				String shift = request.getParameter("shift");
				String backup = request.getParameter("backup");
				String unallocated = request.getParameter("unallocated");
				
				List<Computer> computerList = null;
				
				if(shift == null && backup == null && unallocated == null)
				{
					System.out.println("\n-- Fetching all system data --");
					computerList = computerService.getAllSystems();
				}
				else {
					System.out.println("\n--- Fetching list of systems with based on allocation and backup status");
					computerList = computerService.getSystems(shift, backup, unallocated);
				}
				
				if(computerList != null)
				{
					if(computerList.size() > 0)
					{
						System.out.println("All system details fetched successfully");
						new Message().infoToClient(response, computerList);
					}
					else
					{
						new Message().infoToClient(HttpServletResponse.SC_NOT_FOUND, response, shift != null ?
								"No resources data in database. Add system details and then try fetching data."
								: "No unallocated systems present" );	
						System.out.println(shift != null ? "No resource data present in table" : "No unallocated systems present");
					}
				}
				else
				{
					new Message().infoToClient(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
										response, "Database error");
					System.out.println("Database error");
				}
				
				break;

		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		final String CRUD = request.getPathInfo();
		
		switch(CRUD)
		{
			case "/updateStatus":
				
				doPut(request, response);
				
				break;
				
			case "/addSystem":
				
				System.out.println("\n-- Saving resource details to database --");
				Computer comp = new Json().toPojo(request, Computer.class);
				if(comp != null)
				{
					if(computerService.addSystem(comp))
					{
						new Message().infoToClient(HttpServletResponse.SC_CREATED, 
																response, "Resource details saved."); ;
						System.out.println("Resource details saved ");
					}
					else
					{
						new Message().infoToClient(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
										response, "Duplicate resource cannot be added. MAC address should be unique");
						System.out.println("Duplicate resource cannot be added.");
					}
				}
				else {
					new Message().infoToClient(HttpServletResponse.SC_INTERNAL_SERVER_ERROR
																, response, "Invalid input format");
					System.out.println("JSON parse error");
				}
				
				break;
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		final String CRUD = request.getPathInfo();
		
		switch (CRUD)
		{
			case "/updateStatus":
				
				String comp_Id = null;
				String colName = null;
				String status = null;
				boolean flag = false;
				
				comp_Id = request.getParameter("comp_Id");
				colName = request.getParameter("colName");
				status = request.getParameter("status");
				
				if(comp_Id == null && colName == null && status == null) {
					flag = true;
					comp_Id = (String) request.getAttribute("comp_Id");
					colName = (String) request.getAttribute("colName");
					status = (String) request.getAttribute("status");
				}
				
				System.out.println("\n-- Updating " + colName + " status of system --");
				
				if(flag)
				{
					System.out.println(comp_Id + " " + colName + " " + status);
					if(computerService.updateStatus(comp_Id, colName, status))
					{

						try {
							Integer emp_Id = Integer.parseInt(response.getHeader("emp_Id"));
							new Message().infoToClient(emp_Id.toString(),response);
						} catch (NumberFormatException e) {
							new Message().infoToClient("System updated successfully",response);
						}
						
						System.out.println("Status of system '" + comp_Id + 
													"' is updated succesfully");
				
						final String id = comp_Id;
						final String cname = colName;
						
						Date date = null;
						
						if(DATE.getDate(8, 0, 0).toString().compareTo(new Date().toString()) >= 0)
							date = DATE.getDate(8, 0, 0);
						else if(DATE.getDate(16, 0, 0).toString().compareTo(new Date().toString()) >= 0)
							date = DATE.getDate(16, 0, 0);
						else if(DATE.getDate(23, 59, 59).toString().compareTo(new Date().toString()) >= 0)
							date = DATE.getDate(23, 59, 59);

						
						SchedulerTask st = new SchedulerTask();
						st.schedule(new TimerTask() {
							@Override
							public void run() {
								if(computerService.updateStatus(id, cname, "Yes"))
								{
									System.err.println("Status of system '" + id + 
																"' is updated succesfully");
								}
							}
						}, date);
					}
					else
					{
						new Message().infoToClient(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
											response, "System not present in database. Wrong id entered.");
						System.out.println("System not present in database. Wrong id entered.");
					}
				}
				else
				{
					System.out.println(comp_Id + " " + colName + " " + status);
					if(computerService.updateStatus(comp_Id, colName, status))
					{
						new Message().infoToClient("Status updated successfully",response);
						System.out.println("System availability status set as '" + status + "'");
					}
					else 
					{
						new Message().infoToClient(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
											response, "Database error (or) system not present");
						System.out.println("Database error (or) system not present");
					}
					
				}
				
				break;
		}
	}
	
}
