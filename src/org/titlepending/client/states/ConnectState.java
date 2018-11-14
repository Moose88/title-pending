package org.titlepending.client.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.titlepending.client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ConnectState extends BasicGameState {
    /**
     * Connecting State
     * - Waiting to connect
     *      idle screen
     *      add to render
     *
     * - Connected
     *      Go to lobby
     *
     * - No Server / Full Server
     *      Give error
     *      Go back to menu
     *
     */

    public void init(GameContainer container, StateBasedGame game)
            throws SlickException{

    }

    public void enter(GameContainer container, StateBasedGame game)
        throws SlickException{
        Client client = (Client) game;
        Socket s=null;
        ObjectInputStream input =null;
        System.out.println("Attempting to connect to server.");
        int response = -1;
        try{
            s = new Socket("localhost",Client.PORT);
            input = new ObjectInputStream(s.getInputStream());
            response = input.read();

        } catch (IOException e){
            e.printStackTrace();
            client.enterState(Client.MAINMENUSTATE);
        }
        if(response!=-1)
            client.enterState(response);

        System.out.println("Failed to establish connection");
        client.enterState(Client.MAINMENUSTATE);
    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{

    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{

    }
    public int getID(){return Client.CONNECTSTATE; }
}
