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
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 */
	public void encryptConnectionWithKey(byte[] key) throws IOException,
			Exception {
		System.out.println("secret: " + key);

		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		IvParameterSpec ivspec = new IvParameterSpec(iv);

		Cipher aesDec = Cipher.getInstance("AES/CBC/PKCS5Padding");
		aesDec.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), ivspec);
		in = new BufferedReader(new InputStreamReader(new CipherInputStream(
				clientSocket.getInputStream(), aesDec)));

		Cipher aesEnc = Cipher.getInstance("AES/CBC/PKCS5Padding");
		aesEnc.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), ivspec);
		out = new PrintWriter(new OutputStreamWriter(new CipherOutputStream(
				clientSocket.getOutputStream(), aesEnc)));

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
