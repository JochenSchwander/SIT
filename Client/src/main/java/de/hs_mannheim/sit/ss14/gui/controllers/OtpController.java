package de.hs_mannheim.sit.ss14.gui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import de.hs_mannheim.sit.ss14.auxiliaries.ClientSocket;
import de.hs_mannheim.sit.ss14.gui.models.OtpModel;

public class OtpController {
	private GuiController guiController;
	private OtpModel otpModel;

	OtpController(GuiController guiController, OtpModel otpModel) {
		this.guiController = guiController;
		this.otpModel = otpModel;

		initModel();
		requestOtp();
	}

	private void initModel() {
		otpModel.otpTextField = new JTextField();
		otpModel.saltTextField = new JTextField();
		otpModel.infoTextarea = new JTextArea();
		otpModel.resetAL = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiController.startView();
			}
		};

		otpModel.infoTextarea
				.setText("You will see your one-time password and the salt which you are required to enter on the website.\nThen you will be automatically granted access to the application.");

	}

	/**
	 * request the one-timepassword and salt and shows it to the user
	 * 
	 */
	private void requestOtp() {
		try {
			guiController.socket.sendMessage("requestotp");

			String recievedMessage = guiController.socket.recieveMessage();
			if (recievedMessage.equals("requestotp")) {
				recievedMessage = guiController.socket.recieveMessage();
				String[] recievedMessageArray = recievedMessage.split(";");

				// Ausgabe des empfangenen OTPs

				otpModel.otpTextField.setText(recievedMessageArray[0]);
				otpModel.saltTextField.setText(recievedMessageArray[1]);

				waitForServerResponse();

			} else {
				throw new Exception("Communication with server failed.");
			}
		} catch (IOException e) {
			otpModel.infoTextarea
					.setText("The connection to the server could not be established.");
		} catch (Exception e) {
			otpModel.infoTextarea.setText("We are sorry, an error occured.");
			e.printStackTrace();
		}
	}

	private void waitForServerResponse() {
		(new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String recievedMessage = guiController.socket
							.recieveMessage();
					if (recievedMessage.equals("weblogin")) {
						recievedMessage = guiController.socket.recieveMessage();
						String[] recievedMessageArray = recievedMessage
								.split(";");

						if (recievedMessageArray[0].equals("success")) {

							guiController
									.displayLoggedInView(recievedMessageArray[1]);

						} else { // if failed
							otpModel.infoTextarea
									.setText(recievedMessageArray[1]);
						}

					} else {
						throw new Exception("Communication with server failed.");
					}
				} catch (IOException e) {
					otpModel.infoTextarea
							.setText("The connection to the server could not be established.");
				} catch (Exception e) {
					otpModel.infoTextarea
							.setText("We are sorry, an error occured.");
					e.printStackTrace();
				}
			}
		})).start();
	}
}
