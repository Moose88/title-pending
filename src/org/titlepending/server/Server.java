package org.titlepending.server;

import org.titlepending.client.Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Server {

    private static final int PORT = 8000;
    private static final int PLIMIT = 8;
    private static ConcurrentLinkedQueue<Player> players= new ConcurrentLinkedQueue<>();

    public static void main(String[] args) throws IOException{


        System.out.println("Game server started");

        new Handler().start();

        while(true){
            // Game logic goes here
        }

    }

    private static class  Handler extends Thread{
        ServerSocket listener;
        public Handler() throws IOException{
            listener = new ServerSocket(PORT);
            System.out.println("Listening for new clients.");
            try{
                while(true){
                    new Clients(listener.accept()).start();
                }
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                listener.close();
            }
        }
    }

    private static class Clients extends Thread{
        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        private Player player;
        public Clients(Socket socket) throws IOException{
            this.socket=socket;
            System.out.println("creating output stream.");
            out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("creating input stream.");
            in = new ObjectInputStream(socket.getInputStream());


        }

        public void run(){
            System.out.println("New player connected.");
            try{
                System.out.println("Attempting to create player.");
                player = new Player(ThreadLocalRandom.current().nextInt(),out,in);
                players.add(player);
                System.out.println("Player created attempting to send response to client.");
                out.write(2);
                out.flush();
                System.out.println("Connected sending signal to move to playing state.");


            } catch (IOException e){
                e.printStackTrace();
            }finally {
                   players.remove(player);
                try{
                    in.close();
                    out.close();
                    socket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }



        }
    }

}

