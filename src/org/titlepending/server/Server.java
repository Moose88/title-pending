package org.titlepending.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public static void main(String[] args) throws IOException{

        ServerSocket listener = new ServerSocket(8000);

        while (true){
            Socket socket = listener.accept();
            try{
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(),true);
                    out.println("2");
                }
            catch (IOException e){
                e.printStackTrace();
                socket.close();
            }finally {
                socket.close();
            }
        }
    }
}
