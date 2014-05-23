package de.hs_mannheim.sit.ss14.dummy;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

class TCPClient {
	public static void main(String argv[]) throws Exception {
		Socket clientSocket;
		String sentence;
		String modifiedSentence;
		try {
			clientSocket = new Socket("localhost", 31337);
			PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			sentence = "echo\n HAllo server";
			// sentence = inFromUser.readLine();
			outToServer.println(sentence);
			outToServer.flush();
			modifiedSentence = inFromServer.readLine();
			System.out.println("FROM SERVER: " + modifiedSentence);
			clientSocket.close();
		} catch (ConnectException e) {
			System.out.println("Verbindungsfehler.");
		}
	}
}