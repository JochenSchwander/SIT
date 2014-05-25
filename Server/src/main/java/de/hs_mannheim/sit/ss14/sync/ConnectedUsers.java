package de.hs_mannheim.sit.ss14.sync;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Sync between Socket and Web connections for authentification.
 *
 * @author Jochen Schwander
 */
public final class ConnectedUsers {

	private static Map<String, User> authorized = Collections.synchronizedMap(new HashMap<String, User>());

	private static Map<String, User> pending = Collections.synchronizedMap(new HashMap<String, User>());

	private ConnectedUsers() {
	}

	/**
	 * Remove an authorized user.
	 *
	 * @param user the user to remove
	 */
	public static void removeAuthorizedUser(final User user) {
		authorized.remove(user.getUserName());
	}

	/**
	 * Removes a pending user and closes its socket connection.
	 *
	 * @param username the users username
	 */
	public static void removePendingUser(final String username) {
		pending.get(username).getHandler().webloginFail();
		pending.remove(username);
	}

	/**
	 * Removes a pending user without closing it's socket connection.
	 *
	 * @param user the user to remove
	 */
	public static void dropPendingUser(final User user) {
		pending.remove(user.getUserName());
	}

	/**
	 * Adds a user to the pending users.
	 *
	 * @param user the user to add
	 */
	public static void addPendingUser(final User user){
		pending.put(user.getUserName(), user);
	}

	/**
	 * Authorizes the user.
	 *
	 * @param username the users username
	 */
	public static void authorizeUser(final String username) {
		User user = pending.get(username);
		pending.remove(username);
		authorized.put(username, user);
		user.getHandler().webloginSuccess();
	}

	/**
	 * Checks if the given user is already authorized.
	 *
	 * @param user the user to check
	 *
	 * @return true, if already authorized, false otherwise
	 */
	public static boolean isAlreadyAuthorized(final User user) {
		return authorized.containsKey(user.getUserName());
	}

	/**
	 * Returns the pending user.
	 *
	 * @param username the users username
	 * @return pending user
	 */
	public static User getPendingUser(final String username) {
		return pending.get(username);
	}

}
