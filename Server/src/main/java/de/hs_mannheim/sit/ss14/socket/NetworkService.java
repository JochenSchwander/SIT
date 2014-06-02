package de.hs_mannheim.sit.ss14.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import de.hs_mannheim.sit.ss14.crypto.RSADecrypter;
import de.hs_mannheim.sit.ss14.database.DatabaseConnector;
import de.hs_mannheim.sit.ss14.database.MySQLDatabaseConnector;

/**
 * Service Thread, that accepts incomming socket connections.
 *
 * @author Jochen Schwander
 */
public class NetworkService implements Runnable {
	private final ServerSocket serverSocket;
	private final ExecutorService pool;
	private final DatabaseConnector dbcon;
	private final RSADecrypter rsaDecrypter;

	/**
	 * Constructor.
	 *
	 * @param pool the ExecutorService that is used to launch the connection specific threads
	 * @param serverSocket the ServerSocket, on which the connections are coming in
	 */
	public NetworkService(final ExecutorService pool, final ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		this.pool = pool;
		rsaDecrypter = new RSADecrypter();
		dbcon = new MySQLDatabaseConnector();

		try {
			dbcon.connect();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				pool.execute(new Handler(serverSocket.accept(), dbcon, rsaDecrypter));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pool.shutdown(); // keine Annahme von neuen Anforderungen
			try {
				// warte maximal 4 Sekunden auf Beendigung aller Anforderungen
				pool.awaitTermination(4L, TimeUnit.SECONDS);
				if (!serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
