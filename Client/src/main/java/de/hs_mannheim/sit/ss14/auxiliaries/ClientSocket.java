package de.hs_mannheim.sit.ss14.auxiliaries;

import java.io.*;
import java.net.Socket;

import javax.crypto.*;

/**
 * Stellt Funktionen für die Server-Client-Verbindung bereit.
 * 
 * @author DS
 * 
 */
public class ClientSocket {

	private String address = "localhost";
	private int port = 31337;
	private Socket clientSocket;
	private BufferedReader in;
	private PrintWriter out;

	/**
	 * Beim Instanziierung der Klasse wird automatisch eine Verbindung zum
	 * Server hergestellt.
	 * 
	 * @throws IOException
	 */
	public ClientSocket() throws IOException {
		clientSocket = new Socket(address, port);
		in = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		out = new PrintWriter(new OutputStreamWriter(
				clientSocket.getOutputStream()));
	}

	/**
	 * sobald diese Methode aufgerufen wurden, wird die Verbindung mit dem
	 * Schlüssel aus dem Parameter verschlüsselt.
	 * 
	 * @param key
	 * @throws IOException
	 */
	public void encryptConnectionWithKey(Cipher cipher) throws IOException {
		in = new BufferedReader(new InputStreamReader(new CipherInputStream(
				clientSocket.getInputStream(), cipher)));
		out = new PrintWriter(new OutputStreamWriter(new CipherOutputStream(
				clientSocket.getOutputStream(), cipher)));
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
