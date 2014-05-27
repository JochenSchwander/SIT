package de.hs_mannheim.sit.ss14.gui.view;

import javax.swing.*;

import de.hs_mannheim.sit.ss14.gui.models.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {
	public MainWindow() {
	}

	private static final long serialVersionUID = -1317809154386180027L;
	public LoginTab loginTab;
	public RegisterTab registerTab;
	public InfoTab infoTab;

	/**
	 * Starts the main window and sets the basic layout of the desktop client
	 */
	public void startView(LoginModel loginModel, RegisterModel registerModel) {
		setSize(647, 400);
		setTitle("SIT Projekt");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getContentPane().setLayout(new GridLayout(1, 1));

		JTabbedPane tabbedPane = new JTabbedPane();

		loginTab = new LoginTab(loginModel);
		tabbedPane.addTab("Login", null, loginTab, "Log into the application");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		registerTab = new RegisterTab(registerModel);
		tabbedPane.addTab("Register", null, registerTab, "Register for using the application");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		infoTab = new InfoTab();
		tabbedPane.addTab("Info", null, infoTab, "Informations about the team");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		getContentPane().add(tabbedPane);

		// pack();
		setVisible(true);
	}

	public void loggedInView(LoggedInModel loggedInModel) {
		getContentPane().removeAll();
		getContentPane().setLayout(new GridLayout(2, 1));

		JPanel greenPanel = new JPanel();
		greenPanel.setLayout(new GridLayout(1, 0, 0, 0));
		greenPanel.setBackground(Color.green);
		getContentPane().add(greenPanel);
		
		JPanel messagePanel = new JPanel();
		greenPanel.setLayout(new GridLayout(1, 0, 0, 0));
		getContentPane().add(messagePanel);
		
		JTextArea infoTextarea=loggedInModel.infoTextarea;
		messagePanel.add(infoTextarea);
		
		
		setVisible(true);
	}

	
	public void otpView(OtpModel otpModel) {
		getContentPane().removeAll();
		getContentPane().setLayout(new GridLayout(1, 1));
		add(new OtpView(otpModel));
		setVisible(true);
	}

}