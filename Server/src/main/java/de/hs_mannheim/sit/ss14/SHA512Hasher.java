package de.hs_mannheim.sit.ss14;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Phil-Patrick Kai Kwiotek
 *
 */
public class SHA512Hasher implements Hasher{
	static int ITERATION_NUMBER = 1000; //Empfehlung am 1000
	@Override
	public byte[] calculateHash(String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("SHA-512");
	      digest.reset();
	      digest.update(salt);
	      byte[] input = digest.digest(password.getBytes("UTF-8"));
	      for (int i = 0; i < ITERATION_NUMBER; i++) {
	          digest.reset();
	          input = digest.digest(input);
	      }
	      return input;
	}

}
