package org.titlepending.client.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.titlepending.client.Client;

public class RejectedState extends BasicGameState{
    int timer;
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException{

    }

    public void enter(GameContainer container, StateBasedGame game)
        throws SlickException{
        timer = 30000;

    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{

    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{
        timer -= delta;
        if(timer<=0){
            Client client = (Client)game;
            client.enterState(Client.MAINMENUSTATE);
        }
    }

    public int getID(){return Client.REJECTSTATE; }


}
