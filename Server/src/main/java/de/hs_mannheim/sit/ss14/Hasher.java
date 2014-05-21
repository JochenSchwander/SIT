package de.hs_mannheim.sit.ss14;

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
	 */
	byte[] calculateHash(String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException;
}
