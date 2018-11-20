package org.titlepending.shared;


import org.titlepending.client.Client;
import org.titlepending.client.Updates;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientThread extends Thread{
    private Socket socket;
    private ObjectInputStream in;
    private boolean isServer;

    public ClientThread (Socket socket, boolean isServer) throws IOException {
        this.socket = socket;
        this.isServer = isServer;
    }

    public void run(){
        System.out.println("New player connected.");
        boolean done = false;
        try{
            in = new ObjectInputStream(socket.getInputStream());
            while (!done){
                try{
                    Nuntius input = (Nuntius) in.readObject();
                    if(isServer){
                        //code to add to server queue
                    }else
                        Updates.getInstance().addToQueue(input);
                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            System.out.println("Disconnecting Players");
            try {
                in.close();
                socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}