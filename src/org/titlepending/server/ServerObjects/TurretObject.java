package org.titlepending.server.ServerObjects;

import java.io.Serializable;

public class TurretObject extends GameObject implements Serializable {

    private int turretID;

    public TurretObject(float x, float y, int turretID){
        super(x, y, 0, 0);
        this.turretID = turretID;

    }
}
