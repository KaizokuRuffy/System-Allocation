package Controller.Util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class Message {
	
	//For 200 OK Status code response with response body as plain text
	public void infoToClient(String Message, HttpServletResponse response) {
		PrintWriter out = null;

		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		response.setStatus(HttpServletResponse.SC_OK);
		
		if(response.getContentType() == null)
		response.setContentType("text/plain");
		
		out.write(Message);
		System.out.println(response.getStatus() + " - " + Message);
	}

	public <T> void infoToClient(HttpServletResponse response, T obj) {
		String json = new Json().toJSON(obj);
		PrintWriter out = null;

		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		response.setContentType("application/json");
		out.write(json);
		response.setStatus(HttpServletResponse.SC_OK);
		System.out.println(response.getStatus() + " - Request processed succesfully ");
	}
	
	//For Other HTTP Status code response with response body as plain text
	public void infoToClient(int status_Code, HttpServletResponse response, String Message) {
		PrintWriter out = null;

		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		response.setStatus(status_Code);
		response.setContentType("text/plain");
		out.write(Message);
	}

}
