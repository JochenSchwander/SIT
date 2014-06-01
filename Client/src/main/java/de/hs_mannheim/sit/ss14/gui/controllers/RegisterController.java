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

/**
 * Controller for then RegisternTab and the whole register process.
 * 
 * @author DS
 * 
 */
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
	 * Starts the register process: 1. establish connection to server 2. sends
	 * the users register form to the server, encrypted with RSA 3. shows if the
	 * user could successfully be registered.
	 */
	@SuppressWarnings("deprecation")
	private void startRegisterProcess() {

		if (checkInputValidity()) {
			try {
				socket = new ClientSocket();
				String recievedMessage;
				RSAEncrypter rsa = new RSAEncrypter();

				socket.sendMessage("register\n"
						+ rsa.encrypt(registerModel.usernameTextfield.getText()
								+ ";"
								+ registerModel.desktopPasswordTextfield
										.getText() + ";"
								+ registerModel.webPasswordTextfield.getText()));

				recievedMessage = socket.recieveMessage();
				// check if matches this pattern: "login" then a new line "fail"
				// or "success" and the message
				if (recievedMessage.equals("register")) {
					recievedMessage = socket.recieveMessage();
					String[] recievedMessageArray = recievedMessage.split(";");

					if (recievedMessageArray[0].equals("success")) {
						registerModel.infoTextarea
								.setText("You have successfully been registered for using the application.");
						socket.closeConnection();

					} else { // if failed
						registerModel.usernameMessageTextarea
								.setText("This username is already in use.");
						socket.closeConnection();
					}
				}

			} catch (IOException e) {
				registerModel.infoTextarea
						.setText("The connection to the server could not be established.");
			}

		}

	}

	/**
	 * checks if fields are empty and the corresponding passwords match each other
	 * 
	 * @return
	 */
	private boolean checkInputValidity() {
		registerModel.usernameMessageTextarea.setText("");
		registerModel.webPasswordsMessageTextarea.setText("");
		registerModel.desktopPasswordsMessageTextarea.setText("");

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
