package de.hs_mannheim.sit.ss14.gui.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.AbstractDocument;

import de.hs_mannheim.sit.ss14.gui.models.LoginModel;

/**
 * Beim Erstellen durch ViewStart wird die Ansicht für das erste Sicherheitskriterium gezeigt, und mit showOtp() wird dann das OTP angezeigt,
 * welches im Web eingegeben werden muss.
 * @author DS
 *
 */
public class LoginTab extends JPanel {

	private JButton btnLogin;
	private static final long serialVersionUID = 1L;;

	LoginTab(LoginModel loginModel) {
		
		// this element settings
		setLayout(new GridLayout(2, 1));

		// upper row
		JPanel upperPanel = new JPanel();
		add(upperPanel);
		upperPanel.setLayout(new GridLayout(1, 0, 0, 0));
		upperPanel.setBorder(new EmptyBorder(15, 10, 15, 10));
		
		JTextArea infoTextarea=loginModel.infoTextarea;
		upperPanel.add(loginModel.infoTextarea);
		infoTextarea.setText("Willkommen im Loginbereich. \n\nGeben Sie ihr erstes Sicherheitskriterium ein.");
		infoTextarea.setEditable(false);
		infoTextarea.setLineWrap(true);
		infoTextarea.setWrapStyleWord(true);
		infoTextarea.setBackground(SystemColor.control);
		
		// lower row
		JPanel lowerSplittedPanel = new JPanel();
		add(lowerSplittedPanel);
		lowerSplittedPanel.setLayout(new GridLayout(1, 2));

		// lower row left side panel
		JPanel lowerLeftSplittedPanel = new JPanel();
		lowerSplittedPanel.add(lowerLeftSplittedPanel);
		lowerLeftSplittedPanel.setLayout(new GridLayout(2, 1, 0, 0));

		// lower row left side upper panel
		JPanel usernamePanel = new JPanel();
		lowerLeftSplittedPanel.add(usernamePanel);
		usernamePanel.setLayout(new GridLayout(1, 0, 0, 0));
		usernamePanel.setBorder(new TitledBorder(
				new EmptyBorder(10, 10, 10, 10), "Benutzername",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JTextField usernameTextfield =	loginModel.usernameTextfield;
		usernamePanel.add(usernameTextfield);
		usernameTextfield.setFont(new Font("Tahoma", Font.PLAIN, 16));
		//((AbstractDocument) usernameTextfield.getDocument()).setDocumentFilter(new DocumentSizeFilter(20));

		// lower row left side lower panel
		JPanel passwordPanel = new JPanel();
		lowerLeftSplittedPanel.add(passwordPanel);
		passwordPanel.setLayout(new GridLayout(1, 0, 0, 0));
		passwordPanel.setBorder(new TitledBorder(
				new EmptyBorder(10, 10, 10, 10), "Passwort",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JTextField passwordTextfield =	loginModel.passwordTextfield;
		passwordPanel.add(passwordTextfield);
		//((AbstractDocument) passwordTextfield.getDocument()).setDocumentFilter(new DocumentSizeFilter(50));
		passwordTextfield.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordTextfield.addActionListener(loginModel.submitLoginAL);


		// lower row right side panel
		JPanel lowerRightSplittedPanel = new JPanel();
		lowerSplittedPanel.add(lowerRightSplittedPanel);
		lowerRightSplittedPanel.setLayout(new GridLayout(2, 1, 0, 0));

		// lower row right side upper panel
		JPanel credentialsMessagePanel = new JPanel();
		lowerRightSplittedPanel.add(credentialsMessagePanel);
		credentialsMessagePanel.setLayout(new GridLayout(1, 0, 0, 0));
		credentialsMessagePanel.setBorder(new EmptyBorder(15, 10, 15, 10));

		JTextArea credentialsMessageTextarea =	loginModel.credentialsMessageTextarea;
		credentialsMessageTextarea.setEditable(false);
		credentialsMessageTextarea.setLineWrap(true);
		credentialsMessageTextarea.setWrapStyleWord(true);
		credentialsMessageTextarea.setBackground(SystemColor.control);
		credentialsMessagePanel.add(credentialsMessageTextarea);

		// lower row right side lower panel
		JPanel LoginButtonPanel = new JPanel();
		lowerRightSplittedPanel.add(LoginButtonPanel);
		LoginButtonPanel.setBorder(new EmptyBorder(15, 10, 15, 10));
		LoginButtonPanel.setLayout(new GridLayout(1, 0, 0, 0));

		btnLogin = new JButton("Login");
		LoginButtonPanel.add(btnLogin);
		btnLogin.addActionListener(loginModel.submitLoginAL);

	}
	
	public void displayOtp(LoginModel loginModel){
		loginModel.usernameTextfield.setEnabled(false);
		loginModel.passwordTextfield.setEnabled(false);
		btnLogin.setEnabled(false);
		
	}
}
