package org.titlepending.client.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.titlepending.client.Client;

public class WinningState extends BasicGameState {
    int timer;
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        timer = 5000;
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        if(Client.DEBUG){
            System.out.println("I'm da bes");
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Client client = (Client) game;
        timer-=delta;
        if(timer<=0){
            client.enterState(Client.MAINMENUSTATE);
        }
    }

    @Override
    public int getID() {
        return Client.WINNINGSTATE;
    }
}
