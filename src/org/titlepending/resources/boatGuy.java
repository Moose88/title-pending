package org.titlepending.resources;

import jig.Entity;
import jig.Vector;
import org.newdawn.slick.Graphics;

public class boatGuy extends Entity {

    public boolean done = false;
    public boolean isMoving = false;
    public Vector movingTo;
    public float speed = 0.15f;


    private void boatGuy(){

    }

    public void movement(float newX){
        System.out.println("I'm in Movement!");

        if(isMoving) {
            return;
        }

        //addImage();
        System.out.println("Position =  " + newX);
        movingTo = getPosition().setX(newX);
        isMoving = true;

    }

    public void update(int delta){
        System.out.println("Updating!");

        if (isMoving) {
            double angle = getPosition().angleTo(movingTo);
            setPosition(getPosition().add(Vector.getUnit(angle).scale(speed * delta)));
            System.out.println(getPosition());
            if (getPosition().epsilonEquals(movingTo, speed * delta)) {
                isMoving = false;

                setPosition(movingTo);
                System.out.println("movingTo x: " + movingTo.getX() + " movingTo y: " + movingTo.getY());
            }

        }

    }

    @Override
    public void render(Graphics g){
        super.render(g);
        if(done)
            return;
        g.fillRect(getPosition().getX(), 500, 100, 100);
    }
}
