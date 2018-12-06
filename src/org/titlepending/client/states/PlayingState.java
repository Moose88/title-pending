package org.titlepending.client.states;

import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.titlepending.client.Client;
import org.titlepending.client.Updates;
import org.titlepending.entities.ClientShip;
import org.titlepending.shared.Action;

import java.io.IOException;
import java.util.ArrayList;

import org.titlepending.server.ServerObjects.Ship;

public class PlayingState extends BasicGameState {
    private ArrayList<ClientShip> CShips;
    private ClientShip myBoat;
    private TiledMap map;
    private int cmdDelay;
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



    }

    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException{


        cmdDelay =0;
        ArrayList<Ship> Ships = Updates.getInstance().getShips();
        this.CShips = new ArrayList<>();
        if(Client.DEBUG){
            System.out.println("Attepting to create tiled map from: "+Client.MAP_RSC);
        }
        if(Client.DEBUG)
            System.out.println("Before myBoat thread ID: " + Updates.getInstance().getThread().getClientId());


        for(Ship ship : Ships){
            if(Client.DEBUG) System.out.println("Ship x "+ship.getX()+ "Ship y "+ship.getY());
            ClientShip temp =new ClientShip(ship.getX(), ship.getY(), ship.getPlayerID());
            temp.setStats(ship.getStats());
            temp.addSprites();
            CShips.add(temp);

        }

        for(ClientShip ship : CShips) {
            if (ship.getPlayerID() == Updates.getInstance().getThread().getClientId()) {
                myBoat = ship;
            }
        }

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

        for(ClientShip ship : CShips){
            ship.render(g);
        }

        g.translate(screenX, screenY);

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
            Action cmd = new Action(Updates.getInstance().getThread().getClientId());
            cmd.setX(myBoat.getX());
            cmd.setY(myBoat.getY());
            cmd.setVX(myBoat.getVelocity().getX());
            cmd.setVY(myBoat.getVelocity().getY());
            sendCommand(cmd);
            cmdDelay =100;
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
