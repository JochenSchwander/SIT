package de.hs_mannheim.sit.ss14.auxiliaries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class functions as the connection-layer between server and client
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
	 * At instantiation this class automatically connects to the server
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
	 * as soon as this function is successfully called the connection is AES
	 * encrypted
	 * 
	 * @param key
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 * @throws InterruptedException 
	 */
	public void encryptConnectionWithKey(byte[] K) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, InterruptedException {

		byte[] key = new byte[32];

		for (int i = 0; i < key.length; i++) {
			key[i] = K[K.length - key.length + i];
		}

		Cipher aesDec = Cipher.getInstance("AES/CFB8/NoPadding");
		Cipher aesEnc = Cipher.getInstance("AES/CFB8/NoPadding");

		IvParameterSpec ivspec = new IvParameterSpec(
				new byte[aesDec.getBlockSize()]);

		aesDec.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), ivspec);
		aesEnc.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), ivspec);

		in = new BufferedReader(new InputStreamReader(new CipherInputStream(
				clientSocket.getInputStream(), aesDec)));
		out = new PrintWriter(new OutputStreamWriter(new CipherOutputStream(
				clientSocket.getOutputStream(), aesEnc)));

		Thread.sleep(4000);
	}

	public void sendMessage(String sendString) throws IOException {
		out.println(sendString);
		out.flush();
	}

	public String recieveMessage() throws IOException {
		return in.readLine();
	}

	public void closeConnection() throws IOException {
		clientSocket.close();
	}

}
