package org.titlepending.server;


import org.lwjgl.Sys;
import org.titlepending.client.Client;
import org.titlepending.client.Updates;
import org.titlepending.shared.ClientThread;
import org.titlepending.shared.CmdProcessor;
import org.titlepending.shared.Directive;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Server {

    public static final boolean DEBUG = true;
    public static List<ClientThread> players = new CopyOnWriteArrayList<>();
    private static final int PORT = 8000;
    private static final int PLIMIT = 8;
    private static boolean inLobby;
    private static boolean inGame;
    public static ConcurrentLinkedQueue<Directive> commands = new ConcurrentLinkedQueue<>();

    public static final int CONNECTSTATE = 1;
    public static final int PLAYINGSTATE = 2;
    public static final int LOADSTATE = 0;
    public static final int GAMEOVERSTATE = 4;
    public static final int MAINMENUSTATE = 5;
    public static final int STATSSTATE = 6;
    public static final int OPTIONSMENUSTATE = 7;
    public static final int LOBBYSTATE = 8;
    public static final int REJECTSTATE= 9;

    private static int[][] playingShips;

    public static void main(String[] args) throws IOException{
        if(Server.DEBUG){
        InetAddress Ip= InetAddress.getLocalHost();
        Enumeration a = NetworkInterface.getNetworkInterfaces();
        while (a.hasMoreElements()){
            NetworkInterface n = (NetworkInterface) a.nextElement();
            Enumeration b = n.getInetAddresses();
            while (b.hasMoreElements()){
                InetAddress i = (InetAddress) b.nextElement();
                System.out.println(i);
            }
        }
            System.out.println("My IP: "+Ip.getLocalHost().getHostAddress() + "\nHost Name "+Ip.getHostName());
        }


        if(Server.DEBUG)
            System.out.println("Game server started");

        inLobby=true;
        inGame=false;
        new Handler().start();
        CmdProcessor processor = new CmdProcessor(true);

        if(Server.DEBUG){
            System.out.println("Starting lobby state");
        }

        // Timer for the lobby

        // Check that if current players say ready OR timer == 0, move into playing state.
        // Once we transition, the server will need to assign a finalShip array to each
        // player ID.

        /**
         * @param lobbyTimer:
         * @param curPlayers:
         */

        int lobbyTimer = 180000;
        if(DEBUG)
            lobbyTimer = 10000;
        int curPlayers = players.size();
        int curReady = 0;
        Directive cmd;

        while(lobbyTimer >= 0){
            for (ClientThread thread : players){
                Directive timeUpdate = new Directive();
                timeUpdate.setTime(lobbyTimer);
                if(Server.DEBUG) System.out.println("Timer is: " + timeUpdate.getTime());

                try{
                    thread.sendCommand(timeUpdate);

                }catch (SocketException e){
                    if(Server.DEBUG)
                        System.out.println("Attempted to write to client that no longer exists");
                    players.remove(thread);
                    thread.stopThread();
                }
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            lobbyTimer -= 1000;
            if(curPlayers < players.size() && curPlayers > 1){
                lobbyTimer += 30000;
                curPlayers = players.size();
                if(lobbyTimer > 180000)
                    lobbyTimer = 180000;
            }
            if(commands.size()>0) {
                cmd = commands.poll();
                /*
                    do stuff with cmd here
                 */
                processor.processCommand(cmd);
                if(cmd.getready())
                    curReady++;
                else if(!cmd.getready())
                    curReady--;

                System.out.println("Player " + cmd.getId() + " gives a ready check of:  " + cmd.getready());
                System.out.println("Total number of players: " + curPlayers);
                System.out.println("Number of players ready: " + curReady);

                if(curReady == curPlayers && curPlayers >= 2) {
                    System.out.println("We are ready to play the game");
                    //cmd.setStateTransition(PLAYINGSTATE);

                    break;
                }

            }

        }

        // Send the players into the game and have the server take their
        // finalShip arrays and assign them to each id for applicable
        // game logic.

        Directive state = new Directive();
        if(curPlayers < 2){
            // Return the client to the lobby state and say not enough players
            if(DEBUG) System.out.println("Not enough players");
            state.setStateTransition(LOBBYSTATE);
            for(ClientThread thread : players){
                thread.sendCommand(state);
            }
            // TODO: Here, we should restart the server somehow

        } else {
            if(DEBUG) System.out.println("Lets play!");
            state.setStateTransition(PLAYINGSTATE);
            for(ClientThread thread : players){
                thread.sendCommand(state);
            }
        }


        // get a command to receive the id of the client and finalShip array to assign to the server
        // send a command to proceed to the play state with the rendered ship image

        // Setup the client id's to their ship arrays here


        inGame =true;
        inLobby = false;

        if(Server.DEBUG)
            System.out.println("Starting game loop");

        while(inGame){
            // Game logic goes here

            for(ClientThread thread : players) {
                //do updates

            }

            while (!commands.isEmpty()){
                cmd = commands.poll();
                processor.processCommand(cmd);

            }
        }

    }

    private static class  Handler extends Thread{
        ServerSocket listener;
        long startTime;
        public Handler(){
            startTime = System.currentTimeMillis();
        }

        public void run(){
            try {
                listener = new ServerSocket(PORT);
            }catch (IOException e){
                e.printStackTrace();
            }

            boolean done = false;
            if(Server.DEBUG)
                System.out.println("Listening for new clients.");

            while(!done){
                Socket s = null;
                try {
                    s = listener.accept();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if(Server.DEBUG)
                    System.out.println("New connection received\nSetting up client thread");
                ClientThread temp = null;
                try {
                    temp = new ClientThread(s, true);
                }catch (IOException e){
                    e.printStackTrace();
                }
                int id = ThreadLocalRandom.current().nextInt(0,1000000);
                System.out.println("Assigned ID: " + id);
                if(temp != null) temp.setClientId(id);

                if(Server.DEBUG) System.out.println("Starting thread with id: "+id);

                if(temp != null) temp.start();

                if(Server.DEBUG) System.out.println("Building initial command");

                Directive cmd = new Directive();
                cmd.setId(id);
                cmd.setStateTransition(LOBBYSTATE);

                if(Server.DEBUG) System.out.println("Attempting to send command to client");
                if(Server.DEBUG) System.out.println("Number of connected players: "+ players.size());
                if(temp!=null
                        && players.size() < PLIMIT
                        && !inGame){
                    try{
                        temp.sendCommand(cmd);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }else if(temp !=null) {
                    if(Server.DEBUG)
                        System.out.println("Rejecting connection players max");
                    cmd.setStateTransition(MAINMENUSTATE);
                    try{
                        temp.sendCommand(cmd);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    temp.stopThread();
                    temp = null;
                }

                if(Server.DEBUG)
                    System.out.println("Adding thread to players list");
                if(Server.DEBUG) System.out.println("Number of connected players: "+ players.size());
                if(temp != null)
                    players.add(temp);

                if(players.size()==0 && !inLobby && !inGame){ done=true;}
            }
            System.out.println("Maximum players reached");
            try {
                listener.close();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

}

