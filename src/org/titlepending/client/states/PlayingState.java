package org.titlepending.client.states;

import jig.Vector;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.titlepending.client.Client;
import org.titlepending.client.Updates;
import org.titlepending.entities.ClientShip;
import org.titlepending.shared.Action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.titlepending.server.ServerObjects.Ship;

public class PlayingState extends BasicGameState {
    private HashMap<Integer, ClientShip> CShips;
    private ClientShip myBoat;
    private TiledMap map;
    private int cmdDelay;
    private boolean anchor;


    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        map = new TiledMap(Client.MAP_RSC);
        anchor = true;





    }

    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException{
        cmdDelay =0;
        HashMap<Integer,Ship> ships = Updates.getInstance().getShips();
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



        System.out.println("Made it to the playing state");
    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{
        Client client = (Client)game;
        int screenX = (int) myBoat.getX() - client.ScreenWidth/2;
        int screenY = (int) myBoat.getY() - client.ScreenHeight/2;

        g.translate(-screenX, -screenY);
        g.pushTransform();
        g.scale(5, 5);
        map.render(0,0);
        g.popTransform();

        Iterator i = CShips.entrySet().iterator();
        while (i.hasNext()){
            Map.Entry pair = (Map.Entry) i.next();
            ClientShip ship = CShips.get(pair.getKey());
            ship.render(g);
        }


    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{
        /** update all ships from server command before we do local updates **/
        while(!Updates.getInstance().getQueue().isEmpty()){
            Action cmd = (Action) Updates.getInstance().getQueue().poll();
            assert cmd !=null;

            ClientShip update = CShips.get(cmd.getUpdatedShip());
            update.setPosition(cmd.getX(),cmd.getY());
            update.setVelocity(new Vector(cmd.getVx(),cmd.getVy()));
        }
        myBoat.update(delta);
        cmdDelay -= delta;
        Input input = container.getInput();

        if(input.isKeyDown(Input.KEY_W)){
            // Send raise anchor command to server
            if(Client.DEBUG) {
                System.out.println("Sending command to raise anchor");
            }

            anchor = false;
            myBoat.updateVelocity();


        } else if(input.isKeyDown(Input.KEY_S)){
            // Send lower anchor command to server
            anchor = true;
            myBoat.updateVelocity(new Vector(0f,0f));


        }

        if(input.isKeyDown(Input.KEY_A)){
            // Send command to turn left
            if(!anchor) {
                myBoat.updateHeading(-delta);
                myBoat.updateVelocity();
            }


        } else if(input.isKeyDown(Input.KEY_D)){
            // Senc dommand to turn right
            if(!anchor) {
                myBoat.updateHeading(delta);
                myBoat.updateVelocity();
            }

        }

        if(input.isKeyDown(Input.KEY_SPACE)){

        }
        if(cmdDelay <= 0){
            if(Client.DEBUG)
                System.out.println("Sending vx: "+myBoat.getVelocity().getX()+" vy: "+myBoat.getVelocity().getY()+" heading: "+myBoat.getHeading());
            Action cmd = new Action(Updates.getInstance().getThread().getClientId());
            cmd.setHeading(myBoat.getHeading());
            cmd.setVx(myBoat.getVelocity().getX());
            cmd.setVy(myBoat.getVelocity().getY());
            sendCommand(cmd);
            cmdDelay =150;
        }


    }

    private void sendCommand(Action cmd){
        try{
            Updates.getInstance().getThread().sendCommand(cmd);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean isGameInProgress(){
        return false;
    }

    public int getID(){return Client.PLAYINGSTATE; }
}
