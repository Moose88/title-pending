package org.titlepending.server;

import org.titlepending.client.Client;
import org.titlepending.shared.ClientThread;
import org.titlepending.shared.CmdProcessor;
import org.titlepending.shared.Directive;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
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
    private static final int PLIMIT = 2;
    private static boolean inLobby;
    public static ConcurrentLinkedQueue<Directive> commands = new ConcurrentLinkedQueue<>();

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
        new Handler().start();
        CmdProcessor processor = new CmdProcessor(true);

        if(Server.DEBUG){
            System.out.println("Starting lobby state");
        }
        int lobbyTimer = 180000;
        long startTIme = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTIme < lobbyTimer){
            for (ClientThread thread : players){
                Directive timeUpdate = new Directive();
                timeUpdate.setLobbyStartTime(startTIme);
                timeUpdate.setTime(startTIme);
                thread.sendCommand(timeUpdate);
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        inLobby = false;

        if(Server.DEBUG)
            System.out.println("Starting game loop");

        while(true){
            // Game logic goes here
            for(ClientThread thread : players)
                //do updates

            while (!commands.isEmpty()){
                Directive cmd = commands.poll();
                processor.processCommand(cmd);

            }

            if(players.size()!=PLIMIT){

            }else{

            }
        }

    }

    private static class  Handler extends Thread{
        ServerSocket listener;
        private int timer;
        long startTime;
        public Handler(){
            timer = 180000;
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
                long elapsedTime = System.currentTimeMillis() -startTime;
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
                if(temp != null) temp.setClientId(id);

                if(Server.DEBUG) System.out.println("Starting thread with id: "+id);

                if(temp != null) temp.start();

                if(Server.DEBUG) System.out.println("Building initial command");

                Directive cmd = new Directive();
                cmd.setId(id);
                cmd.setStateTransition(Client.LOBBYSTATE);

                if(Server.DEBUG) System.out.println("Attempting to send command to client");
                if(Server.DEBUG) System.out.println("Number of connected plauers: "+players.size());
                if(temp!=null && players.size() < PLIMIT){
                    try{
                        temp.sendCommand(cmd);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }else if(temp !=null) {
                    if(Server.DEBUG)
                        System.out.println("Rejecting connection players max");
                    cmd.setStateTransition(Client.MAINMENUSTATE);
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
                if(temp != null)
                    players.add(temp);

                if(players.size()==0 && !inLobby){ done=true;}
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

