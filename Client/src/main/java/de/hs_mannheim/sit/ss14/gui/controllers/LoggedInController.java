package de.hs_mannheim.sit.ss14.gui.controllers;


import javax.swing.JTextArea;


import de.hs_mannheim.sit.ss14.gui.models.LoggedInModel;

public class LoggedInController {

	private GuiController guiController;
	private LoggedInModel loggedInModel;

	public LoggedInController(GuiController guiController,
			LoggedInModel loggedInModel) {
		this.guiController = guiController;
		this.loggedInModel = loggedInModel;

		initModel();
	}

	private void initModel() {
		loggedInModel.infoTextarea = new JTextArea();
	}
}
