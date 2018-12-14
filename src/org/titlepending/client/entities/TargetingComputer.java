package org.titlepending.client.entities;


import org.newdawn.slick.Graphics;

public class TargetingComputer {
    private ClientShip ship;

    private TargetNet targetNet;
    private boolean fireRight;
    private boolean isVisible;

    public TargetingComputer(ClientShip ship){
        this.ship=ship;
        isVisible = false;
        targetNet = new TargetNet(this.ship.getX(),this.ship.getY());
        fireRight = true;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void aimLeft(){
        targetNet.setPosition(ship.getX(),ship.getY());
        targetNet.setRotation(ship.getHeading()-90);
        fireRight = false;
    }
    public void aimRight(){
        targetNet.setPosition(ship.getX(),ship.getY());
        targetNet.setRotation(ship.getHeading()+90);
        fireRight = true;
    }

    public TargetNet getTargetNet() {
        return targetNet;
    }

    public boolean getFireRight(){return  fireRight;}

    public void setTargetNet(TargetNet targetNet) {
        this.targetNet = targetNet;
    }

    public void render(Graphics g){
        if(isVisible)
            targetNet.render(g);
    }
}
