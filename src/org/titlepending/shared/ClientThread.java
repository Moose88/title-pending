package org.titlepending.shared;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientThread extends Thread{
    private Socket socket;
    private ObjectInputStream in;
    public ClientThread (Socket socket) throws IOException {
        this.socket=socket;
    }

    public void run(){
        System.out.println("New player connected.");
        boolean done = false;
        try{
            in = new ObjectInputStream(socket.getInputStream());
            while (!done){
                try{
                    Object input = in.readObject();
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