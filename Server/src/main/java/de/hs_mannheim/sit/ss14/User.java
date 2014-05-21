package de.hs_mannheim.sit.ss14;


/**
 * Object to hold the required information for a user authentification.
 *
 * @author Jochen Schwander
 */
public class User {

	private String userName;

	private String desktopPassword;

	private String webPassword;

	private String salt;

	private String oneTimeCode;


	/**
	 * Tests if the given desktop password is valid for this user.
	 *
	 * @param desktopPassword the desktop password to check
	 * @return true, if the password is valid, false, if the password is invalid
	 */
	public boolean checkDesktopPassword(final String desktopPassword) {
		return desktopPassword.equals(this.desktopPassword);
	}

	/**
	 * Tests if the given web password is valid for this user.
	 *
	 * @param webPassword the web password to check
	 * @return true, if the password is valid, false, if the password is invalid
	 */
	public boolean checkWebPassword(final String webPassword) {
		return webPassword.equals(this.webPassword);
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getDesktopPassword() {
		return desktopPassword;
	}

	public void setDesktopPassword(final String desktopPassword) {
		this.desktopPassword = desktopPassword;
	}

	public String getWebPassword() {
		return webPassword;
	}

	public void setWebPassword(final String webPassword) {
		this.webPassword = webPassword;
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

	public void setOneTimeCode(final String oneTimeCode) {
		this.oneTimeCode = oneTimeCode;
	}

}
