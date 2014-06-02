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
	public MainWindow mainWindow;
	public ClientSocket socket;
	

	public GuiController() {
		startMainWindow();
	}

	/**
	 * creates the window for the first time
	 */
	private void startMainWindow() {
		mainWindow = new MainWindow();
		
		LoginModel loginModel = new LoginModel();
		new LoginController(this, loginModel);

		RegisterModel registerModel = new RegisterModel();
		new RegisterController(registerModel);
		
		mainWindow.startView(loginModel, registerModel);
	}

	/**
	 * basically the same as startMainWindow but the old window is resettet to start.
	 */
	public void startView() {
		
		LoginModel loginModel = new LoginModel();
		new LoginController(this, loginModel);

		RegisterModel registerModel = new RegisterModel();
		new RegisterController(registerModel);
		
		mainWindow.startView(loginModel, registerModel);
	}
	
	/**
	 * initializes the view where the one-time password is shown
	 */
	public void displayOtpView() {
		OtpModel otpModel = new OtpModel();
		new OtpController(this, otpModel);
		
		mainWindow.otpView(otpModel);
		
	}
	
	/**
	 * initializes the view where the user is logged in, an can use the functionality of the program
	 */
	public void displayLoggedInView(String recievedMessage) {
		LoggedInModel loggedInModel = new LoggedInModel();
		new LoggedInController(this, loggedInModel);
		
		loggedInModel.recieveTextArea.setText(recievedMessage);
		mainWindow.loggedInView(loggedInModel);
		
	}
}
