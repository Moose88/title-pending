package org.titlepending.shared;

import java.io.Serializable;

/**
 * object that is sent across TCP/IP socket for communication in client server pair.
 */
public class Nuntius implements Serializable {
    private boolean forServer;
    private int stateTransition;

    public Nuntius(){}

    public  void setForServer(boolean forServer){this.forServer = forServer;}

    public void setStateTransition(int stateTransition){this.stateTransition = stateTransition;}

    public int getStateTransition(){return stateTransition;}

    public boolean getForServer(){return forServer;}
}
