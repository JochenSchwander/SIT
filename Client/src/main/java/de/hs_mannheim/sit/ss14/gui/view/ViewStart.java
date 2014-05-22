package de.hs_mannheim.sit.ss14.gui.view;

import javax.swing.*;

import de.hs_mannheim.sit.ss14.gui.models.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ViewStart extends JFrame {

	public static void main(String args[]) {
		new ViewStart(new LoginModel(),new RegisterModel());
	}

	/**
	 * For the basic layout of the desktop client
	 */
	public ViewStart(LoginModel loginModel,RegisterModel registerModel) {
		setSize(400, 300);
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

		tabbedPane.addTab("Login", null, new LoginTab(loginModel),
				"Loggen Sie sich ein.");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		tabbedPane.addTab("Register", null, new RegisterTab(registerModel),
				"Registrieren Sie sich für die Nutzung des Programms.");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		tabbedPane.addTab("Info", null, new InfoTab(),
				"Infos über das Programm und das Projekt.");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		
		getContentPane().add(tabbedPane);

		// pack();
		setVisible(true);
	}

}