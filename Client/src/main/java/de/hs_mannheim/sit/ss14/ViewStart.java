package de.hs_mannheim.sit.ss14;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class ViewStart extends JFrame {

	public static void main(String args[]) {
		new ViewStart();
	}

	/**
	 * For the basic layout of the desktop client
	 */
	ViewStart() {
		setSize(400, 400);
		setTitle("SIT Projekt");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		getContentPane().setLayout(new GridLayout(1, 1));
		getContentPane().add(createTabbedPane());

		// JLabel lblTitle = new JLabel("SIT Hauptprogramm");
		// lblTitle.setFont(new Font("Arial", Font.PLAIN, 26));
		// lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		// getContentPane().add(lblTitle, BorderLayout.NORTH);
		//
		// JPanel centerPanel = new JPanel();
		// getContentPane().add(centerPanel, BorderLayout.CENTER);
		// centerPanel.setLayout(new CardLayout(0, 0));

		//pack();
		setVisible(true);
	}

	private JTabbedPane createTabbedPane(){
		JTabbedPane tabbedPane = new JTabbedPane();
		
		tabbedPane.addTab("Login", null, createPanel1(), "Loggen Sie sich ein.");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		
		tabbedPane.addTab("Register", null, createPanel2(),
				"Registrieren Sie sich für die Nutzung des Programms.");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		
		tabbedPane.addTab("Info", null, createPanel3(), "Infos über das Programm und das Projekt.");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		
		return tabbedPane;
	}
	
	private Component createPanel1() {
		JComponent panel = makeTextPanel("Panel #1");
		return panel;
	}
	
	private Component createPanel2() {
		JComponent panel = makeTextPanel("Panel #2");
		return panel;
	}

	private Component createPanel3() {
		JComponent panel = makeTextPanel("Panel #3");
		return panel;
	}


	protected JComponent makeTextPanel(String text) {
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(filler);
		return panel;
	}
	
	
}