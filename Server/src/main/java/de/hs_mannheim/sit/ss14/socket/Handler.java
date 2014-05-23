package de.hs_mannheim.sit.ss14.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import de.hs_mannheim.sit.ss14.DatabaseConnector;
import de.hs_mannheim.sit.ss14.Hasher;
import de.hs_mannheim.sit.ss14.SHA512Hasher;

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
	private Hasher hasher;
	private boolean isAuthorized;

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
		hasher = new SHA512Hasher();
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
				//TODO
				break;
			case "login":
				//TODO
				break;
			}
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

}
