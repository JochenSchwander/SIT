package de.hs_mannheim.sit.ss14.otpgenerator;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import de.hs_mannheim.sit.ss14.binaryconverter.BinaryConverter;

public class OtpGenerator implements OneTimePasswordGenerator {

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

			return BinaryConverter.byteToBase64(bOneTimePassword);
			//return new BigInteger(40, random).toString(32);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
