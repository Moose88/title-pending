package org.titlepending.entities;

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
    @Override
    public double getRotation(){return velocity.getRotation();}


}
