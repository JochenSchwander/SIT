package de.hs_mannheim.sit.ss14.gui.controllers;

import java.io.IOException;

import de.hs_mannheim.sit.ss14.auxiliaries.ClientSocket;
import de.hs_mannheim.sit.ss14.gui.models.OtpModel;

public class OtpController {
	private ClientSocket socket;
	private GuiController guiController;

	OtpController(GuiController guiController, OtpModel otpModel) {
		this.socket = guiController.socket;

	}

	/**
	 * request the onetimepassword and salt and shows it to the user
	 * 
	 * @param loginModel
	 * @throws IOException
	 */
	private void requestOtp() throws IOException, Exception {
		socket.sendMessage("requestotp");

		if (socket.recieveMessage().equals("requestotp")) {
			String recievedMessage = socket.recieveMessage();
			String[] recievedMessageArray = recievedMessage.split(";");

			// Ausgabe des empfangenen OTPs

			loginModel.infoTextarea.setText("Your OTP:\n"
					+ recievedMessageArray[0] + "\nand Salt:\n"
					+ recievedMessageArray[1]);

			guiController.startView.loginTab.displayOtp(loginModel);

		} else {
			throw new Exception("Communication with server failed.");
		}

	}
}
