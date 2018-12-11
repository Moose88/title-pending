package org.titlepending.server.ServerObjects;

import org.titlepending.server.Server;

import java.io.Serializable;

public class Turret extends GameObject implements Serializable {

    private int turretID;
    private float direction;
    private int health;
    public Turret(float x, float y, int turretID){
        super(x, y, 0, 0);
        this.turretID = turretID;
        if(Server.DEBUG)
            health = 2;
        else
            health = 30;
    }

    public int getTurretID() {
        return turretID;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction){
        this.direction = direction;
    }

    public int getHealth(){
        return health;
    }

    public void setHealth(int health){
        this.health = health;
    }
}
