package de.hs_mannheim.sit.ss14.auxiliaries;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class RSAEncrypter {

	Cipher rsaCipher;

	public RSAEncrypter() {
		try {
			rsaCipher = Cipher.getInstance("RSA");
			rsaCipher.init(Cipher.WRAP_MODE, getPublicKey());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Encrypts the given plainText with AES and cancatinates the RSA encrypted AES-key, seperated with ';'
	 *
	 * @param plainText
	 *            the plain text to encrypt
	 * @return "RSA(AES-key);AES(strings[0]);AES(strings[1]);AES(strings[2])"
	 */
	public String encrypt(String plainText) {
		try {

			// AES
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(Math.min(256, Cipher.getMaxAllowedKeyLength("AES")));
			SecretKey aesKey = keyGen.generateKey();

			Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, new IvParameterSpec(new byte[16]));

			String cypherText = Base64.encodeBase64String(aesCipher.doFinal(plainText.getBytes("UTF-8")));

			String wrappedKey = Base64.encodeBase64String(rsaCipher.wrap(aesKey));

			return wrappedKey + ";" + cypherText;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	private PublicKey getPublicKey() {
		try {
			InputStream f = this.getClass().getClassLoader().getResourceAsStream("public_key");

			byte[] keyBytes = new byte[294];
			f.read(keyBytes);
			f.close();

			X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			return kf.generatePublic(spec);
		} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

}
