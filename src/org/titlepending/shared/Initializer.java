package org.titlepending.shared;

import org.titlepending.server.ServerObjects.Ship;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * object that is sent across TCP/IP socket for communication in client server pair.
 */
public class Initializer extends CommandObject implements Serializable {
    private int stateTransition;
    private boolean ready;
    private int id;
    private int time;
    private int[] ship;
    private ArrayList<Ship> Ships;

    public Initializer(int id){
        super(id);
    }

    public void setStateTransition(int stateTransition){this.stateTransition = stateTransition;}

    public int getStateTransition(){return stateTransition;}

    public void setReady(boolean ready){this.ready = ready;}
    public void setId(int id){this.id = id;}
    public void setTime(int time) {this.time = time;}
    public void setShip(int[] ship){this.ship=ship;}
    public void setShips(ArrayList<Ship> Ships){this.Ships = Ships;}

    public boolean getready(){return ready;}
    public int getId(){return id;}
    public int getTime(){return time;}
    public int[] getShip(){return ship;}
    public ArrayList<Ship> getShips(){return Ships;}

}
