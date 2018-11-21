package org.titlepending.client.states;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.titlepending.client.Client;
import org.titlepending.entities.Ship;

public class PlayingState extends BasicGameState {
    Ship player;
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
            player = new Ship(250,250,0,0);
    }

    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException{
        System.out.println("Made it here");
    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{
        player.render(g);
    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{
        player.update(delta);

    }

    public boolean isGameInProgress(){
        return false;
    }

    public int getID(){return Client.PLAYINGSTATE; }
}
