package org.titlepending.client.states;

import jig.ResourceManager;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.titlepending.client.Client;
import org.titlepending.client.Updates;

public class RejectedState extends BasicGameState{

    private Client client;
    private int timer;

    public void init(GameContainer container, StateBasedGame game)
            throws SlickException{
        this.client = (Client)game;

    }

    public void enter(GameContainer container, StateBasedGame game)
        throws SlickException{
        timer = 30000;

    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{
        Client client = (Client) game;

        g.drawImage(ResourceManager.getImage(Client.FRONT_MENU_RSC).getScaledCopy(3f), 0, 0);
        String string1 = "Server is full, or does not exist.";
        String string2 = "Please press any button to return to the front Menu.";

        g.setFont(client.fontMenu);
        g.setColor(Color.black);
        g.drawString(string1, client.ScreenWidth/2 - g.getFont().getWidth(string1)/2, client.ScreenHeight/2 - g.getFont().getHeight(string1)/2);
        g.drawString(string2, client.ScreenWidth/2 - g.getFont().getWidth(string2)/2, client.ScreenHeight/2 + g.getFont().getHeight(string2)/2);

    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{
        timer -= delta;
        if(timer<=0){
            Client client = (Client)game;
            ResourceManager.getMusic(Client.TITLE_MUSIC).loop();
            client.enterState(Client.MAINMENUSTATE);
        }
    }

    public int getID(){return Client.REJECTSTATE; }

    @Override
    public void keyPressed(int key, char c){
        if(true){
            System.out.println(key);
            ResourceManager.getMusic(Client.TITLE_MUSIC).loop();
            client.enterState(Client.MAINMENUSTATE);
        }
    }

}
