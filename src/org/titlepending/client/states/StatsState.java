package org.titlepending.client.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.titlepending.client.Client;

public class StatsState extends BasicGameState {
    /*
    * Things present on this page
    * Username
    * Most played Character
    * HighScore
    * Most used ship/sail/cannon
    * back button
    * */
    Client client;
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {

    }
    @Override
    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException{

    }
    @Override
    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{
        client = (Client)game;
        /*
        * render button*/
    }
    @Override
    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException {
        Input input = container.getInput();
        if (input.isKeyDown(Input.KEY_ENTER)) {
            client.enterState(Client.MAINMENUSTATE,new FadeOutTransition(),new FadeInTransition());
        }
    }
    @Override
    public int getID() {
        return Client.STATSSTATE;
    }
}
