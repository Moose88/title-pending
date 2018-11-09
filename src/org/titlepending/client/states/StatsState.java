package org.titlepending.client.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

class StatsState extends StartState{
    /*
    * Things present on this page
    * Username
    * Most played Character
    * HighScore
    * Most used ship/sail/cannon
    * back button
    * */
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

        /*
        * render button*/
    }
    @Override
    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException {
        Input input = container.getInput();
        if (input.isKeyDown(Input.KEY_ENTER)) {
            //Go back to start state
        }
    }
}
