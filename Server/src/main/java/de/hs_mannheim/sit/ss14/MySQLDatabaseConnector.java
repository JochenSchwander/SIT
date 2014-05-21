package de.hs_mannheim.sit.ss14;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDatabaseConnector implements DatabaseConnector{

	private Connection connection;
	private Hasher hasher;

	@Override
	public void DatabaseConnectior() throws ClassNotFoundException, SQLException {
		Class.forName( "com.mysql.jdbc.Driver" );
		connection = DriverManager.getConnection( "jdbc:mysql://localhost:8889/sit", "root", "gargelkarx" );


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
	public void createDatabaseStructure() throws SQLException{
	      Statement st = null;
	      try {
	          st = connection.createStatement();
	          st.execute("CREATE TABLE CREDENTIAL (username VARCHAR(100) PRIMARY KEY, desktopPassword VARCHAR(256) NOT NULL, webPassword VARCHAR(256) NOT NULL, salt VARCHAR(256) NOT NULL, oneTimePassword VARCHAR(30),)");
	      } finally {
	          close(st);
	      }
	  }

	/**
	   * Closes the current statement
	   * @param ps Statement
	   */
	  public void close(Statement ps) {
	      if (ps!=null){
	          try {
	              ps.close();
	          } catch (SQLException ignore) {
	          }
	      }
	  }

	  /**
	   * Closes the current resultset
	   * @param ps Statement
	   */
	  public void close(ResultSet rs) {
	      if (rs!=null){
	          try {
	              rs.close();
	          } catch (SQLException ignore) {
	          }
	      }
	  }



}
