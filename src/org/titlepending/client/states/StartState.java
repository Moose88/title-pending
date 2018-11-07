package org.titlepending.client.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.titlepending.client.Client;

public class StartState extends BasicGameState {
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {

    }

    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException{

    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{

    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{
        Client client = (Client) game;
        Input input = container.getInput();
        if(input.isKeyDown(Input.KEY_SPACE))
            client.enterState(Client.CONNECTSTATE,new EmptyTransition(), new FadeOutTransition());

    }
    public int getID(){return Client.STARTUPSTATE; }
}
