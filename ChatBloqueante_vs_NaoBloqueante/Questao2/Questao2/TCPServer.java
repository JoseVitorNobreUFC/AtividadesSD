package Questao2;

import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer {
    private static final int SERVER_PORT = 7896;
    private static final List<Connection> connections = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket listenSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server is running and waiting for connections...");

            while (true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println("New connection accepted: " + clientSocket);

                Connection connection = new Connection(clientSocket);
                connections.add(connection);
                connection.start();

                if (connections.size() >= 2) {
                    connection.notifyReady(); // Notify the connection that it's ready to start exchanging messages
                }
            }
        } catch (IOException e) {
            System.err.println("Error starting the server: " + e.getMessage());
        }
    }

    static synchronized void relayMessage(String message, Connection sender) {
        for (Connection connection : connections) {
            if (!connection.equals(sender)) {
                connection.sendMessage(message);
            }
        }
    }
}

class Connection extends Thread {
    private final Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean ready = false;

    public Connection(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error setting up connection: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (!ready) {
                    wait(); // Wait until the connection is ready
                }
            }

            while (true) {
                String message = in.readUTF();
                System.out.println("Received: " + message);
                TCPServer.relayMessage(message, this);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error handling connection: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }

    public synchronized void notifyReady() {
        ready = true;
        notify();
    }

    public synchronized void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }
}