package org.titlepending.client.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.titlepending.client.Client;
import org.titlepending.client.Updates;
import org.titlepending.client.menus.BaseMenuState;
import org.titlepending.shared.ClientThread;
import org.titlepending.shared.Initializer;

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

    private ClientThread thread;
    private boolean connectSuccess;
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException{
        thread = null;
        connectSuccess = false;

    }

    public void enter(GameContainer container, StateBasedGame game)
        throws SlickException{
        Client client = (Client) game;
        Socket s;


        if(Client.DEBUG)
            System.out.println("Attempting to connect to server.");
        try{
            s = new Socket(BaseMenuState.isIP,Client.PORT);
            thread = new ClientThread(s,false);
            thread.start();
            Updates.getInstance().setThread(thread);
            connectSuccess = true;
        } catch (IOException e) {
            if (Client.DEBUG)
                System.out.println("Connection Rejected");
            if (thread != null)
                thread.stopThread();
            client.enterState(Client.REJECTSTATE);
            connectSuccess = false;
        }

    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{

    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{


        Client client = (Client) game;
        if(!connectSuccess)
            client.enterState(Client.REJECTSTATE);
        else
            while (Updates.getInstance().getQueue().isEmpty()); //wait for input from server

        Initializer input = (Initializer) Updates.getInstance().getQueue().poll();

        if(input!=null) {
            if(Client.DEBUG)
                System.out.println("Received from server\nState transition: "+input.getStateTransition()+"\nid: "+input.getId());
            if(input.getStateTransition() == Client.LOBBYSTATE) {
                thread.setClientId(input.getId());
                client.enterState(Client.LOBBYSTATE, new EmptyTransition(), new FadeInTransition());
            }else{
                if(thread!=null)
                    thread.stopThread();
                System.out.println("Server full");
                client.enterState(Client.REJECTSTATE, new EmptyTransition(), new FadeInTransition());
            }
        }

    }
    public int getID(){return Client.CONNECTSTATE; }
}
