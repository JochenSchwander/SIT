package de.hs_mannheim.sit.ss14;

import java.util.Date;

/**
 * Object to hold the required information for a user authentification.
 * 
 * @author Jochen Schwander
 */
public class User {
	
	private String userName;
	
	private String email;
	
	private String password;

	private String salt;
	
	private String oneTimePassword;
	
	private Date oneTimePasswordExpirationDate;
	
	/**
	 * Checks that the oneTimePassword is valid and not expired.
	 * 
	 * @param oneTimePassword the one time password to check;
	 * @return true, if valid and not expired
	 */
	boolean isValidOneTimePassword(final String oneTimePassword) {
		//TODO
		return false;
	}
	
	/**
	 * Checks that the given password equals the saved one.
	 * 
	 * @param password password to check
 	 * @return true, if the passwrods are equal, false otherwise
	 */
	boolean isValidPassword(final String password) {
		//TODO
		return false;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setSalt(final String salt) {
		this.salt = salt;
	}
	
	public String getOneTimePassword() {
		return oneTimePassword;
	}
	
	public void setOneTimePassword(final String oneTimePassword) {
		this.oneTimePassword = oneTimePassword;
	}

	public void setOneTimePasswordExpirationDate(final Date oneTimePasswordExpirationDate) {
		this.oneTimePasswordExpirationDate = oneTimePasswordExpirationDate;
	}
	
}
