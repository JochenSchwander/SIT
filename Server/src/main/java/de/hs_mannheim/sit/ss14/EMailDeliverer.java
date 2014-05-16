package de.hs_mannheim.sit.ss14;

/**
 * Interface for sending one time passwords via email.
 * 
 * @author Jochen Schwander
 */
public interface EMailDeliverer {
	
	/**
	 * Send the one time password to the user.
	 * 
	 * @param user the user getting the mail
	 */
	void sendEMail(User user);
}
