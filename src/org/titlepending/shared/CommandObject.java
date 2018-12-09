package org.titlepending.shared;

import java.io.Serializable;

public class CommandObject implements Serializable {
    // ID of what is sending the message 0 reserved for the server.
    private int id;
    private boolean cannonBall;
    protected boolean isDead;
    public CommandObject(int id, boolean cannonBall){
        this.id=id;
        this.cannonBall = cannonBall;
        this.isDead = false;
    }

    public int getId() {
        return id;
    }
    public boolean getCannonBall(){return cannonBall;}
    public void setIsDead(boolean isDead){this.isDead = isDead;}
    public boolean getIsDead(){return isDead;}


}
