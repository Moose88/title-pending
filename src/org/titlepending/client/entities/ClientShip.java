package org.titlepending.client.entities;

import jig.ConvexPolygon;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;
import org.titlepending.client.Client;

public class ClientShip extends Entity {

    protected int stats[]; //hull, sail, cannon, captain... Get these numbers from another state
    private boolean isDead;
    private int playerID;
    private Vector velocity;
    private float rotationRate;
    private TiledMap map;
    private ConvexPolygon smHitbox;
    private ConvexPolygon medHitbox;
    private ConvexPolygon lgHitbox;

    private int health;
    private int lgHullHP;
    private int medHullHP;
    private int smHullHP;
    private float heading;

    private Vector sailVector;

    private static SpriteSheet ship_RSC_96 = new SpriteSheet(ResourceManager.getImage(Client.SHIP_RSC), 64, 96);

    private Image lgHull = ship_RSC_96.getSubImage(1, 0).getScaledCopy(1.5f);
    private Image medHull = ship_RSC_96.getSubImage(0, 0).getScaledCopy(1.5f);
    private Image smallHull = ship_RSC_96.getSubImage(2, 0).getScaledCopy(1.5f);
    private Image oneSail = ship_RSC_96.getSubImage(5, 0).getScaledCopy(1.5f);
    private Image twoSails = ship_RSC_96.getSubImage(4, 0).getScaledCopy(1.5f);
    private Image threeSails = ship_RSC_96.getSubImage(3, 0).getScaledCopy(1.5f);
    private Image SoneCannon = ship_RSC_96.getSubImage(6, 2).getScaledCopy(1.5f);
    private Image StwoCannons = ship_RSC_96.getSubImage(5, 2).getScaledCopy(1.5f);
    private Image SthreeCannons = ship_RSC_96.getSubImage(4, 2).getScaledCopy(1.5f);
    private Image oneCannon = ship_RSC_96.getSubImage(6, 1).getScaledCopy(1.5f);
    private Image twoCannons = ship_RSC_96.getSubImage(5, 1).getScaledCopy(1.5f);
    private Image threeCannons = ship_RSC_96.getSubImage(4, 1).getScaledCopy(1.5f);

    public ClientShip(double x, double y, int playerID) {
        super((float) x, (float) y);
        this.playerID = playerID;
        velocity = new Vector(0f, 0f);
        float center = (float) new Vector((float) x, (float) y).angleTo(new Vector(3200*5, 3200*5));
        isDead =false;
        heading = center;

        smHitbox = new ConvexPolygon(new Vector[]{
                new Vector(0,-72 ),
                new Vector(15,-40),
                new Vector(20,0),
                new Vector(15,40),
                new Vector(0,72),
                new Vector(-15,40),
                new Vector(-20,0),
                new Vector(-15,-40)
        });

        medHitbox = new ConvexPolygon(new Vector[]{
                new Vector(0,-72 ),
                new Vector(20,-40),
                new Vector(30,0),
                new Vector(20,40),
                new Vector(0,72),
                new Vector(-20,40),
                new Vector(-30,0),
                new Vector(-20,-40)
        });

        lgHitbox = new ConvexPolygon(new Vector[]{
                new Vector(0,-72 ),
                new Vector(20,-50),
                new Vector(37,0),
                new Vector(20,50),
                new Vector(0,72),
                new Vector(-20,50),
                new Vector(-37,0),
                new Vector(-20,-50)
        });

        lgHullHP = 27;
        medHullHP = 18;
        smHullHP = 9;

        setHeading(center);
        rotationRate = 0.08f;

    }

    public Vector getVelocity() {
        return velocity;
    }
    public void setVelocity(final Vector v){
        velocity =v;
    }

    public void setStats(int[] stats) {
        this.stats = stats;
    }

    public int[] getStats(){return stats;}

    public int getPlayerID() {
        return playerID;
    }

    public float getHeading() {
        return heading;
    }

    public void updateHeading(int delta){
        heading += delta * rotationRate;
        this.setRotation(heading);

    }

