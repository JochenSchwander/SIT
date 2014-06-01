package de.hs_mannheim.sit.ss14.gui.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import de.hs_mannheim.sit.ss14.gui.models.LoggedInModel;
import de.hs_mannheim.sit.ss14.gui.models.LoginModel;
import de.hs_mannheim.sit.ss14.gui.models.OtpModel;
import de.hs_mannheim.sit.ss14.gui.models.RegisterModel;

/**
 * This class acts as a frame for all the other views that are displayed in this
 * window.
 * 
 * @author DS
 * 
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = -1317809154386180027L;
	public LoginTab loginTab;
	public RegisterTab registerTab;
	public InfoTab infoTab;

	/**
	 * Starts the main window and sets the basic layout of the desktop client
	 */
	public void startView(LoginModel loginModel, RegisterModel registerModel) {
		// windows
		setMinimumSize(new Dimension(647, 400));
		// if mac
		String osName = System.getProperty("os.name").toLowerCase();
		boolean isMacOs = osName.startsWith("mac os x");
		if (isMacOs) {
			setMinimumSize(new Dimension(800, 600));
		}
		setTitle("SIT Projekt");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getContentPane().setLayout(new GridLayout(1, 1));

		JTabbedPane tabbedPane = new JTabbedPane();

		loginTab = new LoginTab(loginModel);
		tabbedPane.addTab("Login", null, loginTab, "Log into the application");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		registerTab = new RegisterTab(registerModel);
		tabbedPane.addTab("Register", null, registerTab,
				"Register for using the application");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		infoTab = new InfoTab();
		tabbedPane.addTab("Info", null, infoTab, "Informations about the team");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		getContentPane().add(tabbedPane);

		// pack();
		setVisible(true);
	}

	/**
	 * as part of the login process the one-time password has to be displayed
	 * 
	 * @param otpModel
	 */
	public void otpView(OtpModel otpModel) {
		getContentPane().removeAll();
		getContentPane().setLayout(new GridLayout(1, 1));
		add(new OtpView(otpModel));
		setVisible(true);
	}

	/**
	 * replaces the loginTab with the loggedInView after the user successfully
	 * authenticated himself
	 * 
	 * @param loggedInModel
	 */
	public void loggedInView(LoggedInModel loggedInModel) {
		getContentPane().removeAll();
		getContentPane().setLayout(new GridLayout(1, 1));
		add(new LoggedInView(loggedInModel));
		setVisible(true);
	}

}