package org.titlepending.client.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.titlepending.client.Client;
import org.titlepending.client.Updates;
import org.titlepending.shared.Directive;

public class WaitingState extends BasicGameState {

    public void init(GameContainer container, StateBasedGame game){

    }

    public void enter(GameContainer container, StateBasedGame game){
        if(Client.DEBUG)
            System.out.println("Waiting for other clients to send their ships");
    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g){

    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta){
        if(!Updates.getInstance().getQueue().isEmpty()){
            Directive cmd = Updates.getInstance().getQueue().poll();
            Client client = (Client) game;
            client.enterState(cmd.getStateTransition());
        }

    }

    public int getID(){return Client.WAITINGSTATE;}

}
