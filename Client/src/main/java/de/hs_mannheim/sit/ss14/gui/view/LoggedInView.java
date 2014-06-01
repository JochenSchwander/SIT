package de.hs_mannheim.sit.ss14.gui.view;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.hs_mannheim.sit.ss14.gui.models.LoggedInModel;

import java.awt.GridLayout;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class LoggedInView extends JPanel {

	LoggedInView(LoggedInModel loggedInModel) {
		setLayout(new GridLayout(4, 0, 0, 0));

		JPanel welcomePanel = new JPanel();
		welcomePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(welcomePanel);
		welcomePanel.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel welcomeLabel = new JLabel("Welcome to the SIT Application");
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		welcomePanel.add(welcomeLabel);

		JPanel inputPanel = new JPanel();
		inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(inputPanel);
		inputPanel.setLayout(new GridLayout(1, 0, 0, 0));

		JTextArea recieveTextArea = loggedInModel.recieveTextArea;
		inputPanel.add(recieveTextArea);

		JPanel outputPanel = new JPanel();
		outputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(outputPanel);
		outputPanel.setLayout(new GridLayout(1, 0, 0, 0));

		JTextField toSendTextField = loggedInModel.toSendTextField;
		outputPanel.add(toSendTextField);
		toSendTextField.setColumns(10);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(buttonPanel);
		buttonPanel.setLayout(new GridLayout(2, 3, 0, 0));

		for (int i = 0; i < 8; i++) {
			JPanel panel = new JPanel();
			buttonPanel.add(panel);
		}

		JButton logoutButton = new JButton("Logout");
		buttonPanel.add(logoutButton);

		JButton resetButton = new JButton("Send");
		buttonPanel.add(resetButton);
		resetButton.addActionListener(loggedInModel.sendAL);

	}

}
