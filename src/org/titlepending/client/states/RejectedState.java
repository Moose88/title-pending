package org.titlepending.client.states;

import jig.ResourceManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.titlepending.client.Client;
import org.titlepending.client.Updates;

public class RejectedState extends BasicGameState{

    private int timer;
    private  Client client;
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException{
        this.client= (Client) game;
    }

    public void enter(GameContainer container, StateBasedGame game)
        throws SlickException{
        timer = 30000;
        ResourceManager.getMusic(Client.HTP_MUSIC).loop();
        if(Updates.getInstance().getThread()!=null)
            Updates.getInstance().getThread().stopThread();

    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{
        Client client = (Client) game;

        g.drawImage(ResourceManager.getImage(Client.FRONT_MENU_RSC).getScaledCopy(3f), 0, 0);
        String string1 = "Server is full, or does not exist.";
        String string2 = "Please press any button to return to the front Menu.";

        g.setFont(client.fontMenu);
        g.setColor(Color.black);
        g.drawString(string1, client.ScreenWidth/2f - g.getFont().getWidth(string1)/2f, client.ScreenHeight/2f - g.getFont().getHeight(string1)/2f);
        g.drawString(string2, client.ScreenWidth/2f - g.getFont().getWidth(string2)/2f, client.ScreenHeight/2f + g.getFont().getHeight(string2)/2f);

    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{
        timer -= delta;
        if(timer<=0){
            Client client = (Client)game;
            ResourceManager.getMusic(Client.HTP_MUSIC).stop();
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
