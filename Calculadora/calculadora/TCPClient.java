import java.net.*;
import java.util.Scanner;
import java.io.*;

public class TCPClient {
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        Socket s = null;
        try {
            int serverPort = 7896;
            s = new Socket("localhost", serverPort);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            while (true) {
                String operation = input.nextLine();
                out.writeUTF(operation);
                String data = in.readUTF();
                System.out.println(data);
                if(operation.equals("exit")) {
                    break;
                }
            }
        } catch (UnknownHostException e) {
            System.out.println("Sock:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
            }
        }
    }
}
