package de.hs_mannheim.sit.ss14.gui.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.AbstractDocument;

import de.hs_mannheim.sit.ss14.auxiliaries.DocumentSizeFilter;
import de.hs_mannheim.sit.ss14.gui.models.RegisterModel;

@SuppressWarnings("serial")
public class RegisterTab extends JPanel {


	RegisterTab(RegisterModel registerModel) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JPanel upperPanel = new JPanel();
		upperPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagConstraints gbc_upperPanel = new GridBagConstraints();
		gbc_upperPanel.insets = new Insets(0, 0, 5, 0);
		gbc_upperPanel.fill = GridBagConstraints.BOTH;
		gbc_upperPanel.gridx = 0;
		gbc_upperPanel.gridy = 0;
		add(upperPanel, gbc_upperPanel);
		upperPanel.setLayout(new GridLayout(0, 1, 0, 0));

		JTextArea infoTextarea = registerModel.infoTextarea;
		infoTextarea.setBackground(SystemColor.control);
		infoTextarea.setWrapStyleWord(true);
		infoTextarea.setLineWrap(true);
		infoTextarea.setEditable(false);
		infoTextarea.setText("Register for using the application.");
		upperPanel.add(infoTextarea);

		JPanel middleSplittedPanel = new JPanel();
		;

		GridBagConstraints gbc_middleSplittedPanel = new GridBagConstraints();
		gbc_middleSplittedPanel.insets = new Insets(0, 0, 5, 0);
		gbc_middleSplittedPanel.gridheight = 5;
		gbc_middleSplittedPanel.fill = GridBagConstraints.BOTH;
		gbc_middleSplittedPanel.gridx = 0;
		gbc_middleSplittedPanel.gridy = 1;
		add(middleSplittedPanel, gbc_middleSplittedPanel);

		middleSplittedPanel.setLayout(new GridLayout(1, 2));

		// lower row left side panel
		JPanel lowerLeftSplittedPanel = new JPanel();
		middleSplittedPanel.add(lowerLeftSplittedPanel);
		GridBagLayout gbl_lowerLeftSplittedPanel = new GridBagLayout();
		gbl_lowerLeftSplittedPanel.columnWidths = new int[] { 0, 0 };
		gbl_lowerLeftSplittedPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_lowerLeftSplittedPanel.columnWeights = new double[] { 1.0,
				Double.MIN_VALUE };
		gbl_lowerLeftSplittedPanel.rowWeights = new double[] { 1.0, 1.0, 1.0,
				1.0, 1.0, 1.0, Double.MIN_VALUE };
		lowerLeftSplittedPanel.setLayout(gbl_lowerLeftSplittedPanel);

