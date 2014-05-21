package de.hs_mannheim.sit.ss14;

import java.sql.SQLException;

/**
 *
 * @author Phil-Patrick Kai Kwiotek
 *
 */
public interface DatabaseConnector {

	public void DatabaseConnectior() throws ClassNotFoundException, SQLException; // init connection here

	public boolean checkWebPassword(User user, String hashedOneTimewebPassword);

	public User checkDesktopPassword(String password, String username);

	public boolean createUser(String username, String desktopPassword, String webPassword);

	public void createDatabaseStructure() throws SQLException;
}
