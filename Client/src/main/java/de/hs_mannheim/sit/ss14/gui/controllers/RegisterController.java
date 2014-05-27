package de.hs_mannheim.sit.ss14.gui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.hs_mannheim.sit.ss14.auxiliaries.ClientSocket;
import de.hs_mannheim.sit.ss14.auxiliaries.RSAEncrypter;
import de.hs_mannheim.sit.ss14.gui.models.RegisterModel;

public class RegisterController {

	private RegisterModel registerModel;
	private ClientSocket socket;

	public RegisterController(RegisterModel registerModel) {
		this.registerModel = registerModel;

		initModel();
	}

	private void initModel() {
		registerModel.usernameTextfield = new JTextField();
		registerModel.desktopPasswordTextfield = new JPasswordField();
		registerModel.repeatDesktopPasswordTextfield = new JPasswordField();
		registerModel.webPasswordTextfield = new JPasswordField();
		registerModel.repeatWebPasswordTextfield = new JPasswordField();
		registerModel.infoTextarea = new JTextArea();
		registerModel.usernameMessageTextarea = new JTextArea();
		registerModel.desktopPasswordsMessageTextarea = new JTextArea();
		registerModel.webPasswordsMessageTextarea = new JTextArea();
		registerModel.submitRegisterAL = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startRegisterProcess();

			}
		};
	}

	/**
	 * Für den Registrierablauf zuständige Funktion. 1.Verbindungsaufbau zum
	 * Server. 2. Schlüsseltausch über Diffie-Hellmann, der mit RSA
	 * verschlüsselt ist. ab dann, Verbindung verschlüsselt mit AES 3.
	 * delegieren des Übertragung an die Funktion.
	 *
	 */
	@SuppressWarnings("deprecation")
	private void startRegisterProcess() {

		if (checkInputValidity()) {
			try {
				socket = new ClientSocket();
				String recievedMessage;
				RSAEncrypter rsa = new RSAEncrypter();

				socket.sendMessage("register\n" + rsa.encrypt(registerModel.usernameTextfield.getText() + ";" + registerModel.desktopPasswordTextfield.getText() + ";" + registerModel.webPasswordTextfield.getText()));

				recievedMessage = socket.recieveMessage();
				// check if matches this pattern: "login" then a new line "fail"
				// or "success" and the message
				if (recievedMessage.equals("register")) {
					recievedMessage = socket.recieveMessage();
					String[] recievedMessageArray = recievedMessage.split(";");

					if (recievedMessageArray[0].equals("success")) {
						registerModel.infoTextarea
								.setText("You have successfully been registered for using the application.");

					} else { // if failed
						registerModel.usernameMessageTextarea
								.setText("This username is already in use.");
					}
				}

			} catch (IOException e) {
				registerModel.infoTextarea
						.setText("The connection to the server could not be established.");
			}

		}

	}

	/**
	 * prüft ob die Felder leer sind, und die Passwörter übereinstimmen und
	 * zeigt es dem Nutzer.
	 *
	 * @return
	 */
	private boolean checkInputValidity() {
		boolean fieldsValid = true;
		if (registerModel.usernameTextfield.getText().length() == 0) {
			registerModel.usernameMessageTextarea
					.setText("You have to enter a username.");
			fieldsValid = false;
		}
		if (registerModel.desktopPasswordTextfield.getPassword().length == 0
				|| registerModel.repeatDesktopPasswordTextfield.getPassword().length == 0) {
			registerModel.desktopPasswordsMessageTextarea
					.setText("Passwordfields can not be empty.");
			fieldsValid = false;
		} else if (!arePasswordsEqual(
				registerModel.desktopPasswordTextfield.getPassword(),
				registerModel.repeatDesktopPasswordTextfield.getPassword())) {
			registerModel.desktopPasswordsMessageTextarea
					.setText("The entered passwords have to match.");
			fieldsValid = false;
		}
		if (registerModel.webPasswordTextfield.getPassword().length == 0
				|| registerModel.repeatWebPasswordTextfield.getPassword().length == 0) {
			registerModel.webPasswordsMessageTextarea
					.setText("Passwordfields can not be empty.");
			fieldsValid = false;
		} else if (!arePasswordsEqual(
				registerModel.webPasswordTextfield.getPassword(),
				registerModel.repeatWebPasswordTextfield.getPassword())) {
			registerModel.webPasswordsMessageTextarea
					.setText("The entered passwords have to match.");
			fieldsValid = false;
		}
		return fieldsValid;
	}

	private boolean arePasswordsEqual(char[] password, char[] password2) {
		if (password.length != password2.length) {
			return false;
		}
		for (int i = 0; i < password.length; i++) {
			if (password[i] != password2[i]) {
				return false;
			}
		}
		return true;
	}
}
