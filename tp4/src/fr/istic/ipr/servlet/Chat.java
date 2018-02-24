package fr.istic.ipr.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Chat")
public class Chat extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StringBuffer buffer = new StringBuffer();
       
    public Chat() {
        super();
    }
    
    public void init() throws ServletException {
    	buffer.append("Bienvenue dans le chat <br />");
    	buffer.append("Soyez polis <br />");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"utf-8\" />");
		out.println("<title>Chat</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<form method=\"post\" action=\"Chat\">");
		out.println("<input type=\"text\" name=\"message\" id=\"message\" required autofocus />");
		out.println("<input type=\"submit\" name=\"action\" value=\"submit\"/>");
		out.println("<input type=\"submit\" name=\"action\" value=\"refresh\"/>");
		out.println("</form>");
		out.println("<p>");
		out.println(buffer.toString());
		out.println("</p>");
		out.println("</body>");
		out.println("</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String message = request.getParameter("message");
		
		if("submit".equals(action)) {
			buffer.append(message + "<br />");
		}
		
		doGet(request, response);
	}

}
