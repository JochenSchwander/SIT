package de.hs_mannheim.sit.ss14.gui.models;

import java.awt.event.ActionListener;

import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LoginModel {
	
	public JTextField usernameTextfield;
	public JPasswordField passwordTextfield;
	public JTextArea credentialsMessageTextarea;
	public JTextArea infoTextarea;
	public ActionListener submitLoginAL;
	
	public LoginModel(){
	}

}
