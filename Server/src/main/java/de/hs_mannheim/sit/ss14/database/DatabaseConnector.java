package de.hs_mannheim.sit.ss14.database;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import de.hs_mannheim.sit.ss14.User;

/**
 *
 * @author Phil-Patrick Kai Kwiotek
 *
 */
public interface DatabaseConnector {

	public void connect() throws ClassNotFoundException, SQLException; // init connection here

	public boolean checkWebPassword(User user, String hashedOneTimewebPassword);

	public User checkDesktopPassword(String password, String username);

	public boolean createUser(String username, String desktopPassword, String webPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException, SQLException;

	public void createTableStructure() throws SQLException;

	void deleteTableStructure() throws SQLException;

	void deleteUser(String username) throws SQLException;
}
