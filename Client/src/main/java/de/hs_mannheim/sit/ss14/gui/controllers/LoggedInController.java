package de.hs_mannheim.sit.ss14.gui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.hs_mannheim.sit.ss14.gui.models.LoggedInModel;

/**
 * This controller controls the actions of the view when the user has
 * successfully logged in.
 *
 * Functionality of the program is to send and receive messages to the server, or in the future every other functionality.
 * @author DS
 *
 */
public class LoggedInController {

	private GuiController guiController;
	private LoggedInModel loggedInModel;

	public LoggedInController(GuiController guiController,
			LoggedInModel loggedInModel) {
		this.guiController = guiController;
		this.loggedInModel = loggedInModel;

		initModel();
		waitForServerResponse();

	}

	private void initModel() {
		loggedInModel.recieveTextArea = new JTextArea();
		loggedInModel.toSendTextField = new JTextField();

		//send message to server
		loggedInModel.sendAL = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					guiController.socket.sendMessage("echo\n"
							+ loggedInModel.toSendTextField.getText());
					waitForServerResponse();
				} catch (IOException e1) {
					loggedInModel.recieveTextArea
							.setText("The connection to the server was lost.");
				}
			}
		};

	}

	/**
	 * in this function a new thread is started that waits for a response from the server so that the view is not blocked while waiting.
	 * The answer is written to the recieveTextArea;
	 */
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