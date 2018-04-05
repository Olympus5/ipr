package fr.istic.ipr.servlet;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Chat")
public class Chat extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StringBuffer buffer = new StringBuffer();
       
    public Chat() {
        super();
    }

    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	buffer.append(config.getServletContext().getInitParameter("welcome") + "<br />");
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		//Si il existe pas de variable nom alors redirection a la page static index.html
		if(session.getAttribute("nom") == null) {
			this.getServletContext().getRequestDispatcher("/index.html").forward(request, response);
		}
		
		request.setAttribute("content", buffer);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/chat.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String nom = (String) session.getAttribute("nom");
		
		if(nom == null) {
			nom = request.getParameter("nom");
			
			if(nom == null) { // Si nom est pas présent => redirection index.html
				this.getServletContext().getRequestDispatcher("/index.html").forward(request, response);
			} else {
				if(nom.length() <= 0) {//Si nom est vide => redirection index.html
					this.getServletContext().getRequestDispatcher("/index.html").forward(request, response);
				} else {// Ajout du nom dans la session utilisateur
					session.setAttribute("nom", nom);
					this.getServletContext().getRequestDispatcher("/WEB-INF/chat.jsp").forward(request, response);
				}
			}
		} else{// User loggé, donc autorisé à envoyer des messages
			String action = request.getParameter("action");
			String message = request.getParameter("message");
			
			if("submit".equals(action)) {
				buffer.append("(" + LocalDateTime.now() + ")"+ nom +">" + message + "<br />");
			}
			
			doGet(request, response);
		}
		
	}

}
