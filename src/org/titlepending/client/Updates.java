package org.titlepending.client;

import org.titlepending.shared.Nuntius;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Updates {
    private ConcurrentLinkedQueue<Nuntius> queue;
    private static Updates updates;
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
}
