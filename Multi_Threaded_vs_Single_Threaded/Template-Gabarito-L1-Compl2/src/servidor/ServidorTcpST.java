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
	public static void main(String argv[]) throws Exception {
		
		ServerSocket welcomeSocket = new ServerSocket(8000);

		while (true) {
			s = welcomeSocket.accept();
			getRequest();
			
			Thread.sleep(100);
			sendResponse();
		}
	}
	
	public static String getRequest(String calcOperation) throws IOException {
		Double value = calculadora.add(
				Double.parseDouble(calcOperation.split(calcOperation)[0]),
				Double.parseDouble(calcOperation.split(calcOperation)[2])
			);
		return value.toString();
	}
	
	public static void sendResponse(String response) throws IOException {

	}
}