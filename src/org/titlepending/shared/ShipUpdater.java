package org.titlepending.shared;

import java.io.Serializable;

public class ShipUpdater extends CommandObject implements Serializable {
    private float x;
    private float y;
    private float vx;
    private float vy;
    private float heading;
    private int updatedShip;
    public ShipUpdater(int id){
        super(id,1);

    }

    public void setX(float x){this.x=x;}
    public void setY(float y){this.y=y;}
    public void setVx(float vx){this.vx=vx;}
    public void setVy(float vy){this.vy=vy;}
    public void setHeading(float heading) {this.heading = heading;}
    public void setUpdatedShip(int updatedShip){this.updatedShip=updatedShip;}

    public float getX(){return x;}
    public float getY(){return y;}
    public float getVx(){return vx;}
    public float getVy(){return vy;}
    public float getHeading() {return heading;}
    public int getUpdatedShip(){return updatedShip;}

}
