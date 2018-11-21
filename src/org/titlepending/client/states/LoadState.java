package org.titlepending.client.states;

import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.titlepending.client.Client;
import org.titlepending.client.boatGuy;

import java.io.IOException;

public class LoadState extends BasicGameState {

    private String lastLoaded;
    private int soFar = 2000;
    private int totalResources;
    private int remainingResources;
    public Vector boatPosition;
    public boatGuy boatDude;


    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        LoadingList.setDeferredLoading(true);



        boatPosition = new Vector(0, 500);

        //Load your resources here using ResourceManager
        ResourceManager.loadImage(Client.STARTUP_BANNER_RSC);
        ResourceManager.loadImage(Client.FRONT_MENU_RSC);

        ResourceManager.loadSound(Client.SOUND1);
        ResourceManager.loadSound(Client.SOUND2);
        ResourceManager.loadSound(Client.SOUND3);
        ResourceManager.loadSound(Client.SOUND4);
        ResourceManager.loadSound(Client.SOUND5);
        ResourceManager.loadSound(Client.SOUND6);
        ResourceManager.loadSound(Client.SOUND7);
        ResourceManager.loadSound(Client.SOUND8);
        ResourceManager.loadSound(Client.SOUND9);
        ResourceManager.loadSound(Client.SOUND10);

        ResourceManager.loadMusic(Client.TITLE_MUSIC);

    }

    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException{

        boatDude = new boatGuy();
        boatDude.movement(0);
        ResourceManager.getSound(Client.SCREAM_SOUND).loop(1.2f, 0.05f);
        ResourceManager.getSound(Client.LOADING_SOUND).loop();


    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{

        Client client = (Client)game;

        // Background splash for the loading screen
        g.drawImage(ResourceManager.getImage(Client.LOADING_SKY_RSC).getScaledCopy(client.ScreenWidth, client.ScreenHeight*5/7), 0, 0);

        String statement = new String("Loaded: " + lastLoaded);

        int textWidth = client.fontStandard.getWidth(statement);

        g.drawString(statement, (client.ScreenWidth/2 - textWidth/2), client.ScreenHeight*.9f);

        g.setColor(Color.white);

        if(totalResources == 0)
            totalResources = 12;

        //g.drawImage(ResourceManager.getImage(Client.LOADING_SKY_RSC));
        boatDude.render(g);

        g.drawImage(ResourceManager.getImage(Client.LOADING_SEA_RSC).getScaledCopy(client.ScreenWidth, client.ScreenHeight*5/7), 0, 200);


    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{
        Client client = (Client)game;


        //System.out.println("Expected x position = " + (float)(client.ScreenWidth/totalResources) * (totalResources - remainingResources));

        boatDude.update(delta);

        int runTimer;

        if(Client.DEBUG)
            runTimer = 0;
        else
            runTimer = 1000;
        if(soFar >= runTimer) {

            if (LoadingList.get().getRemainingResources() > 0) {
                totalResources = LoadingList.get().getTotalResources();
                DeferredResource nextResource = LoadingList.get().getNext();

                totalResources = LoadingList.get().getTotalResources();
                remainingResources = LoadingList.get().getRemainingResources();

                boatDude.movement((float)(client.ScreenWidth/totalResources) * (totalResources - remainingResources));



                try {
                    //Set a timer here
                    nextResource.load();
                    soFar = 0;
                    if(remainingResources == 0){
                        remainingResources = 1;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }



                lastLoaded = nextResource.getDescription();
            } else {
                // loading is complete, do normal update here
                //ResourceManager.getSound(Client.LOADING_SOUND).stop();
                ResourceManager.getSound(Client.SCREAM_SOUND).stop();
                ResourceManager.getMusic(Client.TITLE_MUSIC).loop();
                client.enterState(Client.MAINMENUSTATE,new EmptyTransition(), new EmptyTransition());
            }
        }

        soFar += delta;
    }

    public int getID(){ return Client.LOADSTATE; }
}
