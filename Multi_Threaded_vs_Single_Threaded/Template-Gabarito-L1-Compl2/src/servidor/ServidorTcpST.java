package servidor;

import calculadora.*;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTcpST {
	static Socket s;
	static Calculadora calculadora = Calculadora.getInstance();
	static DataOutputStream out;
	static DataInputStream in;
	public static void main(String argv[]) throws Exception {
		
		ServerSocket welcomeSocket = new ServerSocket(8000);

		while (true) {
			s = welcomeSocket.accept();
			out = new DataOutputStream(s.getOutputStream());
			in = new DataInputStream(s.getInputStream());
			String operations = getRequest();
			int value1 = Integer.parseInt(operations.split(" ")[0]);
			int value2 = Integer.parseInt(operations.split(" ")[2]);
			String value = String.valueOf(calculadora.add(value1, value2));

			Thread.sleep(100);
			sendResponse(value);
		}
	}
	
	public static String getRequest() throws IOException {
		return in.readUTF();
	}
	
	public static void sendResponse(String response) throws IOException {
		out.writeUTF(response);
	}
}