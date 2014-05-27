package de.hs_mannheim.sit.ss14.gui.view;

import javax.swing.JPanel;

import de.hs_mannheim.sit.ss14.gui.models.LoggedInModel;
import de.hs_mannheim.sit.ss14.gui.models.LoginModel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

public class LoggedInView extends JPanel{
	
	LoggedInView(LoggedInModel loggedInModel){
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
		
		JPanel outputPanel = new JPanel();
		outputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(outputPanel);
		outputPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(buttonPanel);
		buttonPanel.setLayout(new GridLayout(2, 3, 0, 0));
		
		
	}


}
