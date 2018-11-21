package org.titlepending.client.states;

import org.newdawn.slick.BasicGame;
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
import org.titlepending.entities.Ship;

public class PlayingState extends BasicGameState {
    Ship player;
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
            player = new Ship(250,250,0,0);
    }

    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException{
        System.out.println("Made it to the playing state");
    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{
        player.render(g);
    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{

        Input input = container.getInput();
        player.update(delta);

        Nuntius cmd = new Nuntius();
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
    private void sendCommand(Nuntius cmd){
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
