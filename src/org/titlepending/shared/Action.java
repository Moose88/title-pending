package org.titlepending.shared;

import java.io.Serializable;

public class Action extends CommandObject implements Serializable {
    private boolean fireCannons;
    private float x;
    private float y;
    private float vx;
    private float vy;
    public  Action(int id){
        super(id);
        fireCannons=false;
    }

    public void setFireCannons(){fireCannons=true;}
    public void setX(float x){this.x=x;}
    public void setY(float y){this.y=y;}
    public void setVX(float vx){this.vx=vx;}
    public void setVY(float vy){this.vy=vy;}
    public boolean isFireCannons(){return fireCannons;}
    public float getX(){return x;}
    public float getY(){return y;}
    public float getVx(){return vx;}
    public float getVy(){return vy;}
}
