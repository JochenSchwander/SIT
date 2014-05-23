package de.hs_mannheim.sit.ss14.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Class for starting the Socket Network Service.
 *
 * @author Jochen Schwander
 */
@SuppressWarnings("serial")
public class SocketServer extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		final ExecutorService pool;
		final ServerSocket serverSocket;
		int port = 31337;

		// Liefert einen Thread-Pool, dem bei Bedarf neue Threads hinzugefügt
		// werden. Vorrangig werden jedoch vorhandene freie Threads benutzt.
		pool = Executors.newCachedThreadPool();

		try {
			serverSocket = new ServerSocket(port);

			// Thread zur Behandlung der Client-Server-Kommunikation
			Thread daemon = new Thread(new NetworkService(pool, serverSocket));
			daemon.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
