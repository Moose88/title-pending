package org.titlepending.client.states;

import jig.ResourceManager;
import org.lwjgl.Sys;
import org.newdawn.slick.*;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.FontUtils;
import org.titlepending.client.Client;

import java.io.IOException;

public class LoadState extends BasicGameState {

    private String lastLoaded;
    private int soFar = 1000;
    private int totalResources;
    private int remainingResources;

    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        LoadingList.setDeferredLoading(true);



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


    }

    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException{

    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{

        Client client = (Client)game;

        // Background splash for the loading screen
        g.drawImage(ResourceManager.getImage(Client.TEST_RSC), 0, 0);

        String statement = new String("Loaded: " + lastLoaded);

        int textWidth = client.fontStandard.getWidth(statement);

        g.drawString(statement, (client.ScreenWidth/2 - textWidth/2), client.ScreenHeight*.9f);

        g.setColor(Color.white);

        if(totalResources == 0)
            totalResources = 12;

        g.fillRect((client.ScreenWidth/totalResources) * (totalResources - remainingResources), 500, 100, 100);

    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{
        Client client = (Client)game;

        int runTimer = 1000;
        if(soFar >= runTimer) {

            if (LoadingList.get().getRemainingResources() > 0) {
                totalResources = LoadingList.get().getTotalResources();
                DeferredResource nextResource = LoadingList.get().getNext();

                totalResources = LoadingList.get().getTotalResources();
                remainingResources = LoadingList.get().getRemainingResources();

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
                client.enterState(Client.MAINMENUSTATE,new EmptyTransition(), new EmptyTransition());
            }
        }

        soFar += delta;
    }

    public int getID(){ return Client.LOADSTATE; }
}
