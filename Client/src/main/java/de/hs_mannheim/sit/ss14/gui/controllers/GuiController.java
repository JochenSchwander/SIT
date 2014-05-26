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
	

	GuiController() {
		startView();
	}

	public void startView() {
		mainWindow = new MainWindow();
		
		LoginModel loginModel = new LoginModel();
		LoginController loginController = new LoginController(this, loginModel);

		RegisterModel registerModel = new RegisterModel();
		RegisterController registerController = new RegisterController(registerModel);
		
		mainWindow.startView(loginModel, registerModel);
	}

	public void displayOtpView() {
		OtpModel otpModel = new OtpModel();
		OtpController otpController = new OtpController(this, otpModel);
		
		mainWindow.otpView(otpModel);
		
	}
	
	public void displayLoggedInView(String recievedMessage) {
		LoggedInModel loggedInModel = new LoggedInModel();
		LoggedInController loginController = new LoggedInController(this, loggedInModel);
		
		loggedInModel.infoTextarea.setText(recievedMessage);
		mainWindow.loggedInView(loggedInModel);
		
	}

	public static void main(String args[]) {
		new GuiController();
	}
}
