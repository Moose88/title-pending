package org.titlepending.client;

import org.titlepending.server.ServerObjects.Ship;
import org.titlepending.shared.ClientThread;
import org.titlepending.shared.CommandObject;
import org.titlepending.shared.Initializer;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Updates {
    private ConcurrentLinkedQueue<CommandObject> queue;
    private static Updates updates;
    private ArrayList<Ship> Ships;
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

    public void addToQueue(CommandObject cmd){ queue.add(cmd);}

    public ConcurrentLinkedQueue<CommandObject> getQueue() {
        return queue;
    }

    public void setShips(ArrayList<Ship> Ships){this.Ships = Ships;}

    public void setThread(ClientThread thread) {this.thread = thread;}

    public ArrayList<Ship> getShips(){return Ships;}

    public ClientThread getThread(){return thread;}
}
