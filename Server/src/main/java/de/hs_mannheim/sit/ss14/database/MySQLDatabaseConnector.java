package de.hs_mannheim.sit.ss14.database;
///TODO: decide wicht of the two below !!
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
//import sun.security.provider.SecureRandom;
///END DECISITON
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;

import de.hs_mannheim.sit.ss14.hash.Hasher;
import de.hs_mannheim.sit.ss14.hash.SHA512Hasher;
import de.hs_mannheim.sit.ss14.randomgenerator.OtpGenerator;
import de.hs_mannheim.sit.ss14.randomgenerator.SaltGenerator;
import de.hs_mannheim.sit.ss14.sync.User;


public class MySQLDatabaseConnector implements DatabaseConnector {

	private Connection connection;
	private Hasher hasher;
	private OtpGenerator otp;


	@Override
	public void connect(){
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
			connection = DriverManager.getConnection( "jdbc:mysql://localhost:3306/sit", "root", "gargelkarx" );
			hasher = new SHA512Hasher();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkWebPassword(User user, String hashedOneTimewebPassword){
		if (user!=null||hashedOneTimewebPassword!=null){ //TODO: übedenken
			if(Arrays.equals(Base64.decodeBase64(user.getOneTimeCode()),Base64.decodeBase64(hashedOneTimewebPassword))){
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


//	    if(getDesktopFailedLoginAttempts() < 3){ //TODO: richtige stelle ?
			if (password==null||username==null){ //TODO: übedenken
				user.setUserName("");
				increaseDesktopFailedLoginAttempts(); //increase failed login attempts in db
				user.setOneTimeCode("");
				user.setSalt("");
				///TODO: throw new Exception("password or username was 'null'");
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
			          byte[] proposedDigest = hasher.calculateHash(desktopPassword, Base64.decodeBase64(salt));

			          if(Arrays.equals(proposedDigest,Base64.decodeBase64(password))){
			        	  //generate new one time password and save it to the database
			        	  oneTimePassword = otp.generateOneTimePassword();

			        	  user.setUserName(username);
						  user.setOneTimeCode(oneTimePassword);
						  user.setSalt(salt);

						  resetDesktopFailedLoginAttempts();
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
//	    }
//	    else{
//
//	    }
		return null;
	}



	private void resetDesktopFailedLoginAttempts() {
		// TODO Auto-generated method stub

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
			String webPassword) {
		PreparedStatement ps = null;
	      try {
	          if (username!=null&&desktopPassword!=null&&webPassword!=null&&username.length()<=100){

	              // Salt generation 64 bits long
	              byte[] bSalt = SaltGenerator.generateOneTimePassword();

	              // Digest computation
	              byte[] bDesktopPasswordHash = hasher.calculateHash(desktopPassword, bSalt);
	              String desktopPasswordHash = Base64.encodeBase64String(bDesktopPasswordHash);

	              byte[] bWebPasswordHash = hasher.calculateHash(webPassword, bSalt);
	              String webPasswordHash = Base64.encodeBase64String(bWebPasswordHash);

	              //make binary salt to String
	              String salt = Base64.encodeBase64String(bSalt);

	              ps = connection.prepareStatement("INSERT INTO CREDENTIAL (username, desktopPassword, webPassword, salt, oneTimePassword) VALUES (?,?,?,?,?)");
	              ps.setString(1,username);
	              ps.setString(2,desktopPasswordHash);
	              ps.setString(3,webPasswordHash);
	              ps.setString(4,salt);
	              ps.executeUpdate();
	              return true;
	          } else {
	              return false;
	          }
	      } catch (NoSuchAlgorithmException e){
	    	  e.printStackTrace();
	      } catch (IOException e){
	    	  e.printStackTrace();
	      } catch (SQLException e){
	    	  e.printStackTrace();
	      } finally {
	          close(ps);
	      }
	      return false;
	}

	@Override
	public void deleteUser(String username){
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
	      } catch (SQLException e){
	    	  e.printStackTrace();
	      } finally {
	          close(st);
	      }
	  }



	@Override
	public void createTableStructure(){
	      Statement st = null;
	      try {
	            st = connection.createStatement();
				st.execute("CREATE TABLE CREDENTIAL (username VARCHAR(100) PRIMARY KEY, desktopPassword VARCHAR(256) NOT NULL, webPassword VARCHAR(256) NOT NULL, salt VARCHAR(256) NOT NULL, oneTimePassword VARCHAR(30))");
				System.out.println("Anlegen der CREDENTIAL Tabelle erfolgreich!");

	      } catch (SQLException e) {
				System.out.println("Fehler beim Anlegen der CREDENTIAL Tabelle!");

				e.printStackTrace();
		  } finally {
	          close(st);
	      }
	  }

	@Override
	public void deleteTableStructure(){
	      Statement st = null;
	      try {
	          st = connection.createStatement();
	          st.execute("DROP TABLE CREDENTIAL");
	          System.out.println("Löschen der CREDENTIAL Tabelle erfolgreich!");
	          } catch (SQLException e) {
	        	  System.out.println("Fehler bein Löschen der CREDENTIAL Tabelle!");
	        	  e.printStackTrace();
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
