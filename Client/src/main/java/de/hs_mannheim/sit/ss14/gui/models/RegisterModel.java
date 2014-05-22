package de.hs_mannheim.sit.ss14.gui.models;

import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RegisterModel {

	public JTextField usernameTextfield;
	public JTextField desktopPasswordTextfield;
	public JTextField repeatDesktopPasswordTextfield;
	public JTextField webPasswordTextfield;
	public JTextField repeatWebPasswordTextfield;
	public JTextArea usernameMessageTextarea;
	public JTextArea desktopPasswordsMessageTextarea;
	public JTextArea wesktopPasswordsMessageTextarea;

	public RegisterModel() {
		usernameTextfield = new JTextField();
		desktopPasswordTextfield = new JPasswordField();
		repeatDesktopPasswordTextfield = new JPasswordField();
		webPasswordTextfield = new JPasswordField();
		repeatWebPasswordTextfield = new JPasswordField();
		usernameMessageTextarea = new JTextArea();
		desktopPasswordsMessageTextarea = new JTextArea();
		wesktopPasswordsMessageTextarea = new JTextArea();

	}

}
