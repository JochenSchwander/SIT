package de.hs_mannheim.sit.ss14.gui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.NoSuchPaddingException;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.hs_mannheim.sit.ss14.auxiliaries.ClientDiffieHellman;
import de.hs_mannheim.sit.ss14.auxiliaries.ClientSocket;
import de.hs_mannheim.sit.ss14.auxiliaries.RSAEncrypter;
import de.hs_mannheim.sit.ss14.gui.models.LoginModel;

/**
 * Controller for then LoginTab and the whole login process.
 *
 * @author DS
 *
 */
public class LoginController {

	private GuiController guiController;
	private LoginModel loginModel;

	LoginController(GuiController guiController, LoginModel loginModel) {
		this.guiController = guiController;
		this.loginModel = loginModel;

		initModel();

	}

	private void initModel() {
		loginModel.usernameTextfield = new JTextField();
		loginModel.passwordTextfield = new JPasswordField();
		loginModel.credentialsMessageTextarea = new JTextArea();
		loginModel.infoTextarea = new JTextArea();
		loginModel.submitLoginAL = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startLoginProcess();
			}
		};

	}

	/**
	 * Starts the login process: 1. establish connection to server 2.
	 * diffie-hellman key exchange, already encrypted with RSA to avoid
	 * man-in-the-middle 3. deligate Onetimepasswort-requests to the
	 * guiController that starts the OtpController
	 */
	@SuppressWarnings("deprecation")
	private void startLoginProcess() {
		if (loginModel.usernameTextfield.getText().length() == 0
				|| loginModel.passwordTextfield.getText().length() == 0) {
			loginModel.credentialsMessageTextarea
					.setText("Fields must not be empty.");
		} else {
			try {
				guiController.socket = new ClientSocket();
				ClientDiffieHellman dh = new ClientDiffieHellman();

				String recievedMessage;

				// send pk and credentials to client
				RSAEncrypter rsa = new RSAEncrypter();

				guiController.socket.sendMessage("login\n"
						+ rsa.encrypt(dh.calculatePublicKey() + ";"
								+ loginModel.usernameTextfield.getText() + ";"
								+ loginModel.passwordTextfield.getText()));

				// recieve message
				recievedMessage = guiController.socket.recieveMessage();
				// check if matches this pattern: "login" then a new line "fail"
				// or "success" and the message
				if (recievedMessage.equals("login")) {
					recievedMessage = guiController.socket.recieveMessage();
					String[] recievedMessageArray = recievedMessage.split(";");

					if (recievedMessageArray[0].equals("success")) {
						// receive servers pk and generate shared secret and use
						// it to encrypt the connection
						guiController.socket
								.encryptConnectionWithKey(dh
										.calculateSharedSecret(recievedMessageArray[1]));
						guiController.displayOtpView();

					} else { // if failed
						loginModel.infoTextarea
								.setText(recievedMessageArray[1]);
					}
				} else {
					loginModel.infoTextarea
							.setText("There was a problem communicating with the server.");
				}
			} catch (IOException e) {
				loginModel.credentialsMessageTextarea
						.setText("The connection to the server could not be established.");
			} catch (InvalidKeyException | NoSuchAlgorithmException
					| InvalidParameterSpecException
					| InvalidAlgorithmParameterException | InterruptedException
					| NoSuchPaddingException | InvalidKeySpecException
					| IllegalStateException e) {
				loginModel.credentialsMessageTextarea
						.setText("We are sorry, an error occured.");
				e.printStackTrace();

			}
		}
	}
}
