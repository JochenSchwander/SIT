package de.hs_mannheim.sit.ss14.randomgenerator;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SaltGenerator{

	/**
	 * A Method to generate an random 64bit salt byte[]
	 * @author philkwiotek
	 */
	public static byte[] generateOneTimePassword() {
		SecureRandom random;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
			byte[] bSalt = new byte[8];
	        random.nextBytes(bSalt);

			return bSalt;

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}

