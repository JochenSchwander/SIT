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
 * Controller für den LoginTab und den Loginprozess
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
	private void startLoginProcess() {
		if (loginModel.usernameTextfield.getText().length() == 0
				|| loginModel.passwordTextfield.getPassword().length == 0) {
			loginModel.credentialsMessageTextarea
					.setText("Die Felder dürfen nicht leer sein.");
		} else {
			try {
				socket = new ClientSocket();
				ClientDiffieHellman dh = new ClientDiffieHellman();

				// send pk to server
				socket.sendMessage(""+dh.calculatePublicKey());
				//receive servers pk and generate shared secret
				System.out.println("Secret: "+dh.calculateSharedSecret(socket.recieveMessage()));
				//socket.encryptConnectionWithKey();

				// requestOtp();
			} catch (IOException e) {
				loginModel.credentialsMessageTextarea
						.setText("Die Verbindung zum Server konnte nicht hergestellt werden.");
			} catch (Exception e) {
				loginModel.credentialsMessageTextarea
						.setText("Es ist ein Fehler aufgetreten.");
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
	 * Fordert das Onetimepasswort und den Salt vom Server an und zeigt es auf
	 * dem Client.
	 * 
	 * @param loginModel
	 */
	private void requestOtp() {

		// Ausgabe des empfangenen OTPs
		String otpString = "otp string";
		loginModel.infoTextarea
				.setText("Um ihr zweites Sicherheitskriterium eingeben zu können benötigen Sie dieses OneTimePasswort:\n\n"
						+ otpString);
		loginModel.credentialsMessageTextarea
				.setText("Ihre Anmeldedaten waren korrekt.");
		guiController.startView.loginTab.displayOtp(loginModel);

	}

}
