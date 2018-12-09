package org.titlepending.shared;

import java.io.Serializable;

public class CommandObject implements Serializable {
    // ID of what is sending the message 0 reserved for the server.
    private int id;
    int type;
    protected boolean isDead;
    public CommandObject(int id, int type){
        this.id=id;
        this.type = type;
        this.isDead = false;
    }

    public int getId() {
        return id;
    }
    public int getType(){return type;}
    public void setIsDead(boolean isDead){this.isDead = isDead;}
    public boolean getIsDead(){return isDead;}


}
