package org.titlepending.shared;

import java.io.Serializable;

/**
 * object that is sent across TCP/IP socket for communication in client server pair.
 */
public class Directive implements Serializable {
    private int stateTransition;
    private boolean turnLeft;
    private boolean turnRight;
    private boolean raiseAnchor;
    private boolean lowerAnchor;
    private int id;
    private long time;
    public Directive(){
        turnLeft = turnRight = raiseAnchor = lowerAnchor = false;
    }

    public void setStateTransition(int stateTransition){this.stateTransition = stateTransition;}

    public int getStateTransition(){return stateTransition;}

    public void setTurnLeft(){turnLeft = true;}
    public void setTurnRight(){turnRight = true;}
    public void setRaiseAnchor(){raiseAnchor = true;}
    public void setLowerAnchor(){lowerAnchor = true;}
    public void setId(int id){this.id = id;}
    public void setTime(long time) {this.time = time;}

    public boolean isTurnRight(){return turnRight;}
    public boolean isTurnLeft(){return turnLeft;}
    public boolean isRaiseAnchor(){return raiseAnchor;}
    public boolean isLowerAnchor(){return lowerAnchor;}
    public int getId(){return id;}
    public long getTime(){return time;}
}
