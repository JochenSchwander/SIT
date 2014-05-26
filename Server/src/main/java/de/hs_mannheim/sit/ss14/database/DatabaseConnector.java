package de.hs_mannheim.sit.ss14.database;

import java.sql.SQLException;

import de.hs_mannheim.sit.ss14.sync.User;

/**
 *
 * @author Phil-Patrick Kai Kwiotek
 *
 */
public interface DatabaseConnector {

	public void connect() throws ClassNotFoundException, SQLException; // init connection here

	public boolean checkWebPassword(User user, String hashedOneTimeWebPassword);

	public User checkDesktopPassword(String password, String username);

	public boolean createUser(String username, String desktopPassword, String webPassword);

	public void createTableStructure();

	void deleteTableStructure();

	void deleteUser(String username);
}
