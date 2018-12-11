package org.titlepending.client.states;

import jig.ResourceManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.titlepending.client.Client;

public class GameOverState extends BasicGameState {

    private int timer;
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {

    }

    @Override
    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException {
        if(Client.DEBUG){
           System.out.println("I died");
        }
        timer = 5000;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException {

        Client client = (Client) game;

        // Background
        g.drawImage(ResourceManager.getImage(Client.FRONT_MENU_RSC).getScaledCopy(3f), 0, 0);

        // String
        String statement = "You've been sunk! D:";
        int textWidth = client.fontMenu.getWidth(statement);
        g.setFont(client.fontMenu);
        g.setColor(Color.black);

        g.drawString(statement, (client.ScreenWidth/2 - textWidth/2), client.ScreenHeight/2);

    }

    @Override
    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException {
        Client client = (Client) game;
        timer-=delta;
        if(timer <= 0){
           client.enterState(Client.MAINMENUSTATE);
        }
    }

    @Override
    public int getID() {
        return Client.GAMEOVERSTATE;
    }
}
