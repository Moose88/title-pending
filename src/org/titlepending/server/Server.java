package org.titlepending.server;

import org.lwjgl.Sys;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Server {

    private static final int PORT = 8000;
    private static final int PLIMIT = 8;

    //TODO change to concurrent
    private static final List<Socket> players = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws IOException{

        System.out.println("Game server started");

        new Handler().start();

        while(true){
            // Game logic goes here

            if(players.size()!=8){

            }else{

            }
        }

    }

    private static class  Handler extends Thread{
        ServerSocket listener;
        public Handler() throws IOException{
            listener = new ServerSocket(PORT);
            boolean done = false;
            System.out.println("Listening for new clients.");

            while(!done){
                new Players(listener.accept()).start();
                if(players.size()==8)
                    done=true;
            }
            System.out.println("Maximum players reached");
            listener.close();

        }
    }

    private static class Players extends Thread{
        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        private Player player;
        public Players(Socket socket) throws IOException{
            this.socket=socket;
            players.add(socket);
        }

        public void run(){
            System.out.println("New player connected.");
            try{
                out = new ObjectOutputStream(socket.getOutputStream());
                out.write(2);
                out.flush();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                System.out.println("Disconnecting Players");
                try {
                     out.close();
                     socket.close();
                     players.remove(socket);
                }catch (IOException e){
                     e.printStackTrace();
                }
            }

        }
    }

}

