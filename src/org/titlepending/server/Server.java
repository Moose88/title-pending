package org.titlepending.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.logging.Handler;

public class Server {

    private static final int PORT = 8000;

    private static HashSet<PrintWriter> writers = new HashSet<>();

    public static void main(String[] args) throws IOException{

        ServerSocket listener = new ServerSocket(PORT);

        System.out.println("Game server started");

        try{
            while (true){
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
//        while (true){
//            Socket socket = listener.accept();
//
//            try{
//                PrintWriter out =
//                        new PrintWriter(socket.getOutputStream(),true);
//                    out.println("2");
//                }
//            catch (IOException e){
//                e.printStackTrace();
//                socket.close();
//            }finally {
//                socket.close();
//            }
        }

    private static class Handler extends Thread{
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public Handler(Socket socket){
            this.socket=socket;
        }

        public void run(){
            try{
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(),true);
                out.println("2");
                System.out.println("Connected sending signal to move to playing state");
                writers.add(out);
            } catch (IOException e){
                e.printStackTrace();
            }finally {

                if(out != null)
                    writers.remove(out);
                try{
                    socket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }



        }
    }

}

