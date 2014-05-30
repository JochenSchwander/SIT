package de.hs_mannheim.sit.ss14.gui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.hs_mannheim.sit.ss14.gui.models.LoggedInModel;

public class LoggedInController {

	private StartDesktopClient guiController;
	private LoggedInModel loggedInModel;

	public LoggedInController(StartDesktopClient guiController,
			LoggedInModel loggedInModel) {
		this.guiController = guiController;
		this.loggedInModel = loggedInModel;

		initModel();
		waitForServerResponse();

	}

	private void initModel() {
		loggedInModel.recieveTextArea = new JTextArea();
		loggedInModel.toSendTextField = new JTextField();
		loggedInModel.sendAL = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					guiController.socket
							.sendMessage("echo\n"+loggedInModel.toSendTextField
									.getText());
				} catch (IOException e1) {
					loggedInModel.recieveTextArea
							.setText("The connection to the server was lost.");
				}
			}
		};
		loggedInModel.logoutAL = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					guiController.socket
					.sendMessage("logout");
				} catch (IOException e1) {
				}
				guiController.startView();
			}
		};

	}

	private void waitForServerResponse() {
		(new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String recievedMessage = guiController.socket
							.recieveMessage();
					if (recievedMessage.equals("echo")) {
						loggedInModel.recieveTextArea
								.setText(guiController.socket.recieveMessage());

					} else {
						loggedInModel.recieveTextArea
								.setText("Communication with server failed.");
					}
				} catch (IOException e) {
					loggedInModel.recieveTextArea
							.setText("The connection to the server could not be established.");
				}
			}
		})).start();
	}
}