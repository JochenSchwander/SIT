package de.hs_mannheim.sit.ss14.auxiliaries;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

	private PublicKey publicKey;

	Cipher cipher;

	public RSAEncrypter() {
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.WRAP_MODE, getPublicKey());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Encrypts the given plainText with AES and cancatinates the RSA encrypted AES-key, seperated with ';'
	 * @param plainText the plain text to encrypt
	 * @return String "RSA(AES-key);AES(plainText)
	 */
	public String encrypt(String plainText) {
		try {


			//AES
			 KeyGenerator keyGen = KeyGenerator.getInstance( "AES" );
	         keyGen.init( Math.min( 256, Cipher.getMaxAllowedKeyLength( "AES" ) ) );
	         SecretKey aesKey = keyGen.generateKey();

			Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, new IvParameterSpec(new byte[16]));

			String cypherText = Base64.encodeBase64String(aesCipher.doFinal(Base64.decodeBase64(plainText)));
			String wrappedKey = Base64.encodeBase64String(cipher.wrap( aesKey ));

			return wrappedKey + ";" + cypherText;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static PublicKey getPublicKey() {
		try {
			File f = new File("public_key");
			FileInputStream fis = new FileInputStream(f);
			DataInputStream dis = new DataInputStream(fis);
			byte[] keyBytes = new byte[(int) f.length()];
			dis.readFully(keyBytes);
			dis.close();

			X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			return kf.generatePublic(spec);
		} catch (IOException | NoSuchAlgorithmException
				| InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

}