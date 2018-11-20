package org.titlepending.client.states;

import org.lwjgl.Sys;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.titlepending.client.Client;
import org.titlepending.client.Updates;
import org.titlepending.shared.ClientThread;
import org.titlepending.shared.Nuntius;

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
        Socket s;
        System.out.println("Attempting to connect to server.");
        int response = -1;
        try{
            s = new Socket("localhost",Client.PORT);
            new ClientThread(s,false).start();
            /** TODO: Find a way to save the socket singleton to each client
             *
             *  That way we can save that socket information for input
             *  and outputting object information
             */

        } catch (IOException e){
            e.printStackTrace();
            client.enterState(Client.MAINMENUSTATE);
        }
        boolean done = false;
        System.out.println("We got connected!");
        while (Updates.getInstance().getQueue().isEmpty()){}

        System.out.println("Receiving Updates instance.");
        // This is what we're receiving
        Nuntius input = Updates.getInstance().getQueue().remove();

        //System.out.println("Failed to establish connection");
        client.enterState(input.getStateTransition());
    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{

    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{

    }
    public int getID(){return Client.CONNECTSTATE; }
}
