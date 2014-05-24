package de.hs_mannheim.sit.ss14.gui.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import de.hs_mannheim.sit.ss14.gui.controllers.LoginController;
import de.hs_mannheim.sit.ss14.gui.models.LoginModel;

public class LoginTab extends JPanel {

	private static final long serialVersionUID = 1L;;

	LoginTab(LoginModel loginModel,LoginController loginController ) {
		
		// this element settings
		setLayout(new GridLayout(2, 1));

		// upper row
		JPanel upperPanel = new JPanel();
		add(upperPanel);
		upperPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel text=new JLabel("Geben Sie ihr erstes Sicherheitskriterium ein.");
		upperPanel.add(text);
		
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

		// lower row left side lower panel
		JPanel passwordPanel = new JPanel();
		lowerLeftSplittedPanel.add(passwordPanel);
		passwordPanel.setLayout(new GridLayout(1, 0, 0, 0));
		passwordPanel.setBorder(new TitledBorder(
				new EmptyBorder(10, 10, 10, 10), "Passwort",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JTextField passwordTextfield =	loginModel.passwordTextfield;
		passwordPanel.add(passwordTextfield);
		passwordTextfield.addActionListener(loginController.getBtnLoginAL(loginModel));


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
		LoginButtonPanel.setBorder(new EmptyBorder(15, 10, 15, 10));
		lowerRightSplittedPanel.add(LoginButtonPanel);
		LoginButtonPanel.setLayout(new GridLayout(1, 0, 0, 0));

		JButton btnLogin = new JButton("Login");
		LoginButtonPanel.add(btnLogin);
		btnLogin.addActionListener(loginController.getBtnLoginAL(loginModel));

	}
}
