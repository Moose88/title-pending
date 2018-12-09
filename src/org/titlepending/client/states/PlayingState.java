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
import org.titlepending.entities.*;
import org.titlepending.server.ServerObjects.Ship;
import org.titlepending.shared.BallUpdater;
import org.titlepending.shared.CommandObject;
import org.titlepending.shared.ShipUpdater;
import org.titlepending.shared.WindUpdater;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class PlayingState extends BasicGameState {
    private HashMap<Integer, ClientShip> CShips;
    private HashMap<Integer, CannonBall> cannonBalls;
    private ClientShip myBoat;
    private TiledMap map;
    private int islandLayer;
    private int oceanLayer;
    private int whirlpoolLayer;

    private int bounceDelay;
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
    private WindIndicator wind;

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

        System.out.println("Made it to the playing state");

        bounceDelay =0;
        HashMap<Integer,Ship> ships = Updates.getInstance().getShips();
        this.cannonBalls = new HashMap<>();
        this.CShips = new HashMap<>();
        if(Client.DEBUG){
            System.out.println("Attepting to create tiled map from: "+Client.MAP_RSC);
        }
        if(Client.DEBUG) {
            System.out.println("Before myBoat thread ID: " + Updates.getInstance().getThread().getClientId());
        }

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
        if(Client.DEBUG) {
            System.out.println("After myBoat thread ID: " + Updates.getInstance().getThread().getClientId());
        }
        if(Client.DEBUG) {
            System.out.println("Boat x: " + myBoat.getX() + " Boat y: " + myBoat.getY() + " Boat id: " + myBoat.getPlayerID());
        }

        wind = new WindIndicator( container.getScreenWidth()-128,container.getHeight()-128);
        wind.setWind(new Wind(0,-1));

        cannonsTargeting = new TargetingComputer(myBoat);
        reticle = new TargetReticle(0,0);
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
        for (Map.Entry<Integer, CannonBall> integerCannonBallEntry : cannonBalls.entrySet()){
            CannonBall ball = cannonBalls.get(((Map.Entry) integerCannonBallEntry).getKey());
            ball.render(g);
        }

        wind.render(g);
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
        ShipUpdater shipUpdater;
        BallUpdater ballUpdater;
        while(!Updates.getInstance().getQueue().isEmpty()){
            CommandObject cmd = Updates.getInstance().getQueue().poll();
            assert cmd !=null;
            if(cmd.getType()==1){
                shipUpdater = (ShipUpdater) cmd;
                //do stuff to client representation of ships
                ClientShip update = CShips.get(shipUpdater.getUpdatedShip());
                update.setPosition(shipUpdater.getX(),shipUpdater.getY());
                if(shipUpdater.getUpdatedShip() != myBoat.getPlayerID())
                    update.setHeading(shipUpdater.getHeading());
                update.setVelocity(new Vector(shipUpdater.getVx(),shipUpdater.getVy()));
            }else if(cmd.getType() ==2){
                //do stuff to client representation of cannonballs
                ballUpdater = (BallUpdater) cmd;
                if(cannonBalls.containsKey(ballUpdater.getBallID())){
                    CannonBall update = cannonBalls.get(ballUpdater.getBallID());
                    if(!update.isDead()){
                        update.setPosition(ballUpdater.getX(),ballUpdater.getY());
                    }else{
                        cannonBalls.remove(ballUpdater.getBallID());
                    }
                }else{
                    CannonBall newBall = new CannonBall(ballUpdater.getX(),ballUpdater.getY(),ballUpdater.getBallDestX(),ballUpdater.getBallDestY(),ballUpdater.getHeading()+90,ballUpdater.getBallID(),cmd.getId());
                    cannonBalls.put(newBall.getId(),newBall);
                }
            }else{
                if(Client.DEBUG)
                    System.out.println("Received wind update");
                WindUpdater windUpdater = (WindUpdater) cmd;
                if(Client.DEBUG)
                wind.update(new Vector(windUpdater.getVx(),windUpdater.getVy()));
            }
        }

        Iterator i = CShips.entrySet().iterator();
        while (i.hasNext()){
            Map.Entry pair = (Map.Entry) i.next();
            ClientShip update = CShips.get(pair.getKey());
            update.update(delta);
        }

        i = cannonBalls.entrySet().iterator();
        while (i.hasNext()){
            Map.Entry pair = (Map.Entry) i.next();
            CannonBall ball = cannonBalls.get(pair.getKey());
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
        leftDelay-=delta;
        rightDelay-=delta;
        Input input = container.getInput();
        boolean changed = false;
        if(input.isKeyDown(Input.KEY_W) && bounceDelay <= 0){
            // Send raise anchor command to server

            anchor = false;
            myBoat.updateVelocity();
            changed =true;

        } else if(input.isKeyDown(Input.KEY_S) && bounceDelay <= 0){
            // Send lower anchor command to server
            anchor = true;
            myBoat.updateVelocity(new Vector(0f,0f));
            changed = true;

        }

        if(input.isKeyDown(Input.KEY_A)
                && !anchor
                && !input.isKeyDown(Input.KEY_SPACE)
                && bounceDelay <= 0){
            // Send command to turn left
                myBoat.updateHeading(-delta);
                myBoat.updateVelocity();
                changed =true;

        } else if(input.isKeyDown(Input.KEY_D)
                && !anchor
                && !input.isKeyDown(Input.KEY_SPACE)
                && bounceDelay <= 0){
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
                        CannonBall ball = new CannonBall(myBoat.getX() + 10*5, myBoat.getY() + 10*5, reticle.getX(), reticle.getY(), +90, ThreadLocalRandom.current().nextInt(),myBoat.getPlayerID());
                        cannonBalls.put(ball.getId(),ball);
                        buildBallCommand(ball);
                        justFired = true;

                    }else if(!cannonsTargeting.getFireRight()&& leftDelay<=0){
                        CannonBall ball = new CannonBall(myBoat.getX() + 10*5, myBoat.getY() + 10*5, reticle.getX(), reticle.getY(), +90, ThreadLocalRandom.current().nextInt(),myBoat.getPlayerID());
                        cannonBalls.put(ball.getId(),ball);
                        buildBallCommand(ball);
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


        i=cannonBalls.entrySet().iterator();
        while (i.hasNext()){
            Map.Entry pair = (Map.Entry) i.next();
            CannonBall ball =cannonBalls.get(pair.getKey());
            Collision collision = ball.collides(myBoat);
            if(collision !=null
                    && ball.getPlayerID() != myBoat.getPlayerID()){
                if(Client.DEBUG) {
                    System.out.println("I got hit bois");
                }
                ballUpdater= new BallUpdater(myBoat.getPlayerID());
                ballUpdater.setBallID(ball.getId());
                ballUpdater.setIsDead(true);
                sendCommand(ballUpdater);
                ball.setDead(true);
            }

        }

        if(!notanIsland(myBoat.getHitbox()) && bounceDelay <= 0){
            float angle;

            float curHeading = Math.abs(myBoat.getHeading() % 360);

            float tilex = (int) myBoat.getX()/160;
            float tiley = (int) (myBoat.getY() + 48)/160;

            Vector tileCenter = new Vector(tilex * 160 + 16*5, tiley * 160 + 16*5);
            Vector boatPosition = new Vector(myBoat.getX(), myBoat.getY());

            float islandAngle = (float) boatPosition.angleTo(tileCenter);

            angle = bounceAngle(islandAngle, curHeading);

            bounce(angle);

            changed = true;
            bounceDelay = 1000;

        }


        wind.update(myBoat.getX()+800,myBoat.getY()+450);

        if(changed){
            ShipUpdater cmd = new ShipUpdater(Updates.getInstance().getThread().getClientId());
            cmd.setHeading(myBoat.getHeading());
            cmd.setVx(myBoat.getVelocity().getX());
            cmd.setVy(myBoat.getVelocity().getY());
            sendCommand(cmd);
        }

    }

    private void sendCommand(CommandObject cmd){
        try{
            Updates.getInstance().getThread().sendCommand(cmd);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void buildBallCommand(CannonBall ball) {
        BallUpdater cmd = new BallUpdater(myBoat.getPlayerID());
        cmd.setX(ball.getX());
        cmd.setY(ball.getY());
        cmd.setVx(ball.getVelocity().getX());
        cmd.setVy(ball.getVelocity().getY());
        cmd.setBallID(ball.getId());
        cmd.setTtl(ball.getTtl());
        cmd.setBallDestX(ball.getDestX());
        cmd.setBallDestY(ball.getDestY());
        sendCommand(cmd);
    }

    private boolean notanIsland(ConvexPolygon hitbox){

        float minX = myBoat.getX() + hitbox.getMinX();
        float maxX = myBoat.getX() + hitbox.getMaxX();
        float maxY = myBoat.getY() + hitbox.getMaxY();

        if(map.getTileId((int) minX/160, (int) maxY/160, islandLayer) != 0 &&
                map.getTileId((int) maxX/160, (int) maxY/160, islandLayer) != 0){


            return false;
        }

        return true;

    }

    public boolean isGameInProgress(){
        return false;
    }

    private float bounceAngle(float island, float boat){

        if(boat > island) {
            if(Client.DEBUG)
                System.out.println("Hit on the left");
            return (myBoat.getHeading() + 45);
        }else if (boat < island){
            if(Client.DEBUG)
                System.out.println("Hit on the right");
            return (myBoat.getHeading() - 45);
        }else {
            return myBoat.getHeading() - 180;
        }

    }

    private void bounce(float a){
        if(Client.DEBUG){
            System.out.println("Current heading: " + myBoat.getHeading() + " New heading: " +  a);
        }
        myBoat.setVelocity(myBoat.getVelocity().scale(-1));
        //myBoat.setVelocity(new Vector(myBoat.getVelocity().setRotation(a)));

    }


    public int getID(){return Client.PLAYINGSTATE; }
}
