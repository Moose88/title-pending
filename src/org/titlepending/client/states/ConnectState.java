package org.titlepending.client.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.titlepending.client.Client;
import org.titlepending.client.Updates;
import org.titlepending.client.menus.BaseMenuState;
import org.titlepending.shared.ClientThread;
import org.titlepending.shared.Directive;

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

    public void init(GameContainer container, StateBasedGame game)
            throws SlickException{
        thread = null;

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
        Directive input = Updates.getInstance().getQueue().poll();


        if(Client.DEBUG && input != null)
            System.out.println("Received from server\nState transition: "+input.getStateTransition()+"\nid: "+input.getId());
        if(input!=null)
            if(input.getStateTransition() == Client.LOBBYSTATE) {
                client.enterState(Client.LOBBYSTATE, new EmptyTransition(), new FadeInTransition());
            }else{
                thread.stopThread();
                System.out.println("Server full");
                client.enterState(Client.MAINMENUSTATE, new EmptyTransition(), new FadeInTransition());
            }
    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{

    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{

    }
    public int getID(){return Client.CONNECTSTATE; }
}
