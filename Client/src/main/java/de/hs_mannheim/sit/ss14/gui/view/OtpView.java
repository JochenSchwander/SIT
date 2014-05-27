package de.hs_mannheim.sit.ss14.gui.view;

import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import de.hs_mannheim.sit.ss14.gui.models.OtpModel;

@SuppressWarnings("serial")
public class OtpView extends JPanel {

	OtpView(OtpModel otpModel) {
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
		middlePanel.setLayout(new GridLayout(0, 3, 0, 0));

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

		JPanel middleMiddlePanel = new JPanel();
		middleMiddlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		middlePanel.add(middleMiddlePanel);
		middleMiddlePanel.setLayout(new GridLayout(2, 0, 0, 0));

		final JTextField otpTextField = otpModel.otpTextField;
		middleMiddlePanel.add(otpTextField);
		otpTextField.setColumns(10);

		final JTextField saltTextField = otpModel.saltTextField;
		middleMiddlePanel.add(saltTextField);

		JPanel rightMiddlePanel = new JPanel();
		rightMiddlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		middlePanel.add(rightMiddlePanel);
		rightMiddlePanel.setLayout(new GridLayout(2, 0, 0, 0));

		JButton copyOtpToClipboard = new JButton("copy to clipboard");
		rightMiddlePanel.add(copyOtpToClipboard);
		copyOtpToClipboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				otpTextField.selectAll();
				otpTextField.copy();
			}
		});

		JButton copySaltToClipboard = new JButton("copy to clipboard");
		rightMiddlePanel.add(copySaltToClipboard);
		saltTextField.setColumns(10);
		copySaltToClipboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saltTextField.selectAll();
				saltTextField.copy();
			}
		});

		JPanel lowerPanel = new JPanel();
		add(lowerPanel);
		lowerPanel.setLayout(new GridLayout(3, 3, 0, 0));

		for (int i = 0; i < 8; i++) {
			JPanel panel = new JPanel();
			lowerPanel.add(panel);
		}

		JButton resetButton = new JButton("Reset");
		lowerPanel.add(resetButton);
		resetButton.addActionListener(otpModel.resetAL);
	}

}
