package de.hs_mannheim.sit.ss14.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import de.hs_mannheim.sit.ss14.User;
import de.hs_mannheim.sit.ss14.crypto.DiffieHellman;
import de.hs_mannheim.sit.ss14.database.DatabaseConnector;

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
			//TODO session cleanup!
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
				closeSocketConnection();
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
			case "requestotp":
				requestotp();
				break;
			}
		}
	}

	/**
	 * Register a new User.
	 *
	 * @throws IOException
	 */
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

	/**
	 * Try to login an existing user.
	 *
	 * @throws IOException
	 */
	private void login() throws IOException {
		String userdata = in.readLine();

		//TODO decrypt!

		String[] userdataArray = userdata.split(";");
		user = dbcon.checkDesktopPassword(userdataArray[2], userdataArray[1]);

		//TODO check if already logged in
		if (user == null) {
			out.println("login");
			out.println("fail;Password/Username wrong or entered wrong too many times.");
			out.flush();
			closeSocketConnection();
		} else {
			DiffieHellman dh = new DiffieHellman();
			String B = "";
			String K;
			try {
				B = dh.calculatePublicKey(userdataArray[0]);
				K = dh.calculateSharedSecret();
			} catch (Exception e) {
				//TODO was machen?
				e.printStackTrace();
			}

			out.println("login");
			out.println("success;" + B);
			out.flush();

			//TODO encrypt stream
		}
	}

	/**
	 * Send the otp from the logged in user to the client.
	 */
	private void requestotp() {
		out.println("requestotp");

		if (user == null) {
			out.println(" ; ");
		} else {
			out.println(user.getOneTimeCode() + ";" + user.getSalt());
		}

		out.flush();
	}

	/**
	 * Close the connection to the client.
	 */
	private void closeSocketConnection() {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				//only happens when already closed -> dont care
			}
		}
	}

}
