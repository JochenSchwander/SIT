package de.hs_mannheim.sit.ss14.crypto;

import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

public class AESFilterOutputStream extends CipherOutputStream {

	public AESFilterOutputStream(OutputStream out, String key) throws Exception {
		super(out, Cipher.getInstance("AES/CBC").init(Cipher.DECRYPT_MODE, new SecretKeySpec(hexStringToByteArray(key), "AES")));
	}


	private byte[] hexStringToByteArray(final String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

}
