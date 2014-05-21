package de.hs_mannheim.sit.ss14;

import javax.swing.*;

@SuppressWarnings("serial")
public class ViewStart extends JFrame {

	public static void main(String args[]) {
		new ViewStart();
	}

	ViewStart() {
		JLabel jlbHelloWorld = new JLabel("Hello World");
		add(jlbHelloWorld);
		this.setSize(100, 100);
		// pack();
		setVisible(true);
	}
}