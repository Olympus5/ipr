package fr.istic.ipr.servlet;

import java.io.IOException;
import java.time.LocalDateTime;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("content", buffer);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/chat.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String message = request.getParameter("message");
		
		if("submit".equals(action)) {
			buffer.append("(" + LocalDateTime.now() + ") " + message + "<br />");
		}
		
		doGet(request, response);
	}

}
