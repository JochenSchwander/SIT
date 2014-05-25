package de.hs_mannheim.sit.ss14.otpgenerator;

/**
 * interface for the One Time Password Generator.
 * @author marcelmath
 *
 */
public interface OneTimePasswordGenerator {
	
	/**
	 * generates a random String which will be used as the One Time Password.
	 * @return the OneTimePassword
	 */
	String generateOneTimePassword();
}