package org.titlepending.shared;

import java.io.Serializable;

/**
 * object that is sent across TCP/IP socket for communication in client server pair.
 */
public class Nuntius implements Serializable {
    private int stateTransition;
    private boolean turnLeft;
    private boolean turnRight;
    private boolean raiseAnchor;
    private boolean lowerAnchor;
    public Nuntius(){
        turnLeft = turnRight = raiseAnchor = lowerAnchor = false;
    }

    public void setStateTransition(int stateTransition){this.stateTransition = stateTransition;}

    public int getStateTransition(){return stateTransition;}

    public void setTurnLeft(){turnLeft = true;}
    public void setTurnRight(){turnRight = true;}
    public void setRaiseAncor(){raiseAnchor = true;}
    public void setLowerAncor(){lowerAnchor = true;}

    public boolean isTurnRight(){return turnRight;}
    public boolean isTurnLeft(){return turnLeft;}
    public boolean isRaiseAnchor(){return raiseAnchor;}
    public boolean isLowerAnchor(){return lowerAnchor;}
}
