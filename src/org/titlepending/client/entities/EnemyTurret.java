package org.titlepending.client.entities;

import jig.ConvexPolygon;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.Color;
import org.newdawn.slick.SpriteSheet;
import org.titlepending.client.Client;
import java.util.concurrent.ThreadLocalRandom;

public class EnemyTurret extends Entity {

    private int health;
    private int turretID;
    private boolean isDead;
    private float rotationRate;
    private float heading;
    private int cannonCooldown;
    private boolean justFired;

    private ConvexPolygon hitbox;
    private ConvexPolygon detectionCircle;

    private SpriteSheet test = new SpriteSheet(ResourceManager.getImage(Client.SS2_RSC), 32, 32);

    public EnemyTurret(float x, float y, float direction){
        super(x, y);


        // The x and y will be its initial placement on the map
        isDead =false;
        float center = (float) new Vector(x, y).angleTo(new Vector(3200*5, 3200*5));
        hitbox = new ConvexPolygon(50);
        this.setCoarseGrainedRadius(50);
        detectionCircle = new ConvexPolygon(650);
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

    public void update(final int delta) {
        // This is where we want to check for ships in our detection circle,
        // Then to rotate and fire at the first/remaining ships in its circle.

        cannonCooldown -= delta;

        if(cannonCooldown <= 0)
            justFired = false;

        //this.setRotation(heading);
    }

    public CannonBall fireCannon(ClientShip boat){
        if(cannonCooldown > 0)
            return null;

        justFired = true;
        cannonCooldown = 3000;
        CannonBall ball = new CannonBall(this.getX(), this.getY(), boat.getX(), boat.getY(), +90, ThreadLocalRandom.current().nextInt(), turretID);
        return ball;

    }


    public float getHeading() { return heading; }
    public int getTurretID() { return turretID; }
    public int getHealth() { return health; }
    public boolean isDead() { return isDead; }
    public ConvexPolygon getDetectionCircle() { return detectionCircle; }

    public void setHeading(float heading) { this.heading = heading; }
    public void setTurretID(int turretID) { this.turretID = turretID; }
    public void setHealth(int health) { this.health = health; }
    public void setDead(boolean dead) { isDead = dead; }

}
