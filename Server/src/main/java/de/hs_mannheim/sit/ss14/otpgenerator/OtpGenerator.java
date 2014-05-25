package de.hs_mannheim.sit.ss14.otpgenerator;

import java.math.BigInteger;
import java.security.SecureRandom;

public class OtpGenerator implements OneTimePasswordGenerator {
	
	/**
	 * a Method to generate an random String
	 * @author marcelmath
	 */
	@Override
	public String generateOneTimePassword() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(40, random).toString(32);
	}

}
