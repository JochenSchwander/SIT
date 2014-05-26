package de.hs_mannheim.sit.ss14.randomgenerator;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import de.hs_mannheim.sit.ss14.binaryConverter.binaryConverter;

public class OtpGenerator implements RandomOneTimePasswordGenerator {

	/**
	 * A Method to generate an random String
	 * @author marcelmath
	 * @lastUpdate philkwiotek
	 */
	@Override
	public String generateOneTimePassword() {
		SecureRandom random;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
			byte[] bOneTimePassword = new byte[1];
	        random.nextBytes(bOneTimePassword);

			return binaryConverter.byteToBase64(bOneTimePassword); ///TODO: siehe unten bestimmte zeichen auswählen !
			//return new BigInteger(40, random).toString(32);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
