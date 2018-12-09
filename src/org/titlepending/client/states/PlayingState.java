package org.titlepending.client.states;

import jig.Collision;
import jig.ConvexPolygon;
import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.titlepending.client.Client;
import org.titlepending.client.Updates;
import org.titlepending.entities.CannonBall;
import org.titlepending.entities.ClientShip;
import org.titlepending.entities.TargetReticle;
import org.titlepending.entities.TargetingComputer;
import org.titlepending.server.ServerObjects.Ship;
import org.titlepending.shared.Action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class PlayingState extends BasicGameState {
    private HashMap<Integer, ClientShip> CShips;
    private ArrayList<CannonBall> cannonBalls;
    private ClientShip myBoat;
    private TiledMap map;
    private int islandLayer;
    private int oceanLayer;
    private int whirlpoolLayer;

    private int bounceDelay;
    private int cmdDelay;
    private int rightDelay;
    private int leftDelay;
    private TargetingComputer cannonsTargeting;
    private TargetReticle reticle;
    private boolean anchor;
    private static SpriteSheet RSC_32_32;
    private static Image topleft;
    private static Image top;
    private static Image topright;
    private static Image right;
    private static Image bottomright;
    private static Image bottom;
    private static Image bottomleft;
    private static Image left;
    private static Image arrow;

    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        map = new TiledMap(Client.MAP_RSC);
        anchor = true;


        RSC_32_32 = new SpriteSheet(ResourceManager.getImage(Client.SS2_RSC), 32, 32);

        // Breadown of the RSC_32_32
        topleft = RSC_32_32.getSubImage(0, 0).getScaledCopy(3f);
        top = RSC_32_32.getSubImage(1, 0).getScaledCopy(3f);
        topright = RSC_32_32.getSubImage(2, 0).getScaledCopy(3f);
        right = RSC_32_32.getSubImage(2, 1).getScaledCopy(3f);
        bottomright = RSC_32_32.getSubImage(2, 2).getScaledCopy(3f);
        bottom = RSC_32_32.getSubImage(1, 2).getScaledCopy(3f);
        bottomleft = RSC_32_32.getSubImage(0, 2).getScaledCopy(3f);
        left = RSC_32_32.getSubImage(0, 1).getScaledCopy(3f);

        islandLayer = map.getLayerIndex("Tile Layer 2"); // = 2
        whirlpoolLayer = map.getLayerIndex("Tile Layer 3"); // = 1
        oceanLayer = map.getLayerIndex("Tile Layer 5"); // = 0


    }

    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException{


        bounceDelay =0;
        HashMap<Integer,Ship> ships = Updates.getInstance().getShips();
        cannonBalls = new ArrayList<CannonBall>();
        this.CShips = new HashMap<>();
        if(Client.DEBUG){
            System.out.println("Attepting to create tiled map from: "+Client.MAP_RSC);
        }
        if(Client.DEBUG)
            System.out.println("Before myBoat thread ID: " + Updates.getInstance().getThread().getClientId());

        Iterator i = ships.entrySet().iterator();

        while (i.hasNext()){
            Map.Entry pair = (Map.Entry) i.next();
            Ship ship = ships.get(pair.getKey());
            if(Client.DEBUG){
                System.out.println("Got ID: "+ship.getPlayerID()+" x: "+ship.getX()+" y: "+ship.getY());
            }
            ClientShip temp =new ClientShip(ship.getX(), ship.getY(), ship.getPlayerID());
            temp.setStats(ship.getStats());
            temp.addSprites();
            CShips.put(ship.getPlayerID(),temp);
        }

        myBoat = CShips.get(Updates.getInstance().getThread().getClientId());
        if(Client.DEBUG)
            System.out.println("After myBoat thread ID: " + Updates.getInstance().getThread().getClientId());
        if(Client.DEBUG)
            System.out.println("Boat x: " + myBoat.getX() + " Boat y: " + myBoat.getY() + " Boat id: " + myBoat.getPlayerID());


        cannonsTargeting = new TargetingComputer(myBoat);
        reticle = new TargetReticle(0,0);
        System.out.println("Made it to the playing state");
        rightDelay=leftDelay=0;
    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{
        Client client = (Client)game;
        int screenX = (int) myBoat.getX() - client.ScreenWidth/2;
        int screenY = (int) myBoat.getY() - client.ScreenHeight/2;

        g.pushTransform();
        g.translate(-screenX, -screenY);

        g.pushTransform();
        g.scale(5, 5);
        map.render(0,0);
        g.popTransform();

        for (Map.Entry<Integer, ClientShip> integerClientShipEntry : CShips.entrySet()) {
            ClientShip ship = CShips.get(((Map.Entry) integerClientShipEntry).getKey());
            ship.render(g);
        }
        cannonsTargeting.render(g);
        reticle.render(g);
        for (CannonBall ball : cannonBalls){
            ball.render(g);
        }

        g.popTransform();
        int Xsofar;
        int Ysofar = 0;

        for(Xsofar = top.getWidth(); Xsofar < client.ScreenWidth - top.getWidth(); Xsofar += top.getWidth()){
            g.drawImage(top, Xsofar, Ysofar);
            g.drawImage(bottom, Xsofar, client.ScreenHeight - bottom.getWidth());
        }

        for(Ysofar = left.getHeight(); Ysofar < client.ScreenHeight - left.getHeight(); Ysofar += left.getHeight()){
            g.drawImage(left, 0, Ysofar);
            g.drawImage(right, Xsofar, Ysofar);
        }

        g.drawImage(topleft, 0, 0);
        g.drawImage(bottomleft, 0, client.ScreenHeight - bottomleft.getHeight());
        g.drawImage(topright, client.ScreenWidth - topright.getWidth(), 0);
        g.drawImage(bottomright, client.ScreenWidth - topright.getWidth(), client.ScreenHeight - bottomright.getHeight());

    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{
        /** update all ships from server command before we do local updates **/
        while(!Updates.getInstance().getQueue().isEmpty()){
            Action cmd = (Action) Updates.getInstance().getQueue().poll();
            assert cmd !=null;

            ClientShip update = CShips.get(cmd.getUpdatedShip());
            update.setPosition(cmd.getX(),cmd.getY());
            if(cmd.getUpdatedShip() != myBoat.getPlayerID())
                update.setHeading(cmd.getHeading());
            update.setVelocity(new Vector(cmd.getVx(),cmd.getVy()));
        }

        Iterator i = CShips.entrySet().iterator();
        while (i.hasNext()){
            Map.Entry pair = (Map.Entry) i.next();
            ClientShip update = CShips.get(pair.getKey());
            update.update(delta);
        }

        i = cannonBalls.iterator();
        while (i.hasNext()){
            CannonBall ball = (CannonBall) i.next();
            if(ball.isDead()){
                i.remove();
            }else{
                ball.update(delta);
            }

        }

        i = CShips.entrySet().iterator();
        while(i.hasNext()){
            Map.Entry pair = (Map.Entry) i.next();
            if(myBoat.getDetectionCircle().contains(CShips.get(pair.getKey()).getX(), CShips.get(pair.getKey()).getY())
                    && CShips.get(pair.getKey()).getPlayerID() != myBoat.getPlayerID()){
//                if(Client.DEBUG)
//                    System.out.println("O LAWD HE COMIN!");
            }
        }


        bounceDelay -= delta;
        cmdDelay -= delta;
        leftDelay-=delta;
        rightDelay-=delta;
        Input input = container.getInput();
        boolean changed = false;
        if(input.isKeyDown(Input.KEY_W)){
            // Send raise anchor command to server

            anchor = false;
            myBoat.updateVelocity();
            changed =true;

        } else if(input.isKeyDown(Input.KEY_S)){
            // Send lower anchor command to server
            anchor = true;
            myBoat.updateVelocity(new Vector(0f,0f));
            changed = true;

        }

        if(input.isKeyDown(Input.KEY_A) && !anchor){
            // Send command to turn left
                myBoat.updateHeading(-delta);
                myBoat.updateVelocity();
                changed =true;

        } else if(input.isKeyDown(Input.KEY_D) && !anchor){
            // Send command to turn right
                myBoat.updateHeading(delta);
                myBoat.updateVelocity();
                changed = true;
        }
        boolean isLeft = true;

        if(input.isKeyDown(Input.KEY_SPACE)){
            if(input.isKeyDown(Input.KEY_A)){
                cannonsTargeting.setVisible(true);
                cannonsTargeting.aimLeft();

            }else if(input.isKeyDown(Input.KEY_D)){
                cannonsTargeting.setVisible(true);
                cannonsTargeting.aimRight();
            }else
                cannonsTargeting.setVisible(false);
        }else{
            cannonsTargeting.setVisible(false);
            if(reticle.isVisible()){
                boolean justFired = false;
                if(Client.DEBUG)
                    System.out.println("Firing cannon at ("+reticle.getX()+","+reticle.getY()+") Number of cannonballs: "+myBoat.getStats()[2]);
                for(int j=0; j<myBoat.getStats()[2]+1;j++){
                    if(cannonsTargeting.getFireRight()&&rightDelay<=0) {
                        cannonBalls.add(new CannonBall(myBoat.getX() + j * 20, myBoat.getY() + j, reticle.getX(), reticle.getY(), +90));
                        justFired = true;
                    }else if(!cannonsTargeting.getFireRight()&& leftDelay<=0){
                        cannonBalls.add(new CannonBall(myBoat.getX()+j*20,myBoat.getY()+j,reticle.getX(),reticle.getY(), +90));
                        justFired = true;
                    }
                }
                if(!cannonsTargeting.getFireRight() && justFired)
                    leftDelay=3000;
                else if(justFired)
                    rightDelay=3000;
            }
        }

        if(cannonsTargeting.isVisible()){
            i = CShips.entrySet().iterator();
            reticle.setVisible(false);
            while (i.hasNext()){
                Map.Entry pair = (Map.Entry) i.next();
                ClientShip ship = CShips.get(pair.getKey());
                if(ship.getPlayerID() != myBoat.getPlayerID()){
                    Collision collision = cannonsTargeting.getTargetNet().collides(ship);
                    if(collision!=null){
                        reticle.setVisible(true);
                        reticle.setPosition(ship.getX(),ship.getY());
                    }
                }
        }}else{
            reticle.setVisible(false);
        }

//(float)(myBoat.getX()+288 *Math.cos((double)myBoat.getHeading())),(float) (myBoat.getY()+288*Math.sin((double)myBoat.getHeading())))

        if(!notanIsland(myBoat.getHitbox()) && bounceDelay <= 0){
            // Here we need to

            System.out.println("LAND HO!!");
            changed = true;
            bounceDelay = 100;

        }


        if(changed){
//            if(Client.DEBUG)
//                System.out.println("Sending vx: "+myBoat.getVelocity().getX()+" vy: "+myBoat.getVelocity().getY()+" heading: "+myBoat.getHeading());
            Action cmd = new Action(Updates.getInstance().getThread().getClientId());
            cmd.setHeading(myBoat.getHeading());
            cmd.setVx(myBoat.getVelocity().getX());
            cmd.setVy(myBoat.getVelocity().getY());
            sendCommand(cmd);
        }

    }

    private void sendCommand(Action cmd){
        try{
            Updates.getInstance().getThread().sendCommand(cmd);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean notanIsland(ConvexPolygon hitbox){

        float minX = myBoat.getX() + hitbox.getMinX();
        float maxX = myBoat.getX() + hitbox.getMaxX();
        float minY = myBoat.getY() + hitbox.getMinY();
        float maxY = myBoat.getY() + hitbox.getMaxY();

        int tilex;
        int tiley;

//        if(Client.DEBUG) {
//            System.out.println("minX: " + minX + " maxX: " + maxX);
//            System.out.println("minY: " + minY + " maxY: " + maxY);
//            System.out.println("Current x: " + myBoat.getX() + " Current y: " + myBoat.getY());
//        }

        if(map.getTileId((int) minX/160, (int) maxY/160, islandLayer) != 0 &&
                map.getTileId((int) maxX/160, (int) maxY/160, islandLayer) != 0){
            if(Client.DEBUG)
                System.out.println("Boat Front hit");

            float curHeading = myBoat.getHeading() % 360;

            tilex = (int) myBoat.getX()/160;
            tiley = (int) (myBoat.getY() + 48)/160;

            Vector tileCenter = new Vector(tilex * 160 + 16, tiley * 160 + 16);

            myBoat.getVelocity().angleTo(tileCenter);
            System.out.println("Current heading: " + curHeading + " Tile vector: " + tileCenter + " The angle between my boat and the tile is: " + myBoat.getVelocity().angleTo(tileCenter));
            //Front of boat
            //myBoat.updateHeading((float) ThreadLocalRandom.current().nextDouble(.79, 1.57));
            //myBoat.setVelocity(myBoat.getVelocity().scale(-1));
            //myBoat.updateVelocity();
            return false;
        }

        return true;

    }

    public boolean isGameInProgress(){
        return false;
    }

    public void bounce(){
        myBoat.setVelocity(new Vector(myBoat.getVelocity().getX() * -1, myBoat.getVelocity().getY() * -1));
    }


    public int getID(){return Client.PLAYINGSTATE; }
}
