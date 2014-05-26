package de.hs_mannheim.sit.ss14.gui.view;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import de.hs_mannheim.sit.ss14.gui.models.OtpModel;


public class OtpView extends JPanel{
	
	OtpView(OtpModel otpModel){
		setLayout(new GridLayout(3, 1));
		
		JPanel upperPanel = new JPanel();
		upperPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(upperPanel);
		upperPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JTextArea infoTextarea = otpModel.infoTextarea;
		infoTextarea.setBackground(SystemColor.control);
		upperPanel.add(infoTextarea);
		infoTextarea.setWrapStyleWord(true);
		infoTextarea.setLineWrap(true);
		infoTextarea.setEditable(false);
		
		JPanel middlePanel = new JPanel();
		middlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(middlePanel);
		middlePanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel leftMiddlePanel = new JPanel();
		leftMiddlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		middlePanel.add(leftMiddlePanel);
		leftMiddlePanel.setLayout(new GridLayout(2, 0, 0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("Your one-time password:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		leftMiddlePanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("Your salt:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		leftMiddlePanel.add(lblNewLabel);
		
		JPanel rightMiddlePanel = new JPanel();
		rightMiddlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		middlePanel.add(rightMiddlePanel);
		rightMiddlePanel.setLayout(new GridLayout(2, 0, 0, 0));
		
		JTextField otpTextField = otpModel.otpTextField;
		rightMiddlePanel.add(otpTextField);
		otpTextField.setColumns(10);
		
		JTextField saltTextField = otpModel.saltTextField;
		rightMiddlePanel.add(saltTextField);
		saltTextField.setColumns(10);
		
		JPanel lowerPanel = new JPanel();
		add(lowerPanel);
		lowerPanel.setLayout(new GridLayout(3, 3, 0, 0));
		
	}

}
