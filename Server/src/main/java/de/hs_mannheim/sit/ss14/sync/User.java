package de.hs_mannheim.sit.ss14.sync;

import de.hs_mannheim.sit.ss14.socket.Handler;

/**
 * Object to hold the required information for a user authentification.
 *
 * @author Phil-Patrick Kai Kwiotek
 * @author Jochen Schwander
 */
public class User {

	private String userName;

	private String salt;

	private String oneTimeCode;

	private int failedLoginAttempts; //initial == 0

	private Handler handler;

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(final String salt) {
		this.salt = salt;
	}

	public String getOneTimeCode() {
		return oneTimeCode;
	}

	public int getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	public void setFailedLoginAttempts(final int failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}

	public void setOneTimeCode(final String oneTimeCode) {
		this.oneTimeCode = oneTimeCode;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(final Handler handler) {
		this.handler = handler;
	}

}
