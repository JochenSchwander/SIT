package de.hs_mannheim.sit.ss14.hash;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;


public class SHA512Hasher implements Hasher{
	static int ITERATION_NUMBER = 1000; //Empfehlung am 1000

	/**
	 * A SHA-512 algorithm to hash a pw with a salt 1000 times
	 * @password the password to hash
	 * @salt the salt added to the password
	 * @author Phil-Patrick Kai Kwiotek
	 *
	 */
	@Override
	public String calculateHash(String password, String salt) throws NoSuchAlgorithmException, IOException {
		MessageDigest digest = MessageDigest.getInstance("SHA-512");
	      digest.reset();
	      digest.update(Base64.decodeBase64(salt));
	      byte[] input = digest.digest(password.getBytes("UTF-8"));
	      for (int i = 0; i < ITERATION_NUMBER; i++) {
	          digest.reset();
	          input = digest.digest(input);
	      }
	      return Base64.encodeBase64String(input);
	}


	  /**
	   * use "String calculateHash(String,String)" with byte[]
	   */
	  @Override
	  public byte[] calculateHash(String desktopPassword, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
	  	return Base64.decodeBase64(calculateHash(desktopPassword, Base64.encodeBase64String(salt)));
	  }
}


