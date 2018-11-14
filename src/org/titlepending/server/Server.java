package org.titlepending.server;

import org.titlepending.shared.ClientThread;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {

    private static final int PORT = 8000;
    private static final int PLIMIT = 8;

    //TODO change to concurrent
    private static List<Socket> players = new CopyOnWriteArrayList<>();

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
                Socket s = listener.accept();
                players.add(s);
                new ClientThread(s).start();
                ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                out.write(2); out.flush();
                if(players.size()==1){ done=true;}
            }
            System.out.println("Maximum players reached");
            listener.close();

        }
    }

}

