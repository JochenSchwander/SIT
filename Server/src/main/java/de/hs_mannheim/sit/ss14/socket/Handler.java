package de.hs_mannheim.sit.ss14.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import de.hs_mannheim.sit.ss14.crypto.DiffieHellman;
import de.hs_mannheim.sit.ss14.database.DatabaseConnector;
import de.hs_mannheim.sit.ss14.sync.User;

/**
 * Handles a single socket Connection.
 *
 * @author Jochen Schwander
 */
public class Handler implements Runnable {
	private Socket client;
	private DatabaseConnector dbcon;
	private BufferedReader in;
	private PrintWriter out;
	private Status status;
	private User user;

	/**
	 * Represents the status of the connected User.
	 *
	 * @author Jochen Schwander
	 */
	private enum Status {
		AUTHORIZED, PENDING, UNAUTHORIZED;
	}

	/**
	 * Constructor.
	 *
	 * @param client
	 *            socket on which the client is connected
	 * @param dbcon
	 *            database connection, which should be used for login checks
	 * @throws IOException
	 *             in case something goes wrong with in-/output streams
	 */
	Handler(final Socket client, final DatabaseConnector dbcon) throws IOException {
		this.client = client;
		this.dbcon = dbcon;
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
		status = Status.UNAUTHORIZED;
	}

	@Override
	public void run() {
		try {
			String command;
			while ((command = in.readLine()) != null) {
				handleCommand(command);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// TODO session cleanup!
		}
	}

	/**
	 * Closes the clients connection.
	 */
	public void webloginFail() {
		out.println("weblogin");
		out.println("fail;Web authentification failed");
		out.flush();
		closeSocketConnection();
	}


	/**
	 * Fully authorizes the client.
	 */
	public void webloginSuccess() {
		out.println("weblogin");
		status = Status.AUTHORIZED;
		out.println("success;Welcome " + user.getUserName());
		out.flush();
	}

	/**
	 * Handles the commands that come over the socket connection.
	 *
	 * @param command
	 *            the command to handle
	 * @throws IOException
	 *             in case something goes wrong with in-/output streams
	 */
	private void handleCommand(final String command) throws IOException {
		switch (status) {
		case AUTHORIZED:
			switch (command) {
			case "echo":
				out.println("Echo: " + in.readLine());
				out.flush();
				break;
			case "logout":
				closeSocketConnection();
				break;
			}
			break;
		case PENDING:
			switch (command) {
			case "requestotp":
				requestotp();
				break;
			}
			break;
		case UNAUTHORIZED:
			switch (command) {
			case "register":
				register();
				break;
			case "login":
				login();
				break;
			}
			break;
		}
	}

	/**
	 * Register a new User.
	 *
	 * @throws IOException
	 */
	private void register() throws IOException {
		String userdata = in.readLine();

		// TODO decrypt!

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

		// TODO decrypt!

		String[] userdataArray = userdata.split(";");
		user = dbcon.checkDesktopPassword(userdataArray[2], userdataArray[1]);

		// TODO check if already logged in
		if (user == null) {
			out.println("login");
			out.println("fail;Password/Username wrong or entered wrong too many times.");
			out.flush();
			closeSocketConnection();
		} else {
			DiffieHellman dh = new DiffieHellman();
			String B = "";
			String K = null;
			try {
				B = dh.calculatePublicKey(userdataArray[0]);
				K = dh.calculateSharedSecret();
			} catch (Exception e) {
				// TODO was machen?
				e.printStackTrace();
			}

			out.println("login");
			out.println("success;" + B);
			out.flush();

			// TODO close streams?
			try {
				byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
				IvParameterSpec ivspec = new IvParameterSpec(iv);

				Cipher aesDec = Cipher.getInstance("AES/CBC/PKCS5Padding");
				aesDec.init(Cipher.DECRYPT_MODE, new SecretKeySpec(hexStringToByteArray(K), "AES"), ivspec);
				in = new BufferedReader(new InputStreamReader(new CipherInputStream(client.getInputStream(), aesDec)));

				Cipher aesEnc = Cipher.getInstance("AES/CBC/PKCS5Padding");
				aesEnc.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(hexStringToByteArray(K), "AES"), ivspec);
				out = new PrintWriter(new OutputStreamWriter(new CipherOutputStream(client.getOutputStream(), aesEnc)));

			} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
				// TODO was machen?
				e.printStackTrace();
			}
		}
	}

	/**
	 * Send the otp from the logged in user to the client.
	 */
	private void requestotp() {
		out.println("requestotp");
		out.println(user.getOneTimeCode() + ";" + user.getSalt());
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
				// only happens when already closed -> dont care
			}
		}
	}

	/**
	 * Convert the D-H key to a byte array.
	 *
	 * @param s
	 *            D-H key
	 * @return D-H key as byte array
	 */
	private static byte[] hexStringToByteArray(final String hexString) {
		return (new BigInteger(hexString, 16)).toByteArray();
	}

}
