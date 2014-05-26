package de.hs_mannheim.sit.ss14.gui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.hs_mannheim.sit.ss14.auxiliaries.*;
import de.hs_mannheim.sit.ss14.gui.models.LoginModel;

/**
 * Controller for then LoginTab and the whole loginprocess.
 * 
 * @author DS
 * 
 */
public class LoginController {

	private GuiController guiController;
	private ClientSocket socket;
	private LoginModel loginModel;

	LoginController(GuiController guiController, LoginModel loginModel) {
		this.guiController = guiController;
		this.loginModel = loginModel;
		this.socket=guiController.socket;

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
	 * Für den Loginablauf zuständige Funktion. 1. Verbindungsaufbau zum Server.
	 * 2. Schlüsseltausch über Diffie-Hellmann, der mit RSA verschlüsselt ist.
	 * ab dann, Verbindung verschlüsselt mit AES 3. delegieren des
	 * Onetimepasswort-requests an die Funktion requestOtp();
	 * 
	 */
	@SuppressWarnings("deprecation")
	private void startLoginProcess() {
		if (loginModel.usernameTextfield.getText().length() == 0
				|| loginModel.passwordTextfield.getText().length() == 0) {
			loginModel.credentialsMessageTextarea
					.setText("Fields must not be empty.");
		} else {
			try {
				socket = new ClientSocket();
				ClientDiffieHellman dh = new ClientDiffieHellman();
				String recievedMessage;

				// send pk and credentials to client
				socket.sendMessage("login\n" + dh.calculatePublicKey() + ";"
						+ loginModel.usernameTextfield.getText() + ";"
						+ loginModel.passwordTextfield.getText());

				// recieve message
				recievedMessage = socket.recieveMessage();
				// check if matches this pattern: "login" then a new line "fail"
				// or "success" and the message
				if (recievedMessage.equals("login")) {
					recievedMessage = socket.recieveMessage();
					String[] recievedMessageArray = recievedMessage.split(";");

					if (recievedMessageArray[0].equals("success")) {
						// receive servers pk and generate shared secret and use
						// it to encrypt the connection
						socket.encryptConnectionWithKey(dh.calculateSharedSecret(recievedMessageArray[1]));
						requestOtp();

					} else { //if failed
						loginModel.infoTextarea
								.setText(recievedMessageArray[1]);
					}
				} else {
					throw new Exception("Communication with server failed.");
				}

				// requestOtp();
			} catch (IOException e) {
				loginModel.credentialsMessageTextarea
						.setText("The connection to the server could not be established.");
			} catch (Exception e) {
				loginModel.credentialsMessageTextarea
						.setText("We are sorry, an error occured.");
				e.printStackTrace();
			}
		}

		// guiController.startView.displayLoggedInView();

		// loginModel.credentialsMessageTextarea.setText("Username: "
		// + loginModel.usernameTextfield.getText() + "\nPasswort: "
		// + loginModel.passwordTextfield.getText());
		//
		// requestOtp();}
	}

	/**
	 * request the onetimepassword and salt and shows it to the user
	 * 
	 * @param loginModel
	 * @throws IOException
	 */
	private void requestOtp() throws IOException, Exception {
		socket.sendMessage("requestotp");

		if (socket.recieveMessage().equals("requestotp")) {
			String recievedMessage = socket.recieveMessage();
			String[] recievedMessageArray = recievedMessage.split(";");
			
			// Ausgabe des empfangenen OTPs

			loginModel.infoTextarea
					.setText("Your OTP:\n"
							+ recievedMessageArray[0]+"\nand Salt:\n"+recievedMessageArray[1]);
			
			guiController.startView.loginTab.displayOtp(loginModel);

		} else {
			throw new Exception("Communication with server failed.");
		}

	}

}
