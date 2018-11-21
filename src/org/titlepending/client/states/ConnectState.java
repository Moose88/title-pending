package org.titlepending.client.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.titlepending.client.Client;
import org.titlepending.client.Updates;
import org.titlepending.shared.ClientThread;
import org.titlepending.shared.Nuntius;

import java.io.IOException;
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

        if(Client.DEBUG)
            System.out.println("Attempting to connect to server.");

        ClientThread thread;
        try{
            s = new Socket("localhost",Client.PORT);
            thread = new ClientThread(s,false);
            thread.start();
            Updates.getInstance().setThread(thread);
        } catch (IOException e){
            e.printStackTrace();
            client.enterState(Client.MAINMENUSTATE);
        }
        boolean done = false;

        if(Client.DEBUG)
             System.out.println("We got connected!");

        while (Updates.getInstance().getQueue().isEmpty());

        if (Client.DEBUG)
            System.out.println("Receiving Updates instance.");
        // This is what we're receiving
        Nuntius input = Updates.getInstance().getQueue().poll();


        if(Client.DEBUG && input != null)
            System.out.println("Received from server\nState transition: "+input.getStateTransition()+"\nid: "+input.getId());
        if(input!=null)
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