		JPanel usernameTextfieldPanel = new JPanel();
		usernameTextfieldPanel.setBorder(new TitledBorder(new EmptyBorder(10,
				10, 10, 10), "Username", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GridBagConstraints gbc_usernameTextfieldPanel = new GridBagConstraints();
		gbc_usernameTextfieldPanel.fill = GridBagConstraints.BOTH;
		gbc_usernameTextfieldPanel.gridx = 0;
		gbc_usernameTextfieldPanel.gridy = 0;
		lowerLeftSplittedPanel.add(usernameTextfieldPanel,
				gbc_usernameTextfieldPanel);
		usernameTextfieldPanel.setLayout(new GridLayout(1, 0, 0, 0));

		JTextField usernameTextfield = registerModel.usernameTextfield;
		usernameTextfieldPanel.add(usernameTextfield);
		usernameTextfield.setColumns(10);
		((AbstractDocument) usernameTextfield.getDocument()).setDocumentFilter(new DocumentSizeFilter(100, "[A-Za-z0-9]+"));

		JPanel desktopPasswordTextfieldPanel = new JPanel();
		desktopPasswordTextfieldPanel.setBorder(new TitledBorder(
				new EmptyBorder(10, 10, 0, 10), "Desktoppassword",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_desktopPasswordTextfieldPanel = new GridBagConstraints();
		gbc_desktopPasswordTextfieldPanel.fill = GridBagConstraints.BOTH;
		gbc_desktopPasswordTextfieldPanel.gridx = 0;
		gbc_desktopPasswordTextfieldPanel.gridy = 1;
		lowerLeftSplittedPanel.add(desktopPasswordTextfieldPanel,
				gbc_desktopPasswordTextfieldPanel);
		desktopPasswordTextfieldPanel.setLayout(new GridLayout(1, 0, 0, 0));

		JPasswordField desktopPasswordTextfield = registerModel.desktopPasswordTextfield;
		desktopPasswordTextfield.setToolTipText("");
		desktopPasswordTextfieldPanel.add(desktopPasswordTextfield);
		((AbstractDocument) desktopPasswordTextfield.getDocument())
		.setDocumentFilter(new DocumentSizeFilter(100, "[A-Za-z0-9]+"));

		JPanel repeatDesktopPasswordTextfieldPanel = new JPanel();
		repeatDesktopPasswordTextfieldPanel.setBorder(new TitledBorder(
				new EmptyBorder(0, 10, 10, 10), "Repeat desktoppassword",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_repeatDesktopPasswordTextfieldPanel = new GridBagConstraints();
		gbc_repeatDesktopPasswordTextfieldPanel.fill = GridBagConstraints.BOTH;
		gbc_repeatDesktopPasswordTextfieldPanel.gridx = 0;
		gbc_repeatDesktopPasswordTextfieldPanel.gridy = 2;
		lowerLeftSplittedPanel.add(repeatDesktopPasswordTextfieldPanel,
				gbc_repeatDesktopPasswordTextfieldPanel);
		repeatDesktopPasswordTextfieldPanel
				.setLayout(new GridLayout(1, 0, 0, 0));

		JPasswordField repeatDesktopPasswordTextfield = registerModel.repeatDesktopPasswordTextfield;
		repeatDesktopPasswordTextfieldPanel.add(repeatDesktopPasswordTextfield);
		((AbstractDocument) repeatDesktopPasswordTextfield.getDocument())
		.setDocumentFilter(new DocumentSizeFilter(100, "[A-Za-z0-9]+"));

		JPanel webPasswordTextfieldPanel = new JPanel();
		webPasswordTextfieldPanel.setBorder(new TitledBorder(new EmptyBorder(
				10, 10, 0, 10), "Webpassword", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GridBagConstraints gbc_webPasswordTextfieldPanel = new GridBagConstraints();
		gbc_webPasswordTextfieldPanel.fill = GridBagConstraints.BOTH;
		gbc_webPasswordTextfieldPanel.gridx = 0;
		gbc_webPasswordTextfieldPanel.gridy = 3;
		lowerLeftSplittedPanel.add(webPasswordTextfieldPanel,
				gbc_webPasswordTextfieldPanel);
		webPasswordTextfieldPanel.setLayout(new GridLayout(1, 0, 0, 0));

		JPasswordField webPasswordTextfield = registerModel.webPasswordTextfield;
		webPasswordTextfieldPanel.add(webPasswordTextfield);
		((AbstractDocument) webPasswordTextfield.getDocument())
		.setDocumentFilter(new DocumentSizeFilter(100, "[A-Za-z0-9]+"));

		JPanel repeatWebPasswordTextfieldPanel = new JPanel();
		repeatWebPasswordTextfieldPanel.setBorder(new TitledBorder(
				new EmptyBorder(0, 10, 10, 10), "Repeat webpassword",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_repeatWebPasswordTextfieldPanel = new GridBagConstraints();
		gbc_repeatWebPasswordTextfieldPanel.fill = GridBagConstraints.BOTH;
		gbc_repeatWebPasswordTextfieldPanel.gridx = 0;
		gbc_repeatWebPasswordTextfieldPanel.gridy = 4;
		lowerLeftSplittedPanel.add(repeatWebPasswordTextfieldPanel,
				gbc_repeatWebPasswordTextfieldPanel);
		repeatWebPasswordTextfieldPanel.setLayout(new GridLayout(1, 0, 0, 0));

		JPasswordField repeatWebPasswordTextfield = registerModel.repeatWebPasswordTextfield;
		repeatWebPasswordTextfieldPanel.add(repeatWebPasswordTextfield);
		((AbstractDocument) repeatWebPasswordTextfield.getDocument())
		.setDocumentFilter(new DocumentSizeFilter(100, "[A-Za-z0-9]+"));
		repeatWebPasswordTextfield
				.addActionListener(registerModel.submitRegisterAL);

		// lower row right side panel
		JPanel lowerRightSplittedPanel = new JPanel();
		middleSplittedPanel.add(lowerRightSplittedPanel);
		GridBagLayout gbl_lowerRightSplittedPanel = new GridBagLayout();
		gbl_lowerRightSplittedPanel.columnWidths = new int[] { 0, 0 };
		gbl_lowerRightSplittedPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0,
				0 };
		gbl_lowerRightSplittedPanel.columnWeights = new double[] { 1.0,
				Double.MIN_VALUE };
		gbl_lowerRightSplittedPanel.rowWeights = new double[] { 1.0, 0.0, 1.0,
				0.0, 1.0, 1.0, Double.MIN_VALUE };
		lowerRightSplittedPanel.setLayout(gbl_lowerRightSplittedPanel);

		JPanel usernameMessageTextareaPanel = new JPanel();
		usernameMessageTextareaPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagConstraints gbc_usernameMessageTextareaPanel = new GridBagConstraints();
		gbc_usernameMessageTextareaPanel.fill = GridBagConstraints.BOTH;
		gbc_usernameMessageTextareaPanel.gridx = 0;
		gbc_usernameMessageTextareaPanel.gridy = 0;
		lowerRightSplittedPanel.add(usernameMessageTextareaPanel,
				gbc_usernameMessageTextareaPanel);
		usernameMessageTextareaPanel.setLayout(new GridLayout(1, 0, 0, 0));

		JTextArea usernameMessageTextarea = registerModel.usernameMessageTextarea;
		usernameMessageTextarea.setLineWrap(true);
		usernameMessageTextarea.setWrapStyleWord(true);
		usernameMessageTextarea.setEditable(false);
		usernameMessageTextarea.setBackground(SystemColor.control);
		usernameMessageTextareaPanel.add(usernameMessageTextarea);

		JPanel desktopPasswordsMessageTextareaPanel = new JPanel();
		desktopPasswordsMessageTextareaPanel.setBorder(new EmptyBorder(0, 10,
				10, 10));
		GridBagConstraints gbc_desktopPasswordsMessageTextareaPanel = new GridBagConstraints();
		gbc_desktopPasswordsMessageTextareaPanel.gridheight = 2;
		gbc_desktopPasswordsMessageTextareaPanel.ipady = 30;
		gbc_desktopPasswordsMessageTextareaPanel.fill = GridBagConstraints.BOTH;
		gbc_desktopPasswordsMessageTextareaPanel.gridx = 0;
		gbc_desktopPasswordsMessageTextareaPanel.gridy = 1;
		lowerRightSplittedPanel.add(desktopPasswordsMessageTextareaPanel,
				gbc_desktopPasswordsMessageTextareaPanel);
		desktopPasswordsMessageTextareaPanel.setLayout(new GridLayout(1, 0, 0,
				0));

		JTextArea desktopPasswordsMessageTextarea = registerModel.desktopPasswordsMessageTextarea;
		desktopPasswordsMessageTextarea.setLineWrap(true);
		desktopPasswordsMessageTextarea.setWrapStyleWord(true);
		desktopPasswordsMessageTextarea.setBackground(SystemColor.control);
		desktopPasswordsMessageTextarea.setEditable(false);
		desktopPasswordsMessageTextareaPanel
				.add(desktopPasswordsMessageTextarea);

		JPanel webPasswordsMessageTextareaPanel = new JPanel();
		webPasswordsMessageTextareaPanel
				.setBorder(new EmptyBorder(0, 10, 0, 10));
		GridBagConstraints gbc_webPasswordsMessageTextareaPanel = new GridBagConstraints();
		gbc_webPasswordsMessageTextareaPanel.gridheight = 3;
		gbc_webPasswordsMessageTextareaPanel.fill = GridBagConstraints.BOTH;
		gbc_webPasswordsMessageTextareaPanel.gridx = 0;
		gbc_webPasswordsMessageTextareaPanel.gridy = 3;
		lowerRightSplittedPanel.add(webPasswordsMessageTextareaPanel,
				gbc_webPasswordsMessageTextareaPanel);
		webPasswordsMessageTextareaPanel.setLayout(new GridLayout(1, 0, 0, 0));

		JTextArea webPasswordsMessageTextarea = registerModel.webPasswordsMessageTextarea;
		webPasswordsMessageTextarea.setLineWrap(true);
		webPasswordsMessageTextarea.setWrapStyleWord(true);
		webPasswordsMessageTextarea.setBackground(SystemColor.control);
		webPasswordsMessageTextarea.setEditable(false);
		webPasswordsMessageTextareaPanel.add(webPasswordsMessageTextarea);

		JPanel lowerPanel = new JPanel();
		GridBagConstraints gbc_lowerPanel = new GridBagConstraints();
		gbc_lowerPanel.fill = GridBagConstraints.BOTH;
		gbc_lowerPanel.gridx = 0;
		gbc_lowerPanel.gridy = 6;
		add(lowerPanel, gbc_lowerPanel);
		lowerPanel.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel emptyPanel = new JPanel();
		lowerPanel.add(emptyPanel);
		emptyPanel.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel buttonsPanel = new JPanel();
		lowerPanel.add(buttonsPanel);
		buttonsPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
		buttonsPanel.setLayout(new GridLayout(0, 1, 0, 0));


		JButton btnRegister = new JButton("Register");
		buttonsPanel.add(btnRegister);
		btnRegister.addActionListener(registerModel.submitRegisterAL);

	}

}
