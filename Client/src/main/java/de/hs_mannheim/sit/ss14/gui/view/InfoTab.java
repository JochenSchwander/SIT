package de.hs_mannheim.sit.ss14.gui.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import de.hs_mannheim.sit.ss14.gui.controllers.*;
import de.hs_mannheim.sit.ss14.gui.models.*;

public class InfoTab extends JPanel {

	private static final long serialVersionUID = 1L;

	InfoTab() {
		// this element settings
		setLayout(new GridLayout(2, 1));

		// upper row
		JPanel upperPanel = new JPanel();
		add(upperPanel);
		upperPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblSit = new JLabel("SIT");
		upperPanel.add(lblSit);

		// lower row
		JPanel lowerSplittedPanel = new JPanel();
		add(lowerSplittedPanel);
		lowerSplittedPanel.setLayout( new GridLayout(1, 2));


		
		// lower row left side panel
		JPanel lowerLeftSplittedPanel = new JPanel();
		lowerSplittedPanel.add(lowerLeftSplittedPanel);
		lowerLeftSplittedPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		// lower row left side upper panel
		JPanel usernamePanel = new JPanel();
		lowerLeftSplittedPanel.add(usernamePanel);
		usernamePanel.setLayout(new GridLayout(1, 0, 0, 0));
		usernamePanel.setBorder(new TitledBorder(new EmptyBorder(10, 10, 10, 10), "Benutzername", TitledBorder.LEADING, TitledBorder.TOP, null, null) );
		
		JTextField usernameTextfield = new JTextField(10);
		usernamePanel.add(usernameTextfield);
		
		// lower row left side lower panel
		JPanel passwordPanel = new JPanel();
		lowerLeftSplittedPanel.add(passwordPanel);
		passwordPanel.setLayout(new GridLayout(1, 0, 0, 0));
		passwordPanel.setBorder(new TitledBorder(new EmptyBorder(10, 10, 10, 10), "Passwort", TitledBorder.LEADING, TitledBorder.TOP, null, null) );

		JTextField passwordTextfield = new JPasswordField(10);
		//passwordTextfield.setActionCommand(OK);
		//passwordTextfield.addActionListener(passwordTextfield);
		passwordPanel.add(passwordTextfield);
		
		
		// lower row right side panel
		JPanel lowerRightSplittedPanel = new JPanel();
		lowerSplittedPanel.add(lowerRightSplittedPanel);
		lowerRightSplittedPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		// lower row right side upper panel
		JPanel credentialsMessagePanel = new JPanel();
		lowerRightSplittedPanel.add(credentialsMessagePanel);
		credentialsMessagePanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblMessage = new JLabel("MESSAGE");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		credentialsMessagePanel.add(lblMessage);
		
		// lower row right side lower panel
		JPanel okButtonPanel = new JPanel();
		lowerRightSplittedPanel.add(okButtonPanel);
		okButtonPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnRegister = new JButton("Registrieren");
		okButtonPanel.add(btnRegister);

	}

}

