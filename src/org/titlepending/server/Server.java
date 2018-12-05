package org.titlepending.server;


import jig.Entity;
import org.titlepending.entities.Ship;
import org.titlepending.entities.ShipFactory;
import org.titlepending.shared.ClientThread;
import org.titlepending.shared.CmdProcessor;
import org.titlepending.shared.Directive;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Server {

    public static final boolean DEBUG = true;
    public static List<ClientThread> players = new CopyOnWriteArrayList<>();
    private static final int PORT = 8000;
    private static final int PLIMIT = 8;
    private static boolean inLobby;
    private static boolean inGame;
    public static ConcurrentLinkedQueue<Directive> commands = new ConcurrentLinkedQueue<>();

    public static final int LOADSTATE = 0;
    public static final int CONNECTSTATE = 1;
    public static final int PLAYINGSTATE = 2;
    public static final int WAITINGSTATE = 3;
    public static final int GAMEOVERSTATE = 4;
    public static final int MAINMENUSTATE = 5;
    public static final int STATSSTATE = 6;
    public static final int OPTIONSMENUSTATE = 7;
    public static final int LOBBYSTATE = 8;
    public static final int REJECTSTATE= 9;
    public static final int r1 = 896;
    public static final int r2 = 1824;
    public static final int r3 = 3200;
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
            //System.out.println("My IP: "+Ip.getLocalHost().getHostAddress() + "\nHost Name "+Ip.getHostName());
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

        int lobbyTimer;
        if(DEBUG) {
            lobbyTimer = 10000;
        }else {
            lobbyTimer = 180000;
        }

        int curReady = 0;
        Directive cmd;
        while(lobbyTimer > 0){
            if(DEBUG) System.out.println("Timer is: " + lobbyTimer+"\nCurrent players: "+players.size());
            for (ClientThread player : players){
                Directive timeUpdate = new Directive();
                timeUpdate.setTime(lobbyTimer);

                try{
                    player.sendCommand(timeUpdate);

                }catch (SocketException e){
                    if(Server.DEBUG)
                        System.out.println("Attempted to write to client that no longer exists.");
                    players.remove(player);
                    player.stopThread();
                }
            }
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            if(players.size() != 0)
                lobbyTimer -= 1000;

            if(commands.size()>0) {
                cmd = commands.poll();
                /** do stuff with cmd here **/
                processor.processCommand(cmd);
                if(cmd.getready())
                    curReady++;
                else if(!cmd.getready())
                    curReady--;
                if(DEBUG) {
                    System.out.println("Player " + cmd.getId() + " gives a ready check of:  " + cmd.getready());
                    System.out.println("Total number of players: " + players.size()+"\nNumber of ready players: "+curReady);
                }


            }
            /** maintain active lobby if only one player is in **/
            if(players.size()==1 && lobbyTimer <= 30000)
                if(DEBUG)
                    lobbyTimer = 10000;
                else
                    lobbyTimer += 30000;

            if(curReady == players.size()
                    && players.size() >= 2) {
                if(DEBUG)
                    System.out.println("We are ready to play the game");
                lobbyTimer = 0;
            }

        }


        /** final timer sent to client with transition state **/
        cmd = new Directive();
        cmd.setStateTransition(WAITINGSTATE);
        cmd.setTime(lobbyTimer);
        for(ClientThread player : players)
            try{
                player.sendCommand(cmd);
            }catch (SocketException e){
                if(DEBUG)
                    System.out.println("Attempted to write to player that doesn't exist.");
                player.stopThread();
            }


        // get a command to receive the id of the client and finalShip array to assign to the server
        // send a command to proceed to the play state with the rendered ship image


        // Setup the client id's to their ship arrays here


        /** Give 30 seconds for each client to send its ship data **/
        int timer = 30000;
        while(commands.size()!=players.size()){
            /** send all connected clients to reject state **/
            if(timer <= 0){
                cmd = new Directive();
                cmd.setStateTransition(REJECTSTATE);
                for (ClientThread player : players){
                    player.sendCommand(cmd);
                    player.stopThread();
                }
            }
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            timer -= 1000;
        }

        ArrayList<Ship> ships = new ArrayList<>();
        double degree = Math.toRadians((float)360/players.size());
        double radAlpha;
        if(players.size()<=4){
            radAlpha = r2;
        }else{
            radAlpha = r3;
        }
        int playerNo = 1;
        Entity.setCoarseGrainedCollisionBoundary(Entity.CIRCLE);

        while(!commands.isEmpty()){
            /** construct player ships here **/
            cmd = commands.poll();
            if(DEBUG){
                System.out.println("Received from Client: "+cmd.getId());
                System.out.println("Hull: "+cmd.getShip()[0]);
                System.out.println("Sails: "+cmd.getShip()[1]);
                System.out.println("Cannon: "+cmd.getShip()[2]);
                System.out.println("Captain: "+cmd.getShip()[3]);
            }
            double shipX = 3200 + (radAlpha*Math.cos(degree*playerNo));
            double shipY = 3200 + (radAlpha*Math.sin(degree*playerNo));
            if(DEBUG) System.out.println("Ship x: "+shipX+"\nShip y: "+shipY);
            ships.add(ShipFactory.getInstance().createNewPlayerShip(shipX, shipY, cmd.getShip(), cmd.getId()));
            playerNo +=1;
        }

        if(DEBUG) System.out.println("Generated "+ships.size()+" ships.");

        for(ClientThread player : players){
            cmd = new Directive();
            cmd.setShips(ships);
            cmd.setStateTransition(PLAYINGSTATE);
            try{
                player.sendCommand(cmd);
            }catch (SocketException e){
                if(DEBUG)
                    System.out.println("Attempted to write to client that no longer exists.");
                player.stopThread();
            }
        }

        inGame =true;
        inLobby = false;

        if(DEBUG)
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
                if(DEBUG)
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

                if(DEBUG) System.out.println("Starting thread with id: "+id);

                if(temp != null) temp.start();

                if(DEBUG) System.out.println("Building initial command");

                Directive cmd = new Directive();
                cmd.setId(id);
                cmd.setStateTransition(LOBBYSTATE);

                if(DEBUG) System.out.println("Attempting to send command to client");

                if(temp!=null
                        && players.size() < PLIMIT
                        && !inGame){
                    try{
                        temp.sendCommand(cmd);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }else if(temp !=null) {
                    if(DEBUG)
                        System.out.println("Rejecting connection players max");
                    cmd.setStateTransition(REJECTSTATE);
                    try{
                        temp.sendCommand(cmd);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    temp.stopThread();
                    temp = null;
                }

                if(DEBUG)
                    System.out.println("Adding thread to players list");
                if(DEBUG) System.out.println("Number of connected players: "+ players.size());
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

