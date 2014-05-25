package de.hs_mannheim.sit.ss14.gui.models;

import java.awt.event.ActionListener;

import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LoginModel {
	
	public JTextField usernameTextfield;
	public JTextField passwordTextfield;
	public JTextArea credentialsMessageTextarea;
	public JTextArea infoTextarea;
	/**
	 * ActionListener der reagiert sobald der Loginbutton gedrückt wird, oder "Enter" im Passwortfeld gedrückt wird.
	 */
	public ActionListener submitLoginAL;
	
	public LoginModel(){
	}

}