    public void updateheading(double minPenetration){
        heading += (float) minPenetration;
        updateVelocity();
    }

    public void setHeading(float heading){
        this.heading = heading;
    }

    public void updateVelocity(){
        setVelocity(sailVector.setRotation(heading-90));
    }

    public void updateVelocity(WindIndicator wind){
        double PD =(this.getRotation()%360);
        double WD =(wind.getRotation()%360);
        double diff = Math.abs(PD-WD);
        updateVelocity();
        if(Client.DEBUG && (diff<15|| diff>345)) {
            setVelocity(velocity.scale(1.1f));

        }
        else if(Client.DEBUG &&(diff>165 && diff<195)){
            setVelocity(velocity.scale(.9f));

        }
    }
    public void bouncedVelocity(){
        updateVelocity();
        setVelocity(getVelocity().scale(-1));
    }

    public void updateVelocity(Vector stop){
        setVelocity(stop.setRotation(heading));
    }

    public void addSprites(){
        //Setting Cannons
        switch (stats[2]) {
            case 0:
                if(stats[0] == 2)
                    addImage(SoneCannon);
                else
                    addImage(oneCannon);
                break;
            case 1:
                if(stats[0] == 2)
                    addImage(StwoCannons);
                else
                    addImage(twoCannons);
                break;
            case 2:
                if(stats[0] == 2)
                    addImage(SthreeCannons);
                else
                    addImage(threeCannons);
                break;
            default:
                System.out.println("I BROKE MY shooters!!!");
                System.exit(-100);
                break;
        }

        // Setting Hull Image and bounding box
        switch (stats[0]) {
            case 2:
                if(stats[3] == 1)
                    setHealth(smHullHP + 5);
                else
                    setHealth(smHullHP);
                addImage(smallHull);
                addShape(smHitbox, Color.transparent,Color.transparent);
                break;
            case 1:
                if(stats[3] == 1)
                    setHealth(medHullHP + 5);
                else
                    setHealth(medHullHP);
                addImage(medHull);
                addShape(medHitbox, Color.transparent,Color.transparent);
                break;
            case 0:
                if(stats[3] == 1)
                    setHealth(lgHullHP + 5);
                else
                    setHealth(lgHullHP);
                addImage(lgHull);
                addShape(lgHitbox, Color.transparent,Color.transparent);
                break;
            default:
                System.out.println("I BROKE MY haul!!!");
                System.exit(-100);
                break;

        }

        // Setting Sails
        switch (stats[1]) {
            case 2:
                addImage(oneSail);
                if(stats[3] == 0)
                    sailVector = new Vector(0f, -.35f);
                else
                    sailVector = new Vector(0f, -.25f);
                break;
            case 1:
                addImage(twoSails);
                if(stats[3] == 0)
                    sailVector = new Vector(0f, -.45f);
                else
                    sailVector = new Vector(0f, -.35f);
                break;
            case 0:
                addImage(threeSails);
                if(stats[3] == 0)
                    sailVector = new Vector(0f, -.55f);
                else
                    sailVector = new Vector(0f, -.5f);
                break;
            default:
                System.out.println("I BROKE MY SAILS!!!");
                System.exit(-100);
                break;
        }



    }

    public ConvexPolygon getHitbox(){

        switch (stats[0]) {
            case 2:
                return smHitbox;
            case 1:
                return medHitbox;
            case 0:
                return lgHitbox;
            default:
                System.out.println("I BROKE MY haul!!!");
                System.exit(-100);
                break;
        }

        return null;
    }

    @Override
    public void render(Graphics g){
        if(!isDead)
            super.render(g);



    }

    public void update(final int delta) {

        Vector pos = getPosition();
        pos.setX(velocity.getX()*delta);
        pos.setY(velocity.getY()*delta);
        this.setRotation(heading);

    }
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Vector getSailVector() { return sailVector; }

    public void setDead(boolean isDead){this.isDead = isDead;}

    public boolean getDead(){return isDead;}
}
