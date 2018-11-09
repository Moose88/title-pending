package org.titlepending.client.states;

import jig.ResourceManager;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;
import org.titlepending.Menus.BaseMenuState;
import org.titlepending.client.Client;

import java.awt.*;
import java.awt.Color;
import java.awt.Font;

public class StartState extends BasicGameState {
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        Client client = (Client) game;




    }

    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException{

    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{
        Client client = (Client) game;

        g.drawImage(ResourceManager.getImage(Client.STARTUP_BANNER_RSC), 0, 0);

        // Create background here



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
