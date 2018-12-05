package org.titlepending.client.states;

import jig.ResourceManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.titlepending.client.Client;
import org.titlepending.client.Updates;
import org.titlepending.client.boatGuy;
import org.titlepending.shared.Directive;

public class WaitingState extends BasicGameState {

    private boatGuy boatDude;

    public void init(GameContainer container, StateBasedGame game){

    }

    public void enter(GameContainer container, StateBasedGame game){
        Client client = (Client) game;
        if(Client.DEBUG)
            System.out.println("Waiting for other clients to send their ships");
        boatDude = new boatGuy();
        boatDude.speed = 1;
        boatDude.movement(250);
        ResourceManager.getSound(Client.SCREAM_SOUND).loop(2f, 0.5f);
    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g){
        Client client = (Client) game;

        g.drawImage(ResourceManager.getImage(Client.FRONT_MENU_RSC).getScaledCopy(3f), 0, 0);
        String string1 = "Game world is building, please wait...";

        g.setFont(client.fontMenu);
        g.setColor(Color.black);
        g.drawString(string1, client.ScreenWidth/2f - g.getFont().getWidth(string1)/2f, client.ScreenHeight/2f - g.getFont().getHeight(string1)/2f);

        boatDude.render(g, client.ScreenHeight* 6/10);

    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta){

        boatDude.movement(boatDude.getX() + 300);
        boatDude.update(delta);

        if(!Updates.getInstance().getQueue().isEmpty()){
            Directive cmd = Updates.getInstance().getQueue().poll();
            Client client = (Client) game;
            Updates.getInstance().setShips(cmd.getShips());
            ResourceManager.getSound(Client.SCREAM_SOUND).stop();
            client.enterState(cmd.getStateTransition());
        }

    }

    public int getID(){return Client.WAITINGSTATE;}

}
