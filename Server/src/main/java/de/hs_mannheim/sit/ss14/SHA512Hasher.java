package de.hs_mannheim.sit.ss14;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

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

}
