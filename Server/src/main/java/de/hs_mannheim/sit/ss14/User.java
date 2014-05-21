package de.hs_mannheim.sit.ss14;

import java.math.BigInteger;


/**
 * Object to hold the required information for a user authentification.
 *
 * @author Phil-Patrick Kai Kwiotek
 */
public class User {

	private String userName;

	private String salt;

	private String sessionKey;

	private String oneTimeCode;

	private int failedLoginAttempts;

	private BigInteger encryptionKey;

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

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public int getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	public void setFailedLoginAttempts(int failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}

	public void setOneTimeCode(final String oneTimeCode) {
		this.oneTimeCode = oneTimeCode;
	}

	public BigInteger getEncryptionKey() {
		return encryptionKey;
	}

	public void setEncryptionKey(BigInteger encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

}
