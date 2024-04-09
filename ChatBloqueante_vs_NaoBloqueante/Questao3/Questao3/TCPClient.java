package Questao3;

import java.io.*;
import java.net.*;

public class TCPClient {
	private static final int SERVER_PORT = 7896;
	private static final String SERVER_HOSTNAME = "localhost";

	private static void receive(Socket socket) {
		try {
			DataInputStream in = new DataInputStream(socket.getInputStream());
			while (true) {
				String message = in.readUTF();
				System.out.println("Received: " + message);
			}
		} catch (IOException e) {
			System.err.println("Error while receiving message: " + e.getMessage());
		}
	}

	private static void send(String message, Socket socket) {
		try {
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(message);
		} catch (IOException e) {
			System.err.println("Error while sending message: " + e.getMessage());
		}
	}

	public static void main(String[] args) {

		try {
			final Socket socket = new Socket(SERVER_HOSTNAME, SERVER_PORT);
			System.out.println("Connected to server.");

			// Inicia uma thread para receber mensagens do servidor
			Thread receiverThread = new Thread(() -> receive(socket));
			receiverThread.start();

			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
			String message;

			// Loop para enviar mensagens para o servidor
			System.out.println("Type your message and hit enter to send");
			while ((message = console.readLine()) != null) {
				send(message, socket);
				System.out.println("You sent: " + message);
			}

			socket.close();
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("IO Error: " + e.getMessage());
		} 
	}
}
