package de.hs_mannheim.sit.ss14.randomgenerator;

/**
 * interface for the One Time Password Generator.
 * @author marcelmath
 *
 */
public interface RandomOneTimePasswordGenerator {

	/**
	 * generates a random String which will be used as the One Time Password.
	 * @return the OneTimePassword
	 */
	String generateOneTimePassword();
}