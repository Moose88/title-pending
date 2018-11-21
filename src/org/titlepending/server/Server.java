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
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Server {

    public static final boolean DEBUG = true;
    //TODO change to concurrent
    private static List<ClientThread> players = new CopyOnWriteArrayList<>();
    private static final int PORT = 8000;
    private static final int PLIMIT = 8;
    public static List<Nuntius> commands = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws IOException{

        if(Server.DEBUG)
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
            if(Server.DEBUG)
                System.out.println("Listening for new clients.");

            while(!done){
                long elapsedTime = System.currentTimeMillis() -startTime;
                Socket s = listener.accept();
                if(Server.DEBUG)
                    System.out.println("New connection received\nSetting up client thread");
                ClientThread temp = new ClientThread(s,true);
                int id = ThreadLocalRandom.current().nextInt();
                temp.setId(id);

                if(Server.DEBUG)
                    System.out.println("Starting thread with id: "+id);
                temp.start();

                if(Server.DEBUG)
                    System.out.println("Building initial command");

                Nuntius cmd = new Nuntius();
                cmd.setId(id);
                cmd.setStateTransition(Client.PLAYINGSTATE);

                if(Server.DEBUG)
                    System.out.println("Attempting to send command to client");
                temp.sendCommand(cmd);


                if(Server.DEBUG)
                    System.out.println("Adding thread to players list");

                players.add(temp);

                if(players.size()==0 && elapsedTime >= timer){ done=true;}
            }
            System.out.println("Maximum players reached");
            listener.close();

        }
    }

}

