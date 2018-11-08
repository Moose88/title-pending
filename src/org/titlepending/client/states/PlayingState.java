package org.titlepending.client.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.titlepending.client.Client;

public class PlayingState extends BasicGameState {
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {

    }

    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException{
        System.out.println("Made it here");
    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{

    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{

    }
    public int getID(){return Client.PLAYINGSTATE; }
}
