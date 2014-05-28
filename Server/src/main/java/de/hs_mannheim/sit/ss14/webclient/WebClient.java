package de.hs_mannheim.sit.ss14.webclient;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.hs_mannheim.sit.ss14.database.DatabaseConnector;
import de.hs_mannheim.sit.ss14.database.MySQLDatabaseConnector;
import de.hs_mannheim.sit.ss14.sync.ConnectedUsers;
import de.hs_mannheim.sit.ss14.sync.User;

/**
 * Servlet implementation class WebClient
 */

public class WebClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static DatabaseConnector dbCon;

	static {
		dbCon = new MySQLDatabaseConnector();
		try {
			dbCon.connect();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebClient() {
        super();
    }

	/**
	 * Delivers the login form to the client.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("WebLogin.html");
        view.forward(request, response);
	}

	/**
	 * Handles post data and authenticates the user or redirect him.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get Hash and username from post parameters
		String hash = request.getParameter("hashOutput");
		String username = request.getParameter("username");

		User user = ConnectedUsers.getPendingUser(username);

		//check if the user exist
		if(user == null){
			RequestDispatcher view = request.getRequestDispatcher("LoginFailed.html");
			view.forward(request, response);
			return;
		}


		if(dbCon.checkWebPassword(user, hash)){ //authenticate the user if the credentials are correct
			RequestDispatcher view = request.getRequestDispatcher("Success.html");
			view.forward(request, response);
			ConnectedUsers.authorizeUser(username);
		}else if(user.getFailedLoginAttempts() >= 3){ //if the user already failed to login for 3 times remove him from pending users
	        RequestDispatcher view = request.getRequestDispatcher("Suspended.html");
		    view.forward(request, response);
	        ConnectedUsers.removePendingUser(username);
		}else { //redirect to the Weblogin and increase the failed login attempts
			RequestDispatcher view = request.getRequestDispatcher("LoginFailed.html");
	        view.forward(request, response);
	        user.increaseFailedLoginAttempts();
		}
	}

}
