package de.hs_mannheim.sit.ss14;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


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
	      digest.update(base64ToByte(salt));
	      byte[] input = digest.digest(password.getBytes("UTF-8"));
	      for (int i = 0; i < ITERATION_NUMBER; i++) {
	          digest.reset();
	          input = digest.digest(input);
	      }
	      return byteToBase64(input);
	}

	  /**
	   * From a base 64 representation, returns the corresponding byte[]
	   * @param data String The base64 representation
	   * @return byte[]
	   * @throws IOException
	   */
	  public static byte[] base64ToByte(String data) throws IOException {
	      BASE64Decoder decoder = new BASE64Decoder();
	      return decoder.decodeBuffer(data);
	  }

	  /**
	   * From a byte[] returns a base 64 representation
	   * @param data byte[]
	   * @return String
	   * @throws IOException
	   */
	  public static String byteToBase64(byte[] data){
	      BASE64Encoder endecoder = new BASE64Encoder();
	      return endecoder.encode(data);
	  }


	  /**
	   * use "String calculateHash(String,String)" with byte[]
	   */
	  @Override
	  public byte[] calculateHash(String desktopPassword, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
	  	return base64ToByte(calculateHash(desktopPassword, byteToBase64(salt)));
	  }
}


