package de.hs_mannheim.sit.ss14.database;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import de.hs_mannheim.sit.ss14.hash.Hasher;
import de.hs_mannheim.sit.ss14.hash.SHA512Hasher;
import de.hs_mannheim.sit.ss14.randomgenerator.OtpGenerator;
import de.hs_mannheim.sit.ss14.randomgenerator.SaltGenerator;
import de.hs_mannheim.sit.ss14.sync.User;

public class MySQLDatabaseConnector implements DatabaseConnector {

	private Connection connection;
	private Hasher hasher;
	private OtpGenerator otp;
	private Pattern whitelist;

	public MySQLDatabaseConnector() {
		whitelist = Pattern.compile("[a-zA-Z0-9]*");
	}

	@Override
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sit", "root", "gargelkarx");
			hasher = new SHA512Hasher();
			otp = new OtpGenerator();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkWebPassword(User user, String hashedOneTimeWebPassword) {
		if (user != null || hashedOneTimeWebPassword != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			String hashedWebPassword = null;
			String serverHashedOneTimeWebPassword = null;

			try {
				ps = connection.prepareStatement("SELECT webPassword, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 5 MINUTE) AS timestamp FROM CREDENTIAL WHERE username = ?");
				ps.setString(1, user.getUserName());
				rs = ps.executeQuery();
				if (rs.next()) {
					hashedWebPassword = rs.getString("webPassword");

					// DATABASE VALIDATION
					if (hashedWebPassword == null) {
						throw new SQLException("Database inconsistant webPassword, timestamp altered");
					}
					if (rs.next()) { // Should not append, because login is the primary key
						throw new SQLException("Database inconsistent two CREDENTIALS with the same LOGIN");
					}
				}

				String oneTimeCode = user.getOneTimeCode();
				// db: h(webPW + OTP)
				serverHashedOneTimeWebPassword = hasher.calculateHash(hashedWebPassword, oneTimeCode);
			} catch (NoSuchAlgorithmException | IOException | SQLException e) {
				e.printStackTrace();
			}

			if (new Date(System.currentTimeMillis()).before(user.getOneTimePasswordExpirationDate())) {
				if (serverHashedOneTimeWebPassword.equals(hashedOneTimeWebPassword)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public User checkDesktopPassword(String password, String username) {
		User user = new User();
		PreparedStatement ps = null;
		ResultSet rs = null;

		if (password == null || username == null) {
			return null;
		} else {
			//whitelist
			Matcher matcher = whitelist.matcher(username);
			if (!matcher.matches()) {
				return null;
			}

			try {
				ps = connection.prepareStatement("SELECT desktopPassword, salt, desktopFailedLoginAttempts FROM CREDENTIAL WHERE username = ?");
				ps.setString(1, username);
				rs = ps.executeQuery();
				String desktopPassword = null, salt = null, oneTimePassword;
				int desktopFailedLoginAttempts = 0;

				if (rs.next()) {
					desktopPassword = rs.getString("desktopPassword");
					salt = rs.getString("salt");
					desktopFailedLoginAttempts = rs.getInt("desktopFailedLoginAttempts");

					// DATABASE VALIDATION
					if (desktopPassword == null || salt == null) {
						throw new SQLException("Database inconsistant salt, desktopPassword, webPassword or one time password altered");
					}
					if (rs.next()) { // Should not append, because login is the primary key
						throw new SQLException("Database inconsistent two CREDENTIALS with the same LOGIN");
					}
				}

				// Compute the new DIGEST
				byte[] digest = Base64.decodeBase64(hasher.calculateHash(password, salt));

				if (Arrays.equals(digest, Base64.decodeBase64(desktopPassword)) && desktopFailedLoginAttempts < 3) {
					// generate new one time password
					oneTimePassword = otp.generateOneTimePassword();

					user.setUserName(username);
					user.setOneTimeCode(oneTimePassword);
					user.setSalt(salt);
					user.setOneTimePasswordExpirationDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000));

					resetDesktopFailedLoginAttempts(username);
					return user;
				} else if (desktopFailedLoginAttempts < 3) {
					increaseDesktopFailedLoginAttempts(username);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} finally {
				close(rs);
				close(ps);
			}
		}
		return null;
	}

	public void resetDesktopFailedLoginAttempts(String username) {
		PreparedStatement ps = null;
		// increase desktopFailedLoginAttempts by 1
		try {
			ps = connection.prepareStatement("UPDATE CREDENTIAL SET `desktopFailedLoginAttempts` = 0 WHERE username = ?;");
			ps.setString(1, username);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Increases the failed login attempts by one for the desktop client in the database
	 *
	 * @param username
	 *            the username of the user which failed login attempts will be increased
	 */
	private void increaseDesktopFailedLoginAttempts(String username) {
		PreparedStatement ps = null;
		// increase desktopFailedLoginAttempts by 1
		try {
			ps = connection.prepareStatement("UPDATE CREDENTIAL SET `desktopFailedLoginAttempts` = `desktopFailedLoginAttempts` + 1 WHERE username = ?;");
			ps.setString(1, username);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Creates a
	 */
	@Override
	public boolean createUser(String username, String desktopPassword, String webPassword) {
		PreparedStatement ps = null;
		try {
			if (username != null && desktopPassword != null && webPassword != null && username.length() <= 100) {

				// Salt generation 64 bits long
				byte[] bSalt = SaltGenerator.generateOneTimePassword();

				// Digest computation
				byte[] bDesktopPasswordHash = Base64.decodeBase64((hasher.calculateHash(desktopPassword, Base64.encodeBase64String(bSalt))));
				String desktopPasswordHash = Base64.encodeBase64String(bDesktopPasswordHash);

				byte[] bWebPasswordHash = Base64.decodeBase64((hasher.calculateHash(webPassword, Base64.encodeBase64String(bSalt))));
				String webPasswordHash = Base64.encodeBase64String(bWebPasswordHash);

				// make binary salt to String
				String salt = Base64.encodeBase64String(bSalt);

				ps = connection.prepareStatement("INSERT INTO CREDENTIAL (username, desktopPassword, webPassword, salt) VALUES (?,?,?,?)"); // desktopFailedLoginAttempts standard 0
				ps.setString(1, username);
				ps.setString(2, desktopPasswordHash);
				ps.setString(3, webPasswordHash);
				ps.setString(4, salt);
				ps.executeUpdate();
				return true;
			} else {
				return false;
			}
		} catch (MySQLIntegrityConstraintViolationException userAlreadyExists) {
			return false;
		} catch (SQLException | IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
		return false;
	}

	@Override
	public void deleteUser(String username) {
		Statement st = null;
		try {
			st = connection.createStatement();
			try {
				st.execute("DELETE FROM `sit`.`CREDENTIAL` WHERE `CREDENTIAL`.`username` = \'" + username + "\'");
				System.out.println("Löschen des usernamen " + username + " erfolgreich!");
			} catch (SQLException e) {
				System.out.println("Fehler beim Löschen des usernamen " + username + "!");

				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(st);
		}
	}

	@Override
	public void createTableStructure() {
		Statement st = null;
		try {
			st = connection.createStatement();
			st.execute("CREATE TABLE CREDENTIAL (username VARCHAR(100) PRIMARY KEY, desktopPassword VARCHAR(256) NOT NULL, webPassword VARCHAR(256) NOT NULL, salt VARCHAR(256) NOT NULL, desktopFailedLoginAttempts INTEGER(8) DEFAULT 0)");
			System.out.println("Anlegen der CREDENTIAL Tabelle erfolgreich!");

		} catch (SQLException e) {
			System.out.println("Fehler beim Anlegen der CREDENTIAL Tabelle!");

			e.printStackTrace();
		} finally {
			close(st);
		}
	}

	@Override
	public void deleteTableStructure() {
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
	 *
	 * @param ps
	 *            Statement
	 */
	public void close(Statement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException ignore) {
			}
		}
	}

	/**
	 * Closes the current resultset
	 *
	 * @param ps
	 *            Statement
	 */
	public void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ignore) {
			}
		}
	}

}
