package org.titlepending.client;

import org.titlepending.shared.Nuntius;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Updates {
    private ConcurrentLinkedQueue<Nuntius> queue;
    private static Updates updates;
    private Socket socket;
    private ObjectOutputStream out;
    private Updates(){
        queue = new ConcurrentLinkedQueue<>();
    }

    public static Updates getInstance(){
        if(updates == null){
            updates = new Updates();
        }
        return updates;
    }

    public void addToQueue(Nuntius cmd){ queue.add(cmd);}

    public ConcurrentLinkedQueue<Nuntius> getQueue() {
        return queue;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket(){return socket;}

    public void createOutPutStream(){
        if(socket==null)
            return;

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public ObjectOutputStream getOut() {
        return out;
    }
}
