package org.titlepending.shared;

import java.io.Serializable;

public class CommandObject implements Serializable {
    // ID of what is sending the message 0 reserved for the server.
    private int id;
    public CommandObject(int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }
}
