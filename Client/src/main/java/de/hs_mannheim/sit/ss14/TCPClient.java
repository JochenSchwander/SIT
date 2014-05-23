package de.hs_mannheim.sit.ss14;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

class TCPClient {
	public static void main(String argv[]) throws Exception {
		Socket clientSocket;
		String sentence;
		String modifiedSentence;
		try {
			clientSocket = new Socket("localhost", 31337);
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			sentence = "echo\n HAllo server";
			// sentence = inFromUser.readLine();
			outToServer.writeBytes(sentence + '\n');
			modifiedSentence = inFromServer.readLine();
			System.out.println("FROM SERVER: " + modifiedSentence);
			clientSocket.close();
		} catch (ConnectException e) {
			System.out.println("Verbindungsfehler.");
		}
	}
}