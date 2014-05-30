package de.hs_mannheim.sit.ss14.randomgenerator;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Class for generating random Strings to be used as one time passwords.
 *
 * @author marcelmath
 */
public class OtpGenerator implements RandomOneTimePasswordGenerator {

	/**
	 * A Method to generate an random String.
	 *
	 * @author marcelmath
	 */
	@Override
	public String generateOneTimePassword() {
		// default, "SHA1PRNG" nicht sicher!
		SecureRandom random = new SecureRandom();
		return new BigInteger(40, random).toString(32);
	}
}
