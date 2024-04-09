package Questao3;

import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer {
    private static final int SERVER_PORT = 7896;
    private static final List<Connection> connections = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket listenSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Servidor TCP esperando por conexões na porta " + SERVER_PORT);
            while (true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println("Nova conexão recebida: " + clientSocket);
                Connection connection = new Connection(clientSocket);
                connections.add(connection);
                connection.start();
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }

    static void broadcastMessage(String message, Connection senderConnection) {
        for (Connection connection : connections) {
            if (connection != senderConnection) {
                connection.sendMessage(message);
            }
        }
    }
}

class Connection extends Thread {
    private final Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;

    public Connection(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Erro ao configurar a conexão: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = in.readUTF();
                System.out.println("Mensagem recebida de " + clientSocket.getInetAddress() + ": " + message);
                TCPServer.broadcastMessage(message, this);
            }
        } catch (IOException e) {
            System.err.println("Erro de E/S com " + clientSocket.getInetAddress() + ": " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Erro ao fechar a conexão com " + clientSocket.getInetAddress() + ": " + e.getMessage());
            }
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            System.err.println("Erro ao enviar mensagem para " + clientSocket.getInetAddress() + ": " + e.getMessage());
        }
    }
}
