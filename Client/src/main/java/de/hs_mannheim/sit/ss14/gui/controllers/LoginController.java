package de.hs_mannheim.sit.ss14.gui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import de.hs_mannheim.sit.ss14.ClientSocket;
import de.hs_mannheim.sit.ss14.gui.models.LoginModel;

public class LoginController {
	
	private GuiController guiController;
	LoginController(GuiController guiController){
		this.guiController=guiController;
		
	}

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
				startLoginProcess(loginModel);
			}
		};
	}

	public ActionListener getPasswordTextfieldAL(final LoginModel loginModel) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startLoginProcess(loginModel);
			}
		};
	}

	private void startLoginProcess(LoginModel loginModel) {
		ClientSocket socket=new ClientSocket();
		try {
			socket.establishConnection();
		} catch ( IOException e) {
			loginModel.credentialsMessageTextarea.setText("Die Verbindung zum Server konnte nicht hergestellt werden.");
		}

		
		
		
		
		
//		loginModel.credentialsMessageTextarea.setText("Username: "
//				+ loginModel.usernameTextfield.getText() + "\nPasswort: "
//				+ loginModel.passwordTextfield.getText());
//		
		
		
		
	}

}
