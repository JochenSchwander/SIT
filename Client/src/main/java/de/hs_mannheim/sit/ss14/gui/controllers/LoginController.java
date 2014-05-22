package de.hs_mannheim.sit.ss14.gui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.hs_mannheim.sit.ss14.gui.models.LoginModel;

public class LoginController {

	/**
	 * ActionListener for when the Login button gets pressed.
	 * 
	 * @param loginModel
	 * @return
	 */
	public ActionListener getBtnLoginAL(final LoginModel loginModel) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginModel.credentialsMessageTextarea.setText("Username: "
						+ loginModel.usernameTextfield.getText()
						+ "\nPasswort: "
						+ loginModel.passwordTextfield.getText());

			}
		};
	}

}
