package org.titlepending.client;

import org.titlepending.shared.ClientThread;
import org.titlepending.shared.Directive;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Updates {
    private ConcurrentLinkedQueue<Directive> queue;
    private static Updates updates;
    private Updates(){
        queue = new ConcurrentLinkedQueue<>();
    }
    private ClientThread thread;
    public static Updates getInstance(){
        if(updates == null){
            updates = new Updates();
        }
        return updates;
    }

    public void addToQueue(Directive cmd){ queue.add(cmd);}

    public ConcurrentLinkedQueue<Directive> getQueue() {
        return queue;
    }

    public void setThread(ClientThread thread) {this.thread = thread;}

    public ClientThread getThread(){return thread;}
}
