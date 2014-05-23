package de.hs_mannheim.sit.ss14.gui.view;

import javax.swing.*;

import de.hs_mannheim.sit.ss14.gui.controllers.LoginController;
import de.hs_mannheim.sit.ss14.gui.controllers.RegisterController;
import de.hs_mannheim.sit.ss14.gui.models.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class StartView extends JFrame {


	/**
	 * For the basic layout of the desktop client
	 * @param registerController 
	 * @param loginController 
	 */
	public StartView(LoginModel loginModel,LoginController loginController, RegisterModel registerModel, RegisterController registerController) {
		setSize(450, 325);
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

		tabbedPane.addTab("Login", null, new LoginTab(loginModel, loginController),
				"Loggen Sie sich ein.");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		tabbedPane.addTab("Register", null, new RegisterTab(registerModel, registerController),
				"Registrieren Sie sich für die Nutzung des Programms.");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		tabbedPane.addTab("Info", null, new InfoTab(),
				"Infos über das Programm und das Projekt.");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		
		getContentPane().add(tabbedPane);

		// pack();
		setVisible(true);
	}
	
	public void setLoggedInView(){
		getContentPane().setLayout(new GridLayout(1, 1));
		
		JPanel usernamePanel = new JPanel();
		usernamePanel.setLayout(new GridLayout(1, 0, 0, 0));


		JTextField usernameTextfield =	new JTextField(10);
		usernamePanel.add(usernameTextfield);
		
		getContentPane().add(usernamePanel);
	}

}