package org.titlepending.client;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.titlepending.client.states.*;

public class Client extends StateBasedGame {

    public static final int STARTUPSTATE = 0;
    public static final int CONNECTSTATE = 1;
    public static final int PLAYINGSTATE = 2;
    public static final int LOADSTATE = 3;
    public static final int GAMEOVERSTATE = 4;
    public static final int PORT = 8000;
    public Client(String title, int width , int height){
        super(title);
        addState(new StartState());
        addState(new ConnectState());
        addState(new PlayingState());
        addState(new LoadState());
        addState(new GameOverState());
    }

    public void initStatesList(GameContainer container) throws SlickException{

    }

    public static void main(String[] args){
        AppGameContainer app;
        try{
            app = new AppGameContainer(new Client("Title Pending",1920,1080));
            app.setDisplayMode(1920,1080,false);
            app.start();
        }catch (SlickException e){

        }
    }
}
