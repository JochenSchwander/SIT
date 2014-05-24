package de.hs_mannheim.sit.ss14.gui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import de.hs_mannheim.sit.ss14.ClientSocket;
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

	LoginController(GuiController guiController) {
		this.guiController = guiController;
	}

	/**
	 * ActionListener der reagiert sobald der Loginbutton gedrückt wird.
	 * 
	 * @param loginModel
	 * @return
	 */
	public ActionListener getBtnLoginAL(final LoginModel loginModel) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startLoginProcess(loginModel);
			}
		};
	}

	/**
	 * * ActionListener der reagiert sobald "Enter" im Passwordfeld gedrückt
	 * wurde.
	 * 
	 * @param loginModel
	 * @return
	 */
	public ActionListener getPasswordTextfieldAL(final LoginModel loginModel) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startLoginProcess(loginModel);
			}
		};
	}

	/**
	 * Für den Loginablauf zuständige Funktion. 1.Verbindungsaufbau zum Server.
	 * 2. Schlüsseltausch über Diffie-Hellmann, der mit RSA verschlüsselt ist.
	 * ab dann, Verbindung verschlüsselt mit AES 3. delegieren des
	 * Onetimepasswort-requests and die Funktion.
	 * 
	 * @param loginModel
	 */
	private void startLoginProcess(LoginModel loginModel) {
		socket = new ClientSocket();
		try {
			socket.establishConnection();
		} catch (IOException e) {
			loginModel.credentialsMessageTextarea
					.setText("Die Verbindung zum Server konnte nicht hergestellt werden.");
		}

		// guiController.setLoggedInView();

		// loginModel.credentialsMessageTextarea.setText("Username: "
		// + loginModel.usernameTextfield.getText() + "\nPasswort: "
		// + loginModel.passwordTextfield.getText());
		//
		requestOtp(loginModel);
	}


	/**
	 * Fordert das Onetimepasswort und den Salt vom Server an und zeigt es auf dem Client.
	 * @param loginModel
	 */
	private void requestOtp(LoginModel loginModel) {

	}

}
