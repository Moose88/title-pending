package org.titlepending.client.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.titlepending.client.Client;
import org.titlepending.client.Updates;
import org.titlepending.shared.Directive;
import java.io.IOException;
import java.util.ArrayList;

import org.titlepending.entities.Ship;

public class PlayingState extends BasicGameState {
    private ArrayList<Ship> Ships;
    private Ship myBoat;



    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        Client client = (Client)game;




    }

    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException{
        this.Ships = Updates.getInstance().getShips();


        for(Ship ship : Ships) {
            if (ship.getPlayerID() == Updates.getInstance().getThread().getClientId())
                myBoat = ship;
        }

        for(Ship ship : Ships){
            ship.addSprites();
        }

        System.out.println("Made it to the playing state");
    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{
        Client client = (Client)game;
        int screenX = (int) myBoat.getX() - client.ScreenWidth/2;
        int screenY = (int) myBoat.getY() - client.ScreenHeight/2;

        for(Ship ship : Ships){
            ship.render(g);
        }


    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{

        Input input = container.getInput();


        Directive cmd = new Directive();
        if(input.isKeyDown(Input.KEY_W)){
            // Send raise anchor command to server
            if(Client.DEBUG)
                System.out.println("Sending command to raise anchor");
            cmd.setRaiseAnchor();
            sendCommand(cmd);
        }
        if(input.isKeyDown(Input.KEY_S)){
            // Send lower anchor command to server
            cmd.setLowerAnchor();
            sendCommand(cmd);
        }
        if(input.isKeyDown(Input.KEY_A)){
            // Send command to turn left
            cmd.setTurnLeft();
            sendCommand(cmd);
        }
        if(input.isKeyDown(Input.KEY_D)){
            cmd.setTurnRight();
            sendCommand(cmd);
        }

    }

    private void sendCommand(Directive cmd){
        cmd.setId(Updates.getInstance().getThread().getClientId());
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
