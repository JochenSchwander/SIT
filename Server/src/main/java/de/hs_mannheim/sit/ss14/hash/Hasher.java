package de.hs_mannheim.sit.ss14.hash;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


/**
 * Interface for Hasher.
 *
 * @author Phil-Patrick Kai Kwiotek
 */
public interface Hasher {

	/**
	 * Hashes the given password with the given salt.
	 *
	 * @param password password to hash
	 * @param salt salt for hashing
	 * @return salted 512Bit hash
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	String calculateHash(String password, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException;

	byte[] calculateHash(String desktopPassword, byte[] bSalt) throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException;
}
