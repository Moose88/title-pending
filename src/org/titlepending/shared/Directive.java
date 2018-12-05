package org.titlepending.shared;

import org.titlepending.entities.Ship;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * object that is sent across TCP/IP socket for communication in client server pair.
 */
public class Directive implements Serializable {
    private int stateTransition;
    private boolean ready;
    private boolean turnLeft;
    private boolean turnRight;
    private boolean raiseAnchor;
    private boolean lowerAnchor;
    private int id;
    private int time;
    private int[] ship;
    private ArrayList<Ship> Ships;

    public Directive(){
        ready = turnLeft = turnRight = raiseAnchor = lowerAnchor = false;
    }

    public void setStateTransition(int stateTransition){this.stateTransition = stateTransition;}

    public int getStateTransition(){return stateTransition;}

    public void setTurnLeft(){turnLeft = true;}
    public void setTurnRight(){turnRight = true;}
    public void setRaiseAnchor(){raiseAnchor = true;}
    public void setLowerAnchor(){lowerAnchor = true;}
    public void setReady(boolean ready){this.ready = ready;}
    public void setId(int id){this.id = id;}
    public void setTime(int time) {this.time = time;}
    public void setShip(int[] ship){this.ship=ship;}
    public void setShips(ArrayList<Ship> Ships){this.Ships = Ships;}

    public boolean isTurnRight(){return turnRight;}
    public boolean isTurnLeft(){return turnLeft;}
    public boolean isRaiseAnchor(){return raiseAnchor;}
    public boolean isLowerAnchor(){return lowerAnchor;}
    public boolean getready(){return ready;}
    public int getId(){return id;}
    public int getTime(){return time;}
    public int[] getShip(){return ship;}
    public ArrayList<Ship> getShips(){return Ships;}

}
