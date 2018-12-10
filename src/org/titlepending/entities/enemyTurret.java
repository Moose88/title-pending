package org.titlepending.entities;

import jig.Collision;
import jig.Entity;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;
import java.util.HashMap;

public class enemyTurret extends Entity {

    private HashMap<Integer, ClientShip> CShips;
    private HashMap<Integer, CannonBall> cannonBalls;
    private TargetingComputer cannonsTargeting;

    private int health;
    private int turretID;
    private Circle hitbox;
    private Circle detectionCircle;

    public enemyTurret(float x, float y, float direction){
        super(x, y);


        // The x and y will be its initial placement on the map
        
        hitbox = new Circle(27000, 8000, 50);
        detectionCircle = new Circle(27000, 8000, 400);

    }

    public void setImage(){
        hitbox.setLocation(this.getX(), this.getY());
    }


}
