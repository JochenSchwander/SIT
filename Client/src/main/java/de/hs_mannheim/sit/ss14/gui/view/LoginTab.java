package de.hs_mannheim.sit.ss14.gui.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.AbstractDocument;

import de.hs_mannheim.sit.ss14.auxiliaries.DocumentSizeFilter;
import de.hs_mannheim.sit.ss14.gui.models.LoginModel;

/**
 * Beim Erstellen durch ViewStart wird die Ansicht für das erste
 * Sicherheitskriterium gezeigt, und mit showOtp() wird dann das OTP angezeigt,
 * welches im Web eingegeben werden muss.
 * 
 * @author DS
 * 
 */
@SuppressWarnings("serial")
public class LoginTab extends JPanel {

	LoginTab(LoginModel loginModel) {

		// this element settings
		setLayout(new GridLayout(3, 1));

		// upper row
		JPanel upperPanel = new JPanel();
		add(upperPanel);
		upperPanel.setLayout(new GridLayout(1, 0, 0, 0));
		upperPanel.setBorder(new EmptyBorder(15, 10, 15, 10));

		JTextArea infoTextarea = loginModel.infoTextarea;
		upperPanel.add(loginModel.infoTextarea);
		infoTextarea
				.setText("Welcome to the login area. \n\nPlease login with your credentials.");
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
				new EmptyBorder(10, 10, 10, 10), "Username",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JTextField usernameTextfield = loginModel.usernameTextfield;
		usernamePanel.add(usernameTextfield);
		((AbstractDocument) usernameTextfield.getDocument())
				.setDocumentFilter(new DocumentSizeFilter(100, "[A-Za-z0-9]+"));

		// lower row left side lower panel
		JPanel passwordPanel = new JPanel();
		lowerLeftSplittedPanel.add(passwordPanel);
		passwordPanel.setLayout(new GridLayout(1, 0, 0, 0));
		passwordPanel.setBorder(new TitledBorder(
				new EmptyBorder(10, 10, 10, 10), "Password",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JTextField passwordTextfield = loginModel.passwordTextfield;
		passwordPanel.add(passwordTextfield);
		((AbstractDocument) passwordTextfield.getDocument())
				.setDocumentFilter(new DocumentSizeFilter(100, ".+"));
		passwordTextfield.addActionListener(loginModel.submitLoginAL);

		// lower row right side panel
		JPanel lowerRightSplittedPanel = new JPanel();
		lowerSplittedPanel.add(lowerRightSplittedPanel);
		lowerRightSplittedPanel.setLayout(new GridLayout(1, 1, 0, 0));
		lowerRightSplittedPanel.setBorder(new EmptyBorder(15, 10, 15, 10));
		
		JTextArea credentialsMessageTextarea = loginModel.credentialsMessageTextarea;
		credentialsMessageTextarea.setEditable(false);
		credentialsMessageTextarea.setLineWrap(true);
		credentialsMessageTextarea.setWrapStyleWord(true);
		credentialsMessageTextarea.setBackground(SystemColor.control);
		lowerRightSplittedPanel.add(credentialsMessageTextarea);

		
		// some empty panels for styling reasons
		JPanel lowestPanel = new JPanel();
		add(lowestPanel);
		lowestPanel.setLayout(new GridLayout(3, 3, 0, 0));

		JPanel panel = new JPanel();
		lowestPanel.add(panel);
		
				// lower row right side lower panel
				JPanel LoginButtonPanel = new JPanel();
				lowestPanel.add(LoginButtonPanel);
		LoginButtonPanel.setLayout(new GridLayout(0, 1, 0, 0));

		JButton btnLogin = new JButton("Login");
		LoginButtonPanel.add(btnLogin);
		btnLogin.addActionListener(loginModel.submitLoginAL);

		JPanel panel_1 = new JPanel();
		lowestPanel.add(panel_1);

		JPanel panel_2 = new JPanel();
		lowestPanel.add(panel_2);

		JPanel panel_3 = new JPanel();
		lowestPanel.add(panel_3);

		JPanel panel_4 = new JPanel();
		lowestPanel.add(panel_4);

		JPanel panel_5 = new JPanel();
		lowestPanel.add(panel_5);
	}
}
