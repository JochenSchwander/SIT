package de.hs_mannheim.sit.ss14.gui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.hs_mannheim.sit.ss14.auxiliaries.ClientSocket;
import de.hs_mannheim.sit.ss14.gui.models.LoginModel;
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
	 * F�r den Registrierablauf zust�ndige Funktion. 1.Verbindungsaufbau zum
	 * Server. 2. Schl�sseltausch �ber Diffie-Hellmann, der mit RSA
	 * verschl�sselt ist. ab dann, Verbindung verschl�sselt mit AES 3.
	 * delegieren des �bertragung an die Funktion.
	 * 
	 */
	private void startRegisterProcess() {
		registerModel.usernameMessageTextarea.setText("");
		registerModel.desktopPasswordsMessageTextarea.setText("");
		registerModel.webPasswordsMessageTextarea.setText("");
		registerModel.infoTextarea.setText("Hier k�nnen sie sich f�r die Benutzung der Anwendung registrieren.");
		
		if (checkInputValidity()) {

		}

	}

	/**
	 * pr�ft ob die Felder leer sind, und die Passw�rter �bereinstimmen und
	 * zeigt es dem Nutzer.
	 * 
	 * @return
	 */
	private boolean checkInputValidity() {	
		boolean fieldsValid = true;
		if (registerModel.usernameTextfield.getText().length() == 0) {
			registerModel.usernameMessageTextarea
					.setText("Sie m�ssen einen Benutzernamen eingeben.");
			fieldsValid = false;
		}
		if (registerModel.desktopPasswordTextfield.getPassword().length == 0
				|| registerModel.repeatDesktopPasswordTextfield.getPassword().length == 0) {
			registerModel.desktopPasswordsMessageTextarea
					.setText("Die Passwortfelder d�rfen nicht leer sein.");
			fieldsValid = false;
		} else if (!arePasswordsEqual(
				registerModel.desktopPasswordTextfield.getPassword(),
				registerModel.repeatDesktopPasswordTextfield.getPassword())) {
			registerModel.desktopPasswordsMessageTextarea
					.setText("Die Passw�rter stimmen nicht �berein.");
			fieldsValid = false;
		}
		if (registerModel.webPasswordTextfield.getPassword().length == 0
				|| registerModel.repeatWebPasswordTextfield.getPassword().length == 0) {
			registerModel.webPasswordsMessageTextarea
					.setText("Die Passwortfelder d�rfen nicht leer sein.");
			fieldsValid = false;
		} else if (!arePasswordsEqual(
				registerModel.webPasswordTextfield.getPassword(),
				registerModel.repeatWebPasswordTextfield.getPassword())) {
			registerModel.webPasswordsMessageTextarea
					.setText("Die Passw�rter stimmen nicht �berein.");
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
