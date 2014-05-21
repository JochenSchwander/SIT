package de.hs_mannheim.sit.ss14;


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
	 */
	String calculateHash(String password, String salt, int iterations);
}
