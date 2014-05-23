package de.hs_mannheim.sit.ss14.database;
///TODO: decide wicht of the two below !!
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
//import sun.security.provider.SecureRandom;
///END DECISITON
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hs_mannheim.sit.ss14.User;
import de.hs_mannheim.sit.ss14.hash.Hasher;
import de.hs_mannheim.sit.ss14.hash.SHA512Hasher;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class MySQLDatabaseConnector implements DatabaseConnector {

	private Connection connection;
	private Hasher hasher;


	@Override
	public void connect() throws ClassNotFoundException, SQLException {
		Class.forName( "com.mysql.jdbc.Driver" );
		connection = DriverManager.getConnection( "jdbc:mysql://localhost:3306/sit", "root", "gargelkarx" );
		hasher = new SHA512Hasher();
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
			String webPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException, SQLException {
		PreparedStatement ps = null;
	      try {
	          if (username!=null&&desktopPassword!=null&&webPassword!=null&&username.length()<=100){
	              // Uses a secure Random not a simple Random
	              SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
	              // Salt generation 64 bits long
	              byte[] bSalt = new byte[8];
	              random.nextBytes(bSalt);

	              ///TODO: consider lenght of 8.  any suggestions ? -> note the reason when found!!
	              // one time password generation 8 bits long
	              byte[] bOneTimePassword = new byte[1];
	              random.nextBytes(bOneTimePassword);

	              // Digest computation
	              byte[] bDesktopPasswordHash = hasher.calculateHash(desktopPassword, bSalt);
	              String desktopPasswordHash = byteToBase64(bDesktopPasswordHash);

	              byte[] bWebPasswordHash = hasher.calculateHash(webPassword, bSalt);
	              String webPasswordHash = byteToBase64(bWebPasswordHash);

	              //make binary salt to String
	              String salt = byteToBase64(bSalt);
	              String oneTimePassword = byteToBase64(bOneTimePassword);

	              ps = connection.prepareStatement("INSERT INTO CREDENTIAL (username, desktopPassword, webPassword, salt, oneTimePassword) VALUES (?,?,?,?,?)");
	              ps.setString(1,username);
	              ps.setString(2,desktopPasswordHash);
	              ps.setString(3,webPasswordHash);
	              ps.setString(4,salt);
	              ps.setString(5,oneTimePassword);
	              ps.executeUpdate();
	              return true;
	          } else {
	              return false;
	          }
	      } finally {
	          close(ps);
	      }
	}

	@Override
	public void deleteUser(String username) throws SQLException{
	      Statement st = null;
	      try {
	          st = connection.createStatement();
	          try {
				st.execute("DELETE FROM `sit`.`CREDENTIAL` WHERE `CREDENTIAL`.`username` = \'"+username+"\'");
				System.out.println("Löschen des usernamen "+username+" erfolgreich!");
	          } catch (SQLException e) {
				System.out.println("Fehler beim Löschen des usernamen "+username+"!");

				e.printStackTrace();
			}
	      } finally {
	          close(st);
	      }
	  }



	@Override
	public void createTableStructure() throws SQLException{
	      Statement st = null;
	      try {
	          st = connection.createStatement();
	          try {
				st.execute("CREATE TABLE CREDENTIAL (username VARCHAR(100) PRIMARY KEY, desktopPassword VARCHAR(256) NOT NULL, webPassword VARCHAR(256) NOT NULL, salt VARCHAR(256) NOT NULL, oneTimePassword VARCHAR(30))");
				System.out.println("Anlegen der CREDENTIAL Tabelle erfolgreich!");
	          } catch (SQLException e) {
				System.out.println("Fehler beim Anlegen der CREDENTIAL Tabelle!");

				e.printStackTrace();
			}
	      } finally {
	          close(st);
	      }
	  }

	@Override
	public void deleteTableStructure() throws SQLException{
	      Statement st = null;
	      try {
	          st = connection.createStatement();
	          try {
				st.execute("DROP TABLE CREDENTIAL");
				System.out.println("Löschen der CREDENTIAL Tabelle erfolgreich!");
	          } catch (SQLException e) {
				System.out.println("Fehler bein Löschen der CREDENTIAL Tabelle!");

				e.printStackTrace();
			}
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

	  /**
	   * From a base 64 representation, returns the corresponding byte[]
	   * @param data String The base64 representation
	   * @return byte[]
	   * @throws IOException
	   */
	  public static byte[] base64ToByte(String data) throws IOException {
	      BASE64Decoder decoder = new BASE64Decoder();
	      return decoder.decodeBuffer(data);
	  }

	  /**
	   * From a byte[] returns a base 64 representation
	   * @param data byte[]
	   * @return String
	   * @throws IOException
	   */
	  public static String byteToBase64(byte[] data){
	      BASE64Encoder endecoder = new BASE64Encoder();
	      return endecoder.encode(data);
	  }



}
