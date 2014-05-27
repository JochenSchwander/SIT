package de.hs_mannheim.sit.ss14.hash;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class SHA512Hasher implements Hasher {
	static int ITERATION_NUMBER = 1000; // Empfehlung am 1000

	/**
	 * A SHA-512 algorithm to hash a pw with a salt 1000 times
	 *
	 * @password the password to hash
	 * @salt the salt added to the password
	 * @author Phil-Patrick Kai Kwiotek
	 *
	 */
	@Override
	public String calculateHash(String password, String salt) throws NoSuchAlgorithmException, IOException {
		MessageDigest digest = MessageDigest.getInstance("SHA-512");

		digest.reset();
		byte[] input = digest.digest((password + salt).getBytes("UTF-8")); // first hash

		for (int i = 1; i < ITERATION_NUMBER; i++) { // the other 999 hashes
			digest.reset();
			input = digest.digest(input);
		}
		return Base64.encodeBase64String(input);
	}


}
