package Questao2;
import java.io.*;
import java.net.*;

public class TCPClient {
    private static final int SERVER_PORT = 7896;
    private static final String SERVER_HOSTNAME = "localhost";

    public static void main(String[] args) {
        try {
            final Socket socket = new Socket(SERVER_HOSTNAME, SERVER_PORT); // Declare socket as final

            // Thread to handle receiving messages from the other client
            Thread receiverThread = new Thread(() -> {
                try {
                    receive(socket);
                } catch (IOException e) {
                    System.err.println("Error while receiving message: " + e.getMessage());
                }
            });
            receiverThread.start();

            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            String message;

            // Loop to send messages and wait for the other client's response
            while (true) {
                // Read input from the user
                System.out.print("You: ");
                message = console.readLine();
                
                // Send the message to the server
                send(message, socket);
                
                // Wait for response from the other client
                receiverThread.join();
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }
    }

    private static String receive(Socket socket) throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        return in.readUTF();
    }

    private static void send(String message, Socket socket) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(message);
    }
}
