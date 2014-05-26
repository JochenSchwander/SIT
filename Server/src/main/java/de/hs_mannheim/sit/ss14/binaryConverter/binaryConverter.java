package de.hs_mannheim.sit.ss14.binaryConverter;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class binaryConverter {
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
