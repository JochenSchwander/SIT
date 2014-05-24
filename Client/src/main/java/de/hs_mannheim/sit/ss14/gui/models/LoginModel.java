package de.hs_mannheim.sit.ss14.gui.models;

import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LoginModel {
	
	public JTextField usernameTextfield;
	public JTextField passwordTextfield;
	public JTextArea credentialsMessageTextarea;
	public JTextArea infoTextarea;
	
	public LoginModel(){
		usernameTextfield = new JTextField();
		passwordTextfield = new JPasswordField();
		credentialsMessageTextarea = new JTextArea();
		infoTextarea= new JTextArea();
	}

}
