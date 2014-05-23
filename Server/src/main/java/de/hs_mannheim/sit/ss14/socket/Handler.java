package de.hs_mannheim.sit.ss14.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import de.hs_mannheim.sit.ss14.DatabaseConnector;
import de.hs_mannheim.sit.ss14.User;

/**
 * Handles a single socket Connection.
 *
 * @author Jochen Schwander
 */
class Handler implements Runnable {
	private Socket client;
	private DatabaseConnector dbcon;
	private BufferedReader in;
	private PrintWriter out;
	private boolean isAuthorized;
	private User user;

	/**
	 * Constructor.
	 *
	 * @param client socket on which the client is connected
	 * @param dbcon database connection, which should be used for login checks
	 * @throws IOException in case something goes wrong with in-/output streams
	 */
	Handler(final Socket client, final DatabaseConnector dbcon) throws IOException {
		this.client = client;
		this.dbcon = dbcon;
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
		isAuthorized = false;
	}

	@Override
	public void run() {
		try {
			String command;
			while((command = in.readLine()) != null) {
				handleCommand(command);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}

	/**
	 * Fully authorizes the client or closes the connection.
	 *
	 * @param success if true, client gets authorized, if false, client connection will be closed
	 */
	public void webloginResult(final boolean success) {
		out.println("weblogin");
		if (success) {
			isAuthorized = true;
			out.println("success;Welcome");
			out.flush();
		} else {
			out.println("fail;Web authentification failed");
			out.flush();
			closeSocketConnection();
		}
	}

	/**
	 * Handles the commands that come over the socket connection.
	 *
	 * @param command the command to handle
	 * @throws IOException in case something goes wrong with in-/output streams
	 */
	private void handleCommand(String command) throws IOException {
		if (isAuthorized) {
			switch(command) {
			case "echo":
				out.println("Echo: " + in.readLine());
				out.flush();
				break;
			case "logout":
				//TODO
				break;
			case "requestotp":
				//TODO
				break;
			}
		} else {
			switch(command) {
			case "register":
				register();
				break;
			case "login":
				login();
				break;
			}
		}
	}

	private void register() throws IOException {
		String userdata = in.readLine();

		//TODO decrypt!

		String[] userdataArray = userdata.split(";");
		try {
			if (dbcon.createUser(userdataArray[0], userdataArray[1], userdataArray[2])) {
				out.println("register");
				out.println("success");
			} else {
				out.println("register");
				out.println("fail");
			}
		} catch (NoSuchAlgorithmException | SQLException | IndexOutOfBoundsException e) {
			e.printStackTrace();
			out.println("register");
			out.println("fail");
		} finally {
			out.flush();
			closeSocketConnection();
		}
	}

	private void login() throws IOException {
		String userdata = in.readLine();

		//TODO decrypt!

		String[] userdataArray = userdata.split(";");
		user = dbcon.checkDesktopPassword(userdataArray[4], userdataArray[3]);

		if (user == null) {
			out.println("login");
			out.println("fail;Password/Username wrong or entered wrong too many times.");
			out.flush();
			closeSocketConnection();
		} else {
			//TODO calulate D-H
			BigInteger B = new BigInteger("1");

			out.println("login");
			out.println("success;" + B.toString());
			out.flush();

			//TODO encrypt stream
		}
	}

	private void closeSocketConnection() {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				//only happens when allready closed -> dont care
			}
		}
	}

}
