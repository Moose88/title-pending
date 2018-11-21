package org.titlepending.shared;


import org.titlepending.client.Client;
import org.titlepending.client.Updates;
import org.titlepending.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread extends Thread{
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean isServer;
    private int id;

    public ClientThread (Socket socket, boolean isServer) throws IOException {
        this.socket = socket;
        this.isServer = isServer;
        if(Client.DEBUG || Server.DEBUG)
            System.out.println("Setting up output stream");
        out = new ObjectOutputStream(socket.getOutputStream());
        if(Client.DEBUG || Server.DEBUG)
            System.out.println("Output stream established\nSetting up input stream ");
        in = new ObjectInputStream(socket.getInputStream());
        if(Client.DEBUG || Server.DEBUG)
            System.out.println("Input stream established");
    }

    public void run(){
        boolean done = false;
        try{
            while (!done){
                try{
                    Nuntius input = (Nuntius) in.readObject();
                    if(isServer){
                        //code to add to server queue
                        if(Server.DEBUG) {
                            System.out.println("Receiving command on Server");
                        }
                        Server.commands.add(input);
                    }else {
                        if(Client.DEBUG) {
                            System.out.println("Receiving command on  Client");
                        }
                        Updates.getInstance().addToQueue(input);
                    }
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
                out.close();
                socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }


    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return id;
    }

    public void sendCommand(Nuntius cmd) throws IOException{
        out.writeObject(cmd);
        out.flush();
    }
}