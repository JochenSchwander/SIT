package de.hs_mannheim.sit.ss14;

/**
 * Interface for exchangeable one time password generators. 
 * 
 * @author Jochen Schwander
 */
public interface OneTimePasswordGenerator {

	/**
	 * Generates a one time password.
	 * 
	 * @return one time password
	 */
	String generateOneTimePassword();
}
