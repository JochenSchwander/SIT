package de.hs_mannheim.sit.ss14.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import de.hs_mannheim.sit.ss14.DatabaseConnector;
import de.hs_mannheim.sit.ss14.MySQLDatabaseConnector;

class NetworkService implements Runnable {
	private final ServerSocket serverSocket;
	private final ExecutorService pool;
	private final DatabaseConnector dbcon;

	public NetworkService(ExecutorService pool, ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		this.pool = pool;
		this.dbcon = new MySQLDatabaseConnector();
	}

	@Override
	public void run() {
		try {
			while (true) {
				pool.execute(new Handler(serverSocket.accept(), dbcon));
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
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}