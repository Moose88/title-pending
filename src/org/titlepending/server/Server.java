package org.titlepending.server;

import org.lwjgl.Sys;
import org.titlepending.client.Client;
import org.titlepending.client.Updates;
import org.titlepending.client.states.PlayingState;
import org.titlepending.shared.ClientThread;
import org.titlepending.shared.Nuntius;

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
        private int timer = 180000;
        long startTime = System.currentTimeMillis();
        public Handler() throws IOException{
            listener = new ServerSocket(PORT);
            boolean done = false;
            System.out.println("Listening for new clients.");
            while(!done){
                long elapsedTime = System.currentTimeMillis() -startTime;
                Socket s = listener.accept();
                players.add(s);
                new ClientThread(s,true).start();
                System.out.println("Tell the client to enter Lobby state");
                // Go to Lobby here
                // Send a Nuntius (message) to the Client to transition states

                Nuntius output = new Nuntius();
                output.setStateTransition(Client.PLAYINGSTATE);
                System.out.println("attempting to send message");
                ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                out.writeObject(output);
                out.flush();
                System.out.println("Message sent");
                if(players.size()==0 && elapsedTime >= timer){ done=true;}
            }
            System.out.println("Maximum players reached");
            listener.close();

        }
    }

}

