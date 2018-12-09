package org.titlepending.shared;

import java.io.Serializable;

public class Action extends CommandObject implements Serializable {
    private boolean cannonBall;
    private float x;
    private float y;
    private float vx;
    private float vy;
    private float heading;
    private int updatedShip;
    private int ttl;
    private boolean isDead;
    private int ballID;
    private float ballDestX;
    private float ballDestY;
    public  Action(int id){
        super(id);
        cannonBall =false;
        isDead =false;
    }

    public void setBallID(int ballID){this.ballID=ballID;}
    public void setX(float x){this.x=x;}
    public void setY(float y){this.y=y;}
    public void setVx(float vx){this.vx=vx;}
    public void setVy(float vy){this.vy=vy;}
    public void setCannonBall(boolean cannonBall) {this.cannonBall = cannonBall;}
    public void setHeading(float heading) {this.heading = heading;}
    public void setUpdatedShip(int updatedShip){this.updatedShip=updatedShip;}
    public void setTtl(int ttl){this.ttl = ttl;}
    public void setDead(boolean isDead){this.isDead = isDead;}
    public void setBallDestX(float ballDestX){this.ballDestX=ballDestX;}
    public void setBallDestY(float ballDestY){this.ballDestY=ballDestY;}
    public boolean getCannonBall(){return cannonBall;}
    public float getX(){return x;}
    public float getY(){return y;}
    public float getVx(){return vx;}
    public float getVy(){return vy;}
    public float getHeading() {return heading;}
    public int getUpdatedShip(){return updatedShip;}
    public int getTtl(){return ttl;}
    public boolean getIsDead(){return isDead;}
    public int getBallID(){return  ballID;}
    public float getBallDestX(){return ballDestX;}
    public float getBallDestY(){return ballDestY;}
}
