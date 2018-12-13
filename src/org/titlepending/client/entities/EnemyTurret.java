package org.titlepending.client.entities;

import jig.ConvexPolygon;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
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
    private TargetReticle targetReticle;

    private ConvexPolygon hitbox;
    private TurretTargetNet detectionCircle;

    private SpriteSheet RSC_64_64 = new SpriteSheet(ResourceManager.getImage(Client.SS2_RSC), 64, 64);

    public EnemyTurret(float x, float y, float direction){
        super(x, y);


        // The x and y will be its initial placement on the map
        isDead =false;
        float center = (float) new Vector(x, y).angleTo(new Vector(3200*5, 3200*5));
        hitbox = new ConvexPolygon(50);
        heading = center;
        setHeading(center);
        rotationRate = 0.08f;
        detectionCircle = new TurretTargetNet(x,y);
        targetReticle = new TargetReticle(0,0,true);

    }

    public void setImage(){
        addShape(hitbox, Color.transparent, Color.transparent);
        addImage(RSC_64_64.getSubImage(5, 0).getScaledCopy(1.5f));

    }

    public void updateHeading(int delta){
        heading += delta * rotationRate;
        this.setRotation(heading);
    }

    public void update(final int delta,ClientShip boat) {
        cannonCooldown -= delta;
        if(cannonCooldown <= 0)
            justFired = false;

        if(detectionCircle.collides(boat)!=null){
            targetReticle.setVisible(true);
            targetReticle.setPosition(boat.getX()+(boat.getVelocity().scale(8*delta).getX()),boat.getY()+(boat.getVelocity().scale(8*delta).getY()));
        }else
            targetReticle.setVisible(false);

    }

    @Override
    public void render(Graphics g){
        if(!isDead)
            super.render(g);
        if(targetReticle.isVisible())
            targetReticle.render(g);
        if(Client.DEBUG){
            detectionCircle.render(g);
        }
    }

    public CannonBall fireCannon(ClientShip boat){
        if(cannonCooldown > 0)
            return null;
        if(detectionCircle.collides(boat)==null)
            return null;
        justFired = true;
        cannonCooldown = 1000;
        CannonBall ball = new CannonBall(this.getX(), this.getY(), targetReticle.getX(), targetReticle.getY(), +90, ThreadLocalRandom.current().nextInt(), turretID);
        return ball;

    }


    public float getHeading() { return heading; }
    public int getTurretID() { return turretID; }
    public int getHealth() { return health; }
    public boolean isDead() { return isDead; }

    public void setHeading(float heading) { this.heading = heading; }
    public void setTurretID(int turretID) { this.turretID = turretID; }
    public void setHealth(int health) { this.health = health; }
    public void setDead(boolean dead) { isDead = dead; }

}
