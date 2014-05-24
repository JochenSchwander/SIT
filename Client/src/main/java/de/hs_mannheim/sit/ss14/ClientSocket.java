package de.hs_mannheim.sit.ss14;

import java.io.*;
import java.net.Socket;

/**
 * Stellt Funktionen für die Server-Client-Verbindung bereit.
 * @author DS
 * 
 */
public class ClientSocket {

	private String address = "localhost";
	private int port = 31337;
	private Socket clientSocket;
	private FilterOutputStream outputFilter;
	private FilterInputStream inputtFilter;
	


	public void establishConnection() throws IOException {
		System.out.println("stelle Verbindung her");
		clientSocket = new Socket(address, port);
		System.out.println("stelle Verbindung her");
	}

	public void sendMessage(String sendString) throws IOException {
		PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(
				clientSocket.getOutputStream()));
		outToServer.println(sendString);
		outToServer.flush();
	}

	public String recieveMessage() throws IOException {
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		return inFromServer.readLine();
	}

	public void closeConnection() throws IOException {
		clientSocket.close();
	}
}
