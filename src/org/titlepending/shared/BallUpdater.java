package org.titlepending.shared;

import java.io.Serializable;

public class BallUpdater extends CommandObject implements Serializable {
    private float x;
    private float y;
    private float vx;
    private float vy;
    private int ballID;
    private float ballDestX;
    private float ballDestY;
    private int ttl;
    private float heading;

    public BallUpdater(int id){
        super(id,true);
    }
    public void setX(float x){this.x=x;}
    public void setY(float y){this.y=y;}
    public void setVx(float vx){this.vx=vx;}
    public void setVy(float vy){this.vy=vy;}
    public void setBallID(int ballID){this.ballID=ballID;}
    public void setBallDestX(float ballDestX){this.ballDestX=ballDestX;}
    public void setBallDestY(float ballDestY){this.ballDestY=ballDestY;}
    public void setTtl(int ttl){this.ttl = ttl;}
    public void setHeading(float heading) {this.heading = heading;}

    public int getBallID(){return  ballID;}
    public float getBallDestX(){return ballDestX;}
    public float getBallDestY(){return ballDestY;}
    public int getTtl(){return ttl;}
    public float getX(){return x;}
    public float getY(){return y;}
    public float getVx(){return vx;}
    public float getVy(){return vy;}
    public float getHeading() {return heading;}



}
