package org.titlepending.entities;


import org.newdawn.slick.Graphics;

public class TargetingComputer {
    private ClientShip ship;

    private TargetNet targetNet;

    private boolean isVisible;

    public TargetingComputer(ClientShip ship){
        this.ship=ship;
        isVisible = false;
        targetNet = new TargetNet(this.ship.getX(),this.ship.getY());
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
    }
    public void aimRight(){
        targetNet.setPosition(ship.getX(),ship.getY());
        targetNet.setRotation(ship.getHeading()+90);
    }

    public TargetNet getTargetNet() {
        return targetNet;
    }

    public void setTargetNet(TargetNet targetNet) {
        this.targetNet = targetNet;
    }

    public void render(Graphics g){
        if(isVisible)
            targetNet.render(g);
    }
}
