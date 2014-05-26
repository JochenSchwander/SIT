package de.hs_mannheim.sit.ss14.gui.controllers;

import de.hs_mannheim.sit.ss14.auxiliaries.ClientSocket;
import de.hs_mannheim.sit.ss14.gui.models.*;
import de.hs_mannheim.sit.ss14.gui.view.*;

/**
 * Hier wird die View gestarted und die Models und Controller erstellt.
 * 
 * @author DS
 * 
 */
public class GuiController {
	public StartView startView;
	public ClientSocket socket;
	

	GuiController() {
		startView();
	}

	public void startView() {
		LoginModel loginModel = new LoginModel();
		LoginController loginController = new LoginController(this, loginModel);

		RegisterModel registerModel = new RegisterModel();
		RegisterController registerController = new RegisterController(registerModel);
		
		startView = new StartView(loginModel, registerModel);
	}

	public static void main(String args[]) {
		new GuiController();
	}
}
