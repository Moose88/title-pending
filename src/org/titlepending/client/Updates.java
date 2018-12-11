package org.titlepending.client;

import org.titlepending.server.ServerObjects.Ship;
import org.titlepending.server.ServerObjects.Turret;
import org.titlepending.shared.ClientThread;
import org.titlepending.shared.CommandObject;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Updates {
    private ConcurrentLinkedQueue<CommandObject> queue;
    private static Updates updates;
    private HashMap<Integer, Ship> Ships;
    private HashMap<Integer, Turret> turrets;
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

    public void setTurrets(HashMap<Integer, Turret> turrets){this.turrets = turrets;}

    public void setShips(HashMap<Integer, Ship> Ships){this.Ships = Ships;}

    public void setThread(ClientThread thread) {this.thread = thread;}

    public HashMap<Integer, Turret> getTurrets(){return turrets;}

    public HashMap<Integer, Ship> getShips(){return Ships;}

    public ClientThread getThread(){return thread;}
}
