package de.hs_mannheim.sit.ss14.gui.models;

import java.awt.event.ActionListener;

import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RegisterModel {
	public JTextField usernameTextfield;
	public JPasswordField desktopPasswordTextfield;
	public JPasswordField repeatDesktopPasswordTextfield;
	public JPasswordField webPasswordTextfield;
	public JPasswordField repeatWebPasswordTextfield;
	
	public JTextArea infoTextarea;
	public JTextArea usernameMessageTextarea;
	public JTextArea desktopPasswordsMessageTextarea;
	public JTextArea webPasswordsMessageTextarea;
	
	public ActionListener submitRegisterAL;

	public RegisterModel() {
	}

}
