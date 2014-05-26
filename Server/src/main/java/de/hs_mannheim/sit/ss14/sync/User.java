package de.hs_mannheim.sit.ss14.sync;

import de.hs_mannheim.sit.ss14.socket.Handler;

/**
 * Object to hold the required information for a user authentification.
 *
 * @author Phil-Patrick Kai Kwiotek
 * @author Jochen Schwander
 */
public class User {

	public User(){
		this.failedLoginAttempts = 0;
	}

	private String userName;

	private String salt;

	private String oneTimeCode;

	private int failedLoginAttempts;

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

	public void increaseFailedLoginAttempts() {
		this.failedLoginAttempts++;
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
