package Controller;

import java.io.IOException;
//import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Beans.Admin;
import Controller.Util.Counter;
import Controller.Util.Json;
import Controller.Util.LoginFilter;
//import Controller.Util.LoginFilter;
import Controller.Util.Message;
import Service.AdminService;
import Service.EmployeeService;

@WebServlet(urlPatterns = { "/AdminController/*" })
public class AdminController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private AdminService adminService = new AdminService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final String CRUD = request.getPathInfo();

		switch (CRUD) {
			case "/adminPresent":

				System.out.println("\n-- Checking if there is admin is already present --");

				if (!adminService.isEmpty()) {
					if (adminService.isAdminPresent()) {
						request.setAttribute("status", "yes");

						if (!new EmployeeService().isPresent())
							new Message().infoToClient("Yes ", response);
						else
							new Message().infoToClient("Yes Yes", response);
					} else {
						request.setAttribute("status", "no");
						new Message().infoToClient("No - Admin", response);
					}
				} else {
					request.setAttribute("status", "no");
					new Message().infoToClient("No - DB empty", response);
				}
				Counter.getcounter().userNotPresent();

				break;

			case "/adminLogout":

				System.out.println("\n-- Admin logging out of the web site --");
				request.getSession(false).invalidate();

				new Message().infoToClient("Logged out successfully", response);

				Counter.getcounter().atLogout();

				break;

			case "/getAdmin":
				System.out.println("\n-- Fetching admin profile --");

				int admin_Id = Integer.parseInt(request.getParameter("admin_Id"));
				Admin admin = adminService.getUser(admin_Id);

				if (admin == null) {
					new Message().infoToClient(HttpServletResponse.SC_NOT_FOUND, response,
							"Invalid username (or) database empty");
					System.out.println("Invalid username (or) database empty");
				} else {
					new Message().infoToClient(response, admin);
					System.out.println("Admin fetched successfully ");
				}

				break;

			case "/create":

				System.out.println("\n-- Admin registration operation --");

				if (adminService.isEmpty()) {
					if (AdminService.createDatabase()) // DB call
					{
						new Message().infoToClient(HttpServletResponse.SC_CREATED, response,
								"Database created successfully");
						System.out.println("Database created successfully");
						AdminService.setEmpty(false);
					} else {
						new Message().infoToClient(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
								response, "Database error");
						System.out.println("Database connection problem");
					}

					Counter.getcounter().userNotPresent();
				} else {
					new Message().infoToClient("Database already created", response);
					System.out.println("Database already created");
				}

				break;

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final String CRUD = request.getPathInfo();

		switch (CRUD) {
			case "/adminLogin":

				System.out.println("\n-- Authenticating admin --");

				String username = null;
				String Password = null;
				Boolean auth = null;

				username = request.getParameter("admin_Email");// var
				Password = new String(request.getParameter("admin_Password"));// var
				
				int id = 0;
				
				try {
					id = adminService.getUser(username).getAdmin_Id();
					auth = adminService.Authenticate(id, Password); // DB call
				}
				catch (NullPointerException e) {
				}
			
				if (auth == null) {
					new Message().infoToClient(HttpServletResponse.SC_FORBIDDEN, response,
							"Invalid username / password");
					System.out.println("Invalid username!!! Enter correct username");
				} else if (auth == true) {
					System.out.println("Logged in successfully");
					request.getSession(true).setAttribute("status", "admin logged in");

					request.getSession(false).setMaxInactiveInterval(60 * 30);
					Counter.getcounter().atLogin();

					new Message().infoToClient("admin_Id :" + id, response);

				} else if (auth == false) {
					new Message().infoToClient(HttpServletResponse.SC_FORBIDDEN, response,
							"Invalid username / password");
					System.out.println("Invalid password!!! Enter password again");
				}

				break;

			case "/addAdmin":

				System.out.println("\n -- Admin account creation operation --");

				Admin admin = new Json().toPojo(request, Admin.class);

				boolean before = adminService.isAdminPresent();

				if (admin != null) {
					if (adminService.createUser(admin)) // DB call
					{
						new Message().infoToClient(HttpServletResponse.SC_CREATED,
								response, "Account created successfully");
						System.out.println(admin.getAdmin_Name() + ", your account has been created succesfully");
					} else {
						new Message().infoToClient(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
								response, "Duplicate user not allowed. Email and Contact.no should be unique");
						System.out.println("Duplicate user not allowed. Email and Contact no should be unique");
						break;
					}
				} else {
					new Message().infoToClient(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
							response, "JSON parse error");
					System.out.println("JSON parse error");

					break;
				}

				boolean after = adminService.isAdminPresent();

				if (!before && after) {
					AdminService.setAdminPresent(true);
					LoginFilter.getExclude().accept(LoginFilter.getUrls().get("Admin present"));
				}

				break;

		}
	}

}
