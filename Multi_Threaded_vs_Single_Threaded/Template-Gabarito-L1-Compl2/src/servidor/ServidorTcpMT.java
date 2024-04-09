package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import calculadora.Calculadora;

public class ServidorTcpMT {

	public static void main(String args[]) {
		Socket clientSocket;
		try {
			int serverPort = 7896;
			ServerSocket listenSocket = new ServerSocket(serverPort);
			while (true) {
				clientSocket = listenSocket.accept();
				Connection connection = new Connection(clientSocket);
				connection.start();
			}
		} catch (IOException e) {
			System.out.println("Listen :" + e.getMessage());
		}
	}
}

class Connection extends Thread {
	Socket clientSocket;
	static Calculadora calculadora = Calculadora.getInstance();
	DataInputStream in = null;
	DataOutputStream out = null;

	public Connection(Socket aClientSocket) throws IOException {
		clientSocket = aClientSocket;
		in = new DataInputStream(clientSocket.getInputStream());
		out = new DataOutputStream(clientSocket.getOutputStream());
	}

	public String getRequest( ) throws IOException {
		// calculadora.getInstance();
	}

	public void sendResponse(String response) throws IOException {
	}
	
	public void run() {
		getRequest();

		Thread.sleep(100);
		sendResponse()	;
	}
}