package de.hs_mannheim.sit.ss14.gui.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import de.hs_mannheim.sit.ss14.gui.controllers.RegisterController;
import de.hs_mannheim.sit.ss14.gui.models.RegisterModel;

public class InfoTab extends JPanel {

	private static final long serialVersionUID = 1L;

	InfoTab() {
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridLayout(0, 1, 0, 0));
		
		JTextArea infoTextarea = new JTextArea();
		add(infoTextarea);
		infoTextarea.setBackground(SystemColor.control);
		infoTextarea.setLineWrap(true);
		infoTextarea.setWrapStyleWord(true);
		infoTextarea.setEditable(false);
		infoTextarea.setText("SIT Projekt\n\nTeammitglieder:\nKwiotek\nMath\nSchwander\nSeemann");;

				


	}

}
