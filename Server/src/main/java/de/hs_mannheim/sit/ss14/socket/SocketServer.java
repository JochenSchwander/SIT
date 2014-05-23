package de.hs_mannheim.sit.ss14.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {

	public static void main(String[] args) throws IOException {
		final ExecutorService pool;
		final ServerSocket serverSocket;
		int port = 31337;

		// Liefert einen Thread-Pool, dem bei Bedarf neue Threads hinzugefügt
		// werden. Vorrangig werden jedoch vorhandene freie Threads benutzt.
		pool = Executors.newCachedThreadPool();

		serverSocket = new ServerSocket(port);

		// Thread zur Behandlung der Client-Server-Kommunikation
		Thread t1 = new Thread(new NetworkService(pool, serverSocket));
		t1.start();
	}
}
