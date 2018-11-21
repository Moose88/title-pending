package org.titlepending.client;

import org.titlepending.shared.ClientThread;
import org.titlepending.shared.Nuntius;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Updates {
    private ConcurrentLinkedQueue<Nuntius> queue;
    private static Updates updates;
    private int id;
    private Updates(){
        queue = new ConcurrentLinkedQueue<>();
    }
    ClientThread thread;
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

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public void setThread(ClientThread thread) {this.thread = thread;}

    public ClientThread getThread(){return thread;}
}
