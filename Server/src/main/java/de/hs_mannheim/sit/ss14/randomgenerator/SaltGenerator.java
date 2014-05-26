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

			return bSalt; ///TODO: siehe unten bestimmte zeichen auswählen !
			//return new BigInteger(40, random).toString(32);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

