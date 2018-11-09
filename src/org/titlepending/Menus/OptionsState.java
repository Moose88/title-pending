package org.titlepending.Menus;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import jig.ResourceManager;
import org.titlepending.client.Client;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class OptionsState extends BaseMenuState {

    private final static int FULLSCREEN = 0;
    private final static int BACK =1;
    private GameContainer container;
    private SavedState savedState;

    private int isFullScreen;

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.client = (Client) game;
        this.items = 2;
        this.selection = 0;
        this.backstate = Client.MAINMENUSTATE;
        this.container = container;
        savedState = new SavedState("options");

        isFullScreen = (int)savedState.getNumber("fullScreen",1                                                                                                                                                                                                                                                                                                             );
        if(isFullScreen==1){
            System.out.println("Setting Fullscreen");
            container.setFullscreen(true);
        } else {
            container.setFullscreen(false);
        }
    }

    public void save() {
        try {
            savedState.save();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isSelected(int option){
        if(selection == option)
            return true;
        return false;
    }

    @Override
    public void render(GameContainer container, StateBasedGame _game, Graphics g) throws SlickException {
        super.render(container, client, g);

        // Draw menu
        int yTop = (int) (this.client.ScreenHeight * 0.3); // one third down the string
        int itemSpace = 95;

        if(isFullScreen == 1)
            drawMenuItem("Switch To Windowed",yTop+FULLSCREEN*itemSpace,isSelected(FULLSCREEN));
        else
            drawMenuItem("Switch to Fullscreen",yTop+FULLSCREEN*itemSpace,isSelected(FULLSCREEN));

        drawMenuItem("Back",yTop+BACK*itemSpace,isSelected(BACK));
    }

    public void update(GameContainer container, StateBasedGame game, int delta){
        // Nothing to do here

    }

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key,c);

        if(key == Input.KEY_ENTER){
            switch(selection){
                case FULLSCREEN:
                    try {
                        container.setFullscreen(!container.isFullscreen());
                        if(container.isFullscreen())
                            isFullScreen = 1;
                        else
                            isFullScreen = 0;
                        savedState.setNumber("fullScreen",isFullScreen);
                        save();
                    } catch (SlickException e){
                        e.printStackTrace();
                    }
                    break;
                case BACK:
                    backPressed();
                    break;
                default:
                    System.out.println("Should not happen");
                    break;
            }
        }
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        //ResourceManager.getMusic(WizzardGame.MUSIC_RSC()[3]).play();
    }


    @Override
    public int getID() {
        return Client.OPTIONSMENUSTATE;
    }
}
