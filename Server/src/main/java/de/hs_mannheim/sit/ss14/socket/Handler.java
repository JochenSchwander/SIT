package de.hs_mannheim.sit.ss14.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import de.hs_mannheim.sit.ss14.DatabaseConnector;

class Handler implements Runnable {
	private Socket client;
	private DatabaseConnector dbcon;
	private BufferedReader in;
	private PrintWriter out;

	Handler(Socket client, DatabaseConnector dbcon) throws IOException {
		this.client = client;
		this.dbcon = dbcon;
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
	}

	@Override
	public void run() {
		try {
			String command;
			while((command = in.readLine()) != null) {
				System.out.println(command);
				handleCommand(command);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}

	private void handleCommand(String command) throws IOException {
		switch(command) {
		case "register":
			break;
		case "login":
			break;
		case "echo":
			out.println("Echo: " + in.readLine());
			out.flush();
			break;
		case "logout":
			break;
		case "requestotp":
			break;
		}
	}

}
