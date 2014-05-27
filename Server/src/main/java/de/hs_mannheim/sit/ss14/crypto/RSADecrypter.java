package de.hs_mannheim.sit.ss14.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.LinkedList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

/**
 * Class for decrypting hybrid RSA AES encrypted Strings.
 *
 * @author Jochen Schwander
 */
public class RSADecrypter {

	private Cipher rsaCipher;

	/**
	 * Initializes the rsa decrypter with the servers private key.
	 */
	public RSADecrypter() {
		try {
			rsaCipher = Cipher.getInstance("RSA");
			rsaCipher.init(Cipher.UNWRAP_MODE, getPrivateKey());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidKeySpecException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Decrypts the given string with the servers rsa private key.
	 *
	 * @param string the string to decrypt
	 * @return decrypted string
	 */
	public List<String> decrypt(final String string) {
		try {
			String[] strings = string.split(";");
			byte[] wrappedKey = Base64.decodeBase64(strings[0]);

			Key symKey = rsaCipher.unwrap(wrappedKey, "AES", Cipher.SECRET_KEY);
			Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			aesCipher.init(Cipher.DECRYPT_MODE, symKey, new IvParameterSpec(new byte[16]));

			List<String> output = new LinkedList<String>();
			for (int i = 1; i < strings.length; i++) {
				output.add(0, Base64.encodeBase64String(aesCipher.doFinal(Base64.decodeBase64(strings[i]))));
			}

			return output;
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Read private key from file with filename.
	 *
	 * @param filename
	 *            key file
	 *
	 * @return PrivateKey
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	private PrivateKey getPrivateKey() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
		InputStream f = this.getClass().getClassLoader().getResourceAsStream("private_key.der");
		byte[] keyBytes = new byte[(int) 1217];
		f.read(keyBytes);
		f.close();

		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");

		return kf.generatePrivate(spec);
	}
}
