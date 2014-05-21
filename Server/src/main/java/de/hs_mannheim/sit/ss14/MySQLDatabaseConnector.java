package de.hs_mannheim.sit.ss14;
import java.sql.Connection;

public class MySQLDatabaseConnector implements DatabaseConnector{

	private Connection connection ;
	private Hasher hasher ;
	
	@Override
	public void DatabaseConnectior() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkWebPassword(User user, String hashedOneTimewebPassword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User checkDesktopPassword(String password, String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createUser(String username, String desktopPassword,
			String webPassword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createDatabaseStructure() {
		// TODO Auto-generated method stub
		
	}

}
