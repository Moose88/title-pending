package org.titlepending.entities;

import jig.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.titlepending.client.Client;
import org.titlepending.client.entities.CannonBall;
import org.titlepending.client.entities.ClientShip;
import org.titlepending.client.entities.TargetingComputer;
import java.util.HashMap;

public class enemyTurret extends Entity {

    private HashMap<Integer, ClientShip> CShips;
    private HashMap<Integer, CannonBall> cannonBalls;
    private TargetingComputer cannonsTargeting;
    private int health;
    private int turretID;
    private boolean isDead;
    private float rotationRate;
    private float heading;
    private ConvexPolygon hitbox;
    private ConvexPolygon detectionCircle;

    private SpriteSheet test = new SpriteSheet(ResourceManager.getImage(Client.SS2_RSC), 32, 32);

    public enemyTurret(float x, float y, float direction){
        super(x, y);


        // The x and y will be its initial placement on the map
        isDead =false;
        float center = (float) new Vector(x, y).angleTo(new Vector(3200*5, 3200*5));
        hitbox = new ConvexPolygon(100);
        detectionCircle = new ConvexPolygon(500);
        heading = center;
        setHeading(center);
        rotationRate = 0.08f;

    }

    public void setImage(){
        addShape(hitbox, Color.red, Color.black);
        addShape(detectionCircle, Color.transparent, Color.red);

    }

    public void updateHeading(int delta){
        heading += delta * rotationRate;
        this.setRotation(heading);
    }

    public void update(final int delta) { this.setRotation(heading); }

    public float getHeading() { return heading; }
    public int getTurretID() { return turretID; }
    public int getHealth() { return health; }
    public boolean isDead() { return isDead; }

    public void setHeading(float heading) { this.heading = heading; }
    public void setTurretID(int turretID) { this.turretID = turretID; }
    public void setHealth(int health) { this.health = health; }
    public void setDead(boolean dead) { isDead = dead; }

}
