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
import java.util.ArrayList;

import org.titlepending.server.ServerObjects.Ship;

public class PlayingState extends BasicGameState {
    private ArrayList<ClientShip> CShips;
    private ClientShip myBoat;
    private TiledMap map;
    private int cmdDelay;


    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {





    }

    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException{
        cmdDelay =0;
        ArrayList<Ship> Ships = Updates.getInstance().getShips();
        this.CShips = new ArrayList<>();
        //map = new TiledMap(Client.MAP_RSC);
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

        //map.render(0,0);
        g.translate(-screenX, -screenY);

        for(ClientShip ship : CShips){
            ship.render(g);
        }


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
            myBoat.setVelocity(myBoat.getSailVector());


        } else if(input.isKeyDown(Input.KEY_S)){
            // Send lower anchor command to server
            myBoat.setVelocity(new Vector(0f,0f));


        }

        if(input.isKeyDown(Input.KEY_A)){
            // Send command to turn left
            myBoat.getVelocity().rotate((double) delta);

        } else if(input.isKeyDown(Input.KEY_D)){
            // Senc dommand to turn right
            myBoat.getVelocity().rotate(-(double) delta);

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

        if(Client.DEBUG)
            System.out.println("Position x: " + myBoat.getX() + " Position y: " + myBoat.getY());
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
