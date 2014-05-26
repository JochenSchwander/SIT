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
import java.util.Arrays;

import de.hs_mannheim.sit.ss14.binaryConverter.binaryConverter;
import de.hs_mannheim.sit.ss14.hash.Hasher;
import de.hs_mannheim.sit.ss14.hash.SHA512Hasher;
import de.hs_mannheim.sit.ss14.sync.User;


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
	public boolean checkWebPassword(User user, String hashedOneTimewebPassword) throws IOException {
		if (user!=null||hashedOneTimewebPassword!=null){ //TODO: übedenken
			if(Arrays.equals(binaryConverter.base64ToByte(user.getOneTimeCode()),binaryConverter.base64ToByte(hashedOneTimewebPassword))){
				return true;
			}
		}
		return false;
	}

//	/**
//	 * Returns a User object of a user in the desktopAutenticated  List<User> if he exists there.
//	 * @param username the username for searching
//	 * @return User obj. if it exists in desktopAuthenticated List<User>
//	 */
//	private User getUserByUsername(String username) {
//		User user = new User();///TODO: ersetzen mit der suche nach dem richtigen user
//		return user;
//	}

	@Override
	public User checkDesktopPassword(String password, String username){
		User user;
		user = new User(); ///TODO: check if already exists in LIST !!
		PreparedStatement ps = null;
	    ResultSet rs = null;


	    if(getDesktopFailedLoginAttempts() < 3){ //TODO: richtige stelle ?
			if (password==null||username==null){ //TODO: übedenken
				user.setUserName("");
				increaseDesktopFailedLoginAttempts(); //increase failed login attempts in db
				user.setOneTimeCode("");
				user.setSalt("");
				return null;
			} else
			{
				//TODO: Zeitlich begrenztes otp einbauen. vllt gleich in der datenbank!
				try {
					ps = connection.prepareStatement("SELECT username, desktopPassword, webPassword, salt FROM CREDENTIAL WHERE username = ?");
					ps.setString(1, username);
			          rs = ps.executeQuery();
			          String desktopPassword = null, webPassword, salt = null, oneTimePassword;
			          if (rs.next()) {
			        	  desktopPassword = rs.getString("desktopPassword");
			        	  webPassword = rs.getString("webPassword");
			              salt = rs.getString("salt");

			              // DATABASE VALIDATION
			              if (desktopPassword == null || webPassword == null || salt == null) {
			                  throw new SQLException("Database inconsistant salt, desktopPassword, webPassword or one time password altered");
			              }
			              if (rs.next()) { // Should not append, because login is the primary key
			                  throw new SQLException("Database inconsistent two CREDENTIALS with the same LOGIN");
			              }
			          }

			          // Compute the new DIGEST
			          byte[] proposedDigest = hasher.calculateHash(desktopPassword, binaryConverter.base64ToByte(salt));

			          if(Arrays.equals(proposedDigest,binaryConverter.base64ToByte(password))){
			        	  //generate new one time password and save it to the database
			        	  oneTimePassword = generateOneTimePassword();

			        	  user.setUserName(username);
						  user.setFailedLoginAttempts(0); //TODO: increaseFailed login ??
						  user.setOneTimeCode(oneTimePassword);
						  user.setSalt(salt);
			        	  return user;
			          }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			     finally{
			          close(rs);
			          close(ps);
			     }


			}
	    }
	    else{

	    }
		return null;
	}



	private void increaseDesktopFailedLoginAttempts() {
		// TODO Auto-generated method stub

	}

//	private int getDesktopFailedLoginAttempts() {
//		// TODO Auto-generated method stub
//		int failedLogins;
//
//
//		return failedLogins;
//	}

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

//	              ///TODO: consider lenght of 8.  any suggestions ? -> note the reason, when found!!
//	              // one time password generation 8 bits long
//	              byte[] bOneTimePassword = new byte[1];
//	              random.nextBytes(bOneTimePassword);

	              // Digest computation
	              byte[] bDesktopPasswordHash = hasher.calculateHash(desktopPassword, bSalt);
	              String desktopPasswordHash = binaryConverter.byteToBase64(bDesktopPasswordHash);

	              byte[] bWebPasswordHash = hasher.calculateHash(webPassword, bSalt);
	              String webPasswordHash = binaryConverter.byteToBase64(bWebPasswordHash);

	              //make binary salt to String
	              String salt = binaryConverter.byteToBase64(bSalt);

//	              String oneTimePassword = byteToBase64(bOneTimePassword);

	              ps = connection.prepareStatement("INSERT INTO CREDENTIAL (username, desktopPassword, webPassword, salt, oneTimePassword) VALUES (?,?,?,?,?)");
	              ps.setString(1,username);
	              ps.setString(2,desktopPasswordHash);
	              ps.setString(3,webPasswordHash);
	              ps.setString(4,salt);
//	              ps.setString(5,oneTimePassword);
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





}
