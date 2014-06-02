package de.hs_mannheim.sit.ss14.database;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
		MySQLDatabaseConnector dbCon = new MySQLDatabaseConnector();
		dbCon.connect();
		//dbCon.deleteTableStructure();
		//dbCon.createTableStructure();
		//dbCon.createUser("phil", "wasIstMartin", "marinIstEinBob");
		//dbCon.deleteUser("phil");
		//dbCon.resetDesktopFailedLoginAttempts("phil");

	}

}
