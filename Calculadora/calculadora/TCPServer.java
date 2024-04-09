import java.net.*;

import java.io.*;
public class TCPServer {

    public static void main(String[] args) {
        try {
            int serverPort = 7896;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while(true) {
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket);
            }
        }catch(IOException e) {System.out.println("Listen:"+e.getMessage());}
    }
}

class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    public Connection (Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream( clientSocket.getInputStream());
            out =new DataOutputStream( clientSocket.getOutputStream());
            this.start();
        }catch(IOException e)  {System.out.println("Connection:"+e.getMessage());}
    }

    public void run(){
        try {
            while (true) {
                String data = in.readUTF();
                String command[] = data.split(" ");
                int values[] = new int[command.length-1];
                    for(int i = 0; i < values.length; i++) 
                        values[i] = Integer.parseInt(command[i+1]);
                if(command[0].equals("exit")) {
                    out.writeUTF("Encerrando a Execução...");
                }else if(command[0].equals("add")) {
                    String result = String.valueOf(Calculadora.add(values));
                    out.writeUTF(result);
                }else if(command[0].equals("sub")) {
                    String result = String.valueOf(Calculadora.sub(values));
                    out.writeUTF(result);
                }else if(command[0].equals("mul")) {
                    String result = String.valueOf(Calculadora.mul(values));
                    out.writeUTF(result);
                }else if(command[0].equals("div")) {
                    String result = String.valueOf(Calculadora.div(values));
                    if(result.equals("-1")){
                        out.writeUTF("Divisão por 0!");
                    } else {
                        out.writeUTF(result);
                    }
                }else{
                    out.writeUTF("Comando Invalido!");
                }
            }
        }catch(EOFException e) {System.out.println("EOF:"+e.getMessage());}
        catch(IOException e)  {System.out.println("Connection:"+e.getMessage());}
        finally { try {clientSocket.close();}catch (IOException e){/*close failed*/}}
    }
}

