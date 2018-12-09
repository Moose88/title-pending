package org.titlepending.server;


import org.titlepending.server.ServerObjects.Ball;
import org.titlepending.server.ServerObjects.GameObject;
import org.titlepending.server.ServerObjects.Ship;
import org.titlepending.entities.ShipFactory;
import org.titlepending.shared.*;

import java.io.IOException;
import java.net.*;
import java.util.*;
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
    public static ConcurrentLinkedQueue<CommandObject> commands = new ConcurrentLinkedQueue<>();

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


        if(Server.DEBUG){
            System.out.println("Starting lobby state");
        }

        // Timer for the lobby


        int lobbyTimer;
        if(DEBUG) {
            lobbyTimer = 10000;
        }else {
            lobbyTimer = 180000;
        }

        int curReady = 0;

        Initializer cmd;

        while(lobbyTimer > 0){
            if(DEBUG) System.out.println("Timer is: " + lobbyTimer+"\nCurrent players: "+players.size());
            for (ClientThread player : players){
                Initializer timeUpdate = new Initializer(0);
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
                cmd = (Initializer) commands.poll();
                /** do stuff with cmd here **/
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
        cmd = new Initializer(0);
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
                cmd = new Initializer(0);
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

        HashMap<Integer, Ship> ships = new HashMap();
        double degree = Math.toRadians((float)360/players.size());
        double radAlpha;
        if(players.size()<=4){
            radAlpha = r2;
        }else{
            radAlpha = r3;
        }
        int playerNo = 1;
        //Entity.setCoarseGrainedCollisionBoundary(Entity.CIRCLE);

        while(!commands.isEmpty()){
            /** construct player ships here **/
            cmd = (Initializer) commands.poll();
            if(DEBUG){
                System.out.println("Received from Client: "+cmd.getId());
                System.out.println("Hull: "+cmd.getShip()[0]);
                System.out.println("Sails: "+cmd.getShip()[1]);
                System.out.println("Cannon: "+cmd.getShip()[2]);
                System.out.println("Captain: "+cmd.getShip()[3]);
            }
            double shipX;
            double shipY;
            if(DEBUG){
                shipX = 1000;
                shipY = 1000;
            }else{
                shipX =(3200 + (radAlpha*Math.cos(degree*playerNo)));
                shipY = (3200 + (radAlpha*Math.sin(degree*playerNo)));
            }
            if(DEBUG) System.out.println("Ship x: "+shipX+" Ship y: "+shipY);
            Ship temp =ShipFactory.getInstance().createNewPlayerShip(shipX, shipY, cmd.getShip(), cmd.getId());
            if(DEBUG) System.out.println("Temp x: "+temp.getX()+" Temp y: "+temp.getY());
            ships.put(cmd.getId(),temp);
            playerNo +=1;

        }

        if(DEBUG) System.out.println("Generated "+ships.size()+" ships.");


        for(ClientThread player : players){
            cmd = new Initializer(0);
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

        Action actions;
        long prevTime= System.currentTimeMillis();
        timer = 0;
        boolean updateAll = false;
        HashMap<Integer,Ball>ballHashMap = new HashMap<>();
        while(inGame){
            //Empty command queue
            while (!commands.isEmpty()){
                actions = (Action) commands.poll();
                Ball ballUpdater;
                Ship shipUpdater;
                if(!actions.getCannonBall()) {
                    shipUpdater = ships.get(actions.getId());
                    if(DEBUG)
                        System.out.println("Updating ship "+actions.getId()+" vx: "+actions.getVx()+" vy: "+actions.getVy());
                    shipUpdater.setVelocity(actions.getVx(),actions.getVy());
                    shipUpdater.setHeading(actions.getHeading());
                    shipUpdater.setUpdated(true);
                }else{
                    if(!ballHashMap.containsKey(actions.getBallID())){
                        ballUpdater = new Ball(
                                actions.getX(),
                                actions.getY(),
                                actions.getVx(),
                                actions.getVy(),
                                actions.getId(),
                                actions.getTtl()
                        );
                        ballHashMap.put(actions.getId(),ballUpdater);
                    }else {
                        if(actions.getIsDead()){
                            ballHashMap.remove(actions.getId());
                        }
                    }
                }

            }

            // Calc Delta Preserve Prev Time
            long curTime = System.currentTimeMillis();
            if(curTime - prevTime < 30 ){
                try {
                    Thread.sleep(30-(curTime-prevTime));
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                curTime = System.currentTimeMillis();
            }
            long delta = curTime - prevTime;


            Iterator i = ships.entrySet().iterator();

            //update ships
            while (i.hasNext()){
                Map.Entry pair = (Map.Entry)i.next();
                ships.get(pair.getKey()).update((int)delta);

            }
            i = ballHashMap.entrySet().iterator();
            //update cannon balls;
            while (i.hasNext()){
                Map.Entry pair = (Map.Entry)i.next();
                Ball updateBall = ballHashMap.get(pair.getKey());
                updateBall.update((int)delta);
                if(updateBall.getTtl() <=0){
                    actions = new Action(0);
                    actions.setBallID(updateBall.getBallID());
                    actions.setDead(true);
                    updateAll(actions);
                    i.remove();

                }
            }
            // Sleep if we havn't done enough work


            timer -= (int) delta;
            if(timer<=0){
                updateAll = true; timer =150;
            }else {
                updateAll=false;
            }
            //Update all ships on client side.
            i = ships.entrySet().iterator();
            while (i.hasNext()){
                Map.Entry pair = (Map.Entry) i.next();
                if(updateAll || ships.get(pair.getKey()).getUpdated()){
                    actions = new Action(0);
                    Ship updatedShip = ships.get(pair.getKey());
                    actions.setUpdatedShip(updatedShip.getPlayerID());
                    actions.setVy(updatedShip.getVy());
                    actions.setVx(updatedShip.getVx());
                    actions.setX(updatedShip.getX());
                    actions.setY(updatedShip.getY());
                    actions.setHeading(updatedShip.getHeading());
                    updateAll(actions);
                }
            }
            i = ballHashMap.entrySet().iterator();
            //update all cannon balls on client side.
            while (i.hasNext()){
                Map.Entry pair = (Map.Entry) i.next();
                if(updateAll){
                    Ball updateBall = ballHashMap.get(pair.getKey());
                    actions = new Action(0);
                    actions.setBallID(updateBall.getBallID());
                    actions.setCannonBall(true);
                    actions.setVx(updateBall.getVx());
                    actions.setVy(updateBall.getVy());
                    actions.setX(updateBall.getX());
                    actions.setY(updateBall.getY());
                    updateAll(actions);
                }
            }
            prevTime = curTime;
        }

    }

    private static void updateAll(CommandObject action)throws IOException{
        for(ClientThread player : players){
            player.sendCommand(action);
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

                Initializer cmd = new Initializer(0);
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

