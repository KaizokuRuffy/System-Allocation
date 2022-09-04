
package Controller.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.AdminService;

/*
	urls to exclude from filtering : 
	1. /adminPresent
	2. /adminLogin
	3. /create  (only if admin is not present)
	4. /addAdmin (only if admin is not present)
	5. /userLogin
*/

@WebFilter(urlPatterns = "/*", initParams = @WebInitParam(name = "excludeURLs", value = "/adminPresent,/adminLogin,/create,/addAdmin,/userLogin"))
public class LoginFilter implements Filter {
	
	static private final Map<String, String> URLs = new HashMap<>();
	private static List<String> exludeURLs = new ArrayList<>();
	private static List<String> commonURLs = Arrays.asList(new String[] { "/getUser", "/getAllSession" });
	private static List<String> adminURLs = Arrays
			.asList(new String[] { "/adminLogout", "/getAdmin", "/create", "/addAdmin", "/getAllUsers", "/addUser",
					"/getAllSystems", "/addSystem", "/updateStatus", });
	private static List<String> employeeURLs = Arrays.asList(new String[] { "/userLogout" , "/getEmpSession" });

	private static Consumer<String> exclude = (String urls) -> {
		String[] s = urls.split(",");

		synchronized (exludeURLs) {
			exludeURLs.clear();
			for (String temp : s)
				exludeURLs.add(temp);
		}
	};

	static {
		System.out.println("Loading LoginFilter class");
		
		URLs.put("Admin not present", "/adminPresent,/adminLogin,/create,/addAdmin,/userLogin");
		URLs.put("Admin present", "/adminPresent,/adminLogin,/userLogin");

		exclude.accept(new AdminService().isAdminPresent()
				? URLs.get("Admin present")
				: URLs.get("Admin not present"));
		System.out.println(exludeURLs);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession sess = request.getSession(false);
	
		if (request.getPathInfo() == null || exludeURLs.contains(request.getPathInfo()))
			chain.doFilter(req, res);

		else if (commonURLs.contains(request.getPathInfo())) {
			if (sess != null && sess.getAttribute("status") != null)

				chain.doFilter(req, res);

			else
				new Message().infoToClient(HttpServletResponse.SC_FORBIDDEN,
													response, "Cannot perform unauthorized operation.");
		}

		else if (adminURLs.contains(request.getPathInfo())) {
			if (sess != null && sess.getAttribute("status") != null
					&& sess.getAttribute("status").equals("admin logged in")) {
				chain.doFilter(req, res);
			} else
				new Message().infoToClient(HttpServletResponse.SC_FORBIDDEN,
									response, "Cannot perform unauthorized operation.");
		}

		else if (employeeURLs.contains(request.getPathInfo())) {
			if (sess != null && sess.getAttribute("status") != null &&
					sess.getAttribute("status").equals("employee logged in"))

				chain.doFilter(req, res);

			else
				new Message().infoToClient(HttpServletResponse.SC_FORBIDDEN,
								response, "Cannot perform unauthorized operation.");
		} 
		
		//Authenticating request for html files made by client
		else if(request.getServletPath() != null && request.getServletPath().contains(".html")) {
			
			if(request.getServletPath().contains("index.html"))
				chain.doFilter(request, response);
			
			if(request.getServletPath().contains("Admin-Reg.html") && !new AdminService().isAdminPresent())
				chain.doFilter(request, response);
			
			if((request.getServletPath().contains("Admin.html") || request.getServletPath().contains("User-Reg.html")
					|| request.getServletPath().contains("System-Reg.html") || request.getServletPath().contains("User-Reg.html")
					|| request.getServletPath().contains("Admin-Reg.html")) && sess != null 
					&& sess.getAttribute("status").equals("admin logged in"))
				chain.doFilter(request, response);
			
			if(request.getServletPath().contains("User.html") && sess != null &&
											sess.getAttribute("status").equals("employee logged in"))
				chain.doFilter(request, response);
		}
		
		//Authenticating request for JavaScript files made by client
		else if(request.getServletPath() != null && request.getServletPath().contains(".js")) {
			chain.doFilter(request, response);
		}
		
		else {
			new Message().infoToClient(HttpServletResponse.SC_FORBIDDEN,
					response, "Cannot perform unauthorized operation.");
		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}

	public static List<String> getExludeURLs() {
		return exludeURLs;
	}

	public static Consumer<String> getExclude() {
		return exclude;
	}

	public static Map<String, String> getUrls() {
		return URLs;
	}

}
