package org.titlepending.client.entities;

import jig.Entity;
import jig.Vector;

public class Wind extends Entity {
    private Vector velocity;

    public Wind(float dx, float dy) {
        velocity = new Vector(dx, dy);
    }

    public void update(Vector update) {
        velocity = update;
    }
    public float getVx(){
        return velocity.getX();
    }
    public float getVy(){
        return velocity.getY();
    }
    @Override
    public double getRotation(){return velocity.getRotation();}


}
