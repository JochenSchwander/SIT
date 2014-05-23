package de.hs_mannheim.sit.ss14;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {
	
	private String address="localhost";
	private int port=31337;
	private Socket clientSocket;
	
	public void establishConnection() throws UnknownHostException, IOException{
		clientSocket = new Socket(address,port);
		
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		outToServer.writeBytes("MSG from Client" + '\n');

		System.out.println("FROM SERVER: " + inFromServer.readLine());
		
	}
	
	public void closeConnection() throws IOException{
		clientSocket.close();		
	}
	
	
	

	public static void main(String argv[]) throws Exception {


	}

}
