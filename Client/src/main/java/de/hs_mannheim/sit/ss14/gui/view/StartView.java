package de.hs_mannheim.sit.ss14.gui.view;

import javax.swing.*;

import de.hs_mannheim.sit.ss14.gui.models.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class StartView extends JFrame {

	private static final long serialVersionUID = -1317809154386180027L;
	public LoginTab loginTab;
	public RegisterTab registerTab;
	public InfoTab infoTab;

	/**
	 * For the basic layout of the desktop client
	 * 
	 * @param registerController
	 * @param loginController
	 */
	public StartView(LoginModel loginModel,	RegisterModel registerModel) {
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
		tabbedPane.addTab("Login", null, loginTab, "Loggen Sie sich ein.");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		registerTab = new RegisterTab(registerModel);
		tabbedPane.addTab("Register", null, registerTab,
				"Registrieren Sie sich f�r die Nutzung des Programms.");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		infoTab = new InfoTab();
		tabbedPane.addTab("Info", null, infoTab,
				"Infos �ber das Programm und das Projekt.");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		getContentPane().add(tabbedPane);

		// pack();
		setVisible(true);
	}

	public void loggedInView() {
		getContentPane().removeAll();
		getContentPane().setLayout(new GridLayout(1, 1));

		JPanel usernamePanel = new JPanel();
		usernamePanel.setLayout(new GridLayout(1, 0, 0, 0));
		usernamePanel.setBackground(Color.green);

		getContentPane().add(usernamePanel);
		setVisible(true);
	}
	
	public void otpView() {
		getContentPane().removeAll();
		getContentPane().setLayout(new GridLayout(1, 1));

		JPanel usernamePanel = new JPanel();
		usernamePanel.setLayout(new GridLayout(1, 0, 0, 0));
		usernamePanel.setBackground(Color.green);

		getContentPane().add(usernamePanel);
		setVisible(true);
	}

}