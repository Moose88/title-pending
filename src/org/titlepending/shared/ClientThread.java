package org.titlepending.shared;


import org.titlepending.client.Client;
import org.titlepending.client.Updates;
import org.titlepending.server.Server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread extends Thread{
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean isServer;
    private int clientId;
    private boolean done;

    public ClientThread (Socket socket, boolean isServer) throws IOException {
        this.socket = socket;
        this.isServer = isServer;
        this.done=false;
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
        try{
            while (!done){
                try{
                    CommandObject input = (CommandObject) in.readObject();
                    if(isServer){
                        //code to add to server queue
                        Server.commands.add(input);
                    }else {
                        Updates.getInstance().addToQueue(input);
                    }
                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                }catch (EOFException e){
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            System.out.println("Disconnecting Players");
            try {
                socket.close();
                if(isServer)
                    Server.players.remove(this);
            }catch (IOException e){
                e.printStackTrace();
            }
        }


    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }

    public void sendCommand(CommandObject cmd) throws IOException{
        out.writeObject(cmd);
        out.flush();
    }

    public void stopThread(){done=true;}
}