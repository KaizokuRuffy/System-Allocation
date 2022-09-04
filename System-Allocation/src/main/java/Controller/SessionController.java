package Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Beans.Session;
import Controller.Util.Json;
import Controller.Util.Message;
import Service.SessionService;

@WebServlet(urlPatterns = {"/SessionController/*"})
public class SessionController extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private SessionService sessionService = new SessionService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		final String CRUD = request.getPathInfo();
		
		switch(CRUD)
		{
			case "/getAllSession" :
				
				System.out.println("\n-- Fetching all session data --");
				List<Session> sessionList = sessionService.getAllSessions();
				if(sessionList != null)
				{
					if(sessionList.size() > 0)
					{
						new Message().infoToClient(response, sessionList);
						System.out.println("All session data fetched successfully");
					}
					else
					{
						new Message().infoToClient(HttpServletResponse.SC_NOT_FOUND, 
															response, "No session data");
						System.out.println("No session data in table");
					}
				}
				else
				{
					new Message().infoToClient(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
															response, "Database error");
					System.out.println("Database error");
				}
					
				break;
				
			case "/getEmpSession":
				
				System.out.println("\n-- Fetching all session data --");
				
				int emp_Id = Integer.parseInt(request.getParameter("emp_Id"));
				
				List<Session> empSessionList = sessionService.getEmpSession(emp_Id);
				if(empSessionList != null)
				{
					if(empSessionList.size() > 0)
					{
						new Message().infoToClient(response, empSessionList);
						System.out.println("All session data fetched successfully");
					}
					else
					{
						new Message().infoToClient(HttpServletResponse.SC_NOT_FOUND, 
															response, "No session data");
						System.out.println("No session data in table");
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
		
			case "/updateSession" :
				
				doPut(request, response);
				
				break;
				
			case "/addSession" :
				System.out.println("\n-- Adding session data to databse --");
				Session session = new Json().toPojo((String)request.getAttribute("Session"), Session.class);
				request.removeAttribute("Session");
				
				if(session != null)
				{
					if(sessionService.addSession(session))
					{
						System.out.println("Session data of employee '" + session.getEmp_Id() + "' is added to database successfully");

						
						RequestDispatcher rd = request.getRequestDispatcher("/SystemController/updateStatus");
						request.setAttribute("comp_Id", session.getComp_Id());
						request.setAttribute("colName", "available");
						request.setAttribute("status", "No");
						rd.forward(request, response);
						
					}
					else
					{
						new Message().infoToClient(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
								response, "System already in use. Try another system");
						System.out.println("System already in use. Try another system");
					}
				}
				else
				{
					new Message().infoToClient(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
																response, "JSON parse error");
					System.out.println("JSON parse error");
				}
				
				break;
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		final String CRUD = request.getPathInfo();
		
		switch(CRUD)
		{
			case "/updateSession" :
				System.out.println("\n-- Updating session data --");
				Session session = new Json().toPojo(request, Session.class);
				if(session != null)
				{
					if(sessionService.updateSession(session))
					{
						System.out.println("Session data for employee '" + session.getEmp_Id() + ", is updated successfully");
						new Message().infoToClient("User logged out and session of user updated successfully", response);

					}
					else
					{
						new Message().infoToClient(HttpServletResponse.SC_INTERNAL_SERVER_ERROR
								, response,"Session data doesn't exist (or) database error");
						System.out.println("Session data doesn't exist (or) database error");
					}
				}
				else {
					new Message().infoToClient(HttpServletResponse.SC_INTERNAL_SERVER_ERROR
																, response, "JSON parse error");
					System.out.println("JSON parse error");
				}
				
				break;
		}
	}
}
