package de.hs_mannheim.sit.ss14;

/**
 * 
 * @author Phil-Patrick Kai Kwiotek
 *
 */
public interface DatabaseConnector {

	public void DatabaseConnectior(); // init connection here

	public boolean checkWebPassword(User user, String hashedOneTimewebPassword);

	public User checkDesktopPassword(String password, String username);

	public boolean createUser(String username, String desktopPassword, String webPassword);

	public void createDatabaseStructure();
}
