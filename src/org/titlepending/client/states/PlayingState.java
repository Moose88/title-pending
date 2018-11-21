package org.titlepending.client.states;

import org.lwjgl.Sys;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.titlepending.client.Client;
import org.titlepending.client.Updates;
import org.titlepending.shared.Nuntius;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class PlayingState extends BasicGameState {
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {

    }

    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException{
        System.out.println("Made it to the playing state");
    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{

    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{
        Input input = container.getInput();


        Nuntius cmd = new Nuntius();
        if(input.isKeyDown(Input.KEY_W)){
            // Send raise anchor command to server
            if(Client.DEBUG)
                System.out.println("Sending command to raise anchor");
            cmd.setRaiseAncor();
            cmd.setId(Updates.getInstance().getId());
            sendCommand(cmd);
        }
        if(input.isKeyDown(Input.KEY_S)){
            // Send lower anchor command to server
            cmd.setLowerAncor();
            cmd.setId(Updates.getInstance().getId());
            sendCommand(cmd);
        }
        if(input.isKeyDown(Input.KEY_A)){
            // Send command to turn left
            cmd.setTurnLeft();
            cmd.setId(Updates.getInstance().getId());
            sendCommand(cmd);
        }
        if(input.isKeyDown(Input.KEY_D)){
            cmd.setTurnRight();
            cmd.setId(Updates.getInstance().getId());
            sendCommand(cmd);
        }

    }
    private void sendCommand(Nuntius cmd){
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
