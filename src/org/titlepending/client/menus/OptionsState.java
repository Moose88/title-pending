package org.titlepending.client.menus;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;
import org.titlepending.client.Client;

public class OptionsState extends BaseMenuState {


    private final static int FULLSCREEN = 0;
    private final static int DIMENSION = 1;
    private final static int IPADDRESS = 2;
    private final static int BACK = 3;

    private GameContainer container;
    private SavedState savedState;

    private String[] resolution = new String[5];

    private int isResolution;
    private int isFullScreen;

    private TextField input;

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        resolution[0] = "< 4k >";
        resolution[1] = "< 1920 x 1080 >";
        resolution[2] = "< 1280 x 720 >";
        resolution[3] = "< 800 x 600 >";
        resolution[4] = "< 640 x 480 >";

        this.client = (Client) game;
        this.items = 4;
        this.selection = 0;
        this.isResolution = 1;
        isIP = "localhost";
        this.backstate = Client.MAINMENUSTATE;
        this.container = container;

        savedState = new SavedState("options");

        isResolution = (int)savedState.getNumber("resolution",1);
        isFullScreen = (int)savedState.getNumber("fullScreen",0);
        isIP = savedState.getString("ipaddress", "localhost");

        if(isFullScreen == 1){
            if(Client.DEBUG)
                System.out.println("Setting Fullscreen");
            container.setFullscreen(true);
        } else {
            container.setFullscreen(false);
        }

        input = new TextField(container, client.fontMenu,
                ((client.ScreenWidth/2)+(client.fontMenu.getWidth("IP Address: ")/2))-250,
                (int)(client.ScreenHeight * 0.6)+ 98*2, client.fontMenu.getWidth("IPADDRESS"),
                client.fontMenu.getLineHeight()-20, source -> {
                    typing = false;
                    isIP = input.getText();
                    if(Client.DEBUG)
                        System.out.println("Enter pressed: isIP = " + isIP);
                    input.setCursorVisible(false);
                    input.setAcceptingInput(false);
                    input.setBackgroundColor(Color.darkGray);
                    input.setFocus(false);
                    savedState.setString("ipaddress", input.getText());
                    save();
                });

        if(isIP.equals("localhost")){
            input.setText("localhost");
        } else {
            input.setText(isIP);
        }

        input.setMaxLength(15);
        input.setFocus(false);
        input.setBorderColor(Color.black);
        input.setAcceptingInput(false);
        input.setBackgroundColor(Color.darkGray);

        if(isResolution == 0){
            if(Client.DEBUG)
                System.out.println("Setting to 4k");
            Client.app.setDisplayMode(3840,2160, Client.app.isFullscreen());
        } else if(isResolution == 1){
            if(Client.DEBUG)
                System.out.println("Setting 1920 x 1080");
            Client.app.setDisplayMode(1920, 1080, Client.app.isFullscreen());
        } else if(isResolution == 2) {
            if(Client.DEBUG)
                System.out.println("Setting 1280 x 720");
            Client.app.setDisplayMode(1280, 720, Client.app.isFullscreen());
        } else if(isResolution == 3){
            if(Client.DEBUG)
                System.out.println("Setting 800 x 600");
            Client.app.setDisplayMode(800, 600, Client.app.isFullscreen());
        } else if(isResolution == 4){
            if(Client.DEBUG)
                System.out.println("Setting 640 x 480");
            Client.app.setDisplayMode(640, 480, Client.app.isFullscreen());
        }
    }

    private void save() {
        try {
            savedState.save();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isSelected(int option){
        return selection == option;
    }


    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        super.render(container, client, g);

        // Draw menu
        int yTop = (int) (this.client.ScreenHeight * 0.6); // one third down the string
        int itemSpace = 95;

        if(isFullScreen == 1)
            drawMenuItem("Switch To Windowed",yTop+FULLSCREEN*itemSpace,isSelected(FULLSCREEN));
        else
            drawMenuItem("Switch to Fullscreen",yTop+FULLSCREEN*itemSpace,isSelected(FULLSCREEN));

        drawMenuItem("IP Address: " , yTop+IPADDRESS*itemSpace, isSelected(IPADDRESS));

        input.render(container, g);

        drawMenuItem("Resolution: " + resolution[isResolution], yTop+DIMENSION*itemSpace,isSelected(DIMENSION));

        drawMenuItem("Back",yTop+BACK*itemSpace,isSelected(BACK));
    }

    public void update(GameContainer container, StateBasedGame game, int delta){
        // Nothing to do here



    }

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key,c);

        if(key == Input.KEY_LEFT && selection == DIMENSION){
            if(isResolution >= 4)
                isResolution = 0;
            else
                isResolution++;

        }

        if(key == Input.KEY_RIGHT && selection == DIMENSION){
            if(isResolution <= 0)
                isResolution = 4;
            else
                isResolution--;
        }

        if(key == Input.KEY_ENTER){
            switch(selection){
                case FULLSCREEN:
                    try {
                        container.setFullscreen(!container.isFullscreen());
                        System.out.println(container.isFullscreen());
                        if(container.isFullscreen())
                            isFullScreen = 1;
                        else
                            isFullScreen = 0;
                        savedState.setNumber("fullScreen", isFullScreen);
                        save();
                    } catch (SlickException e){
                        e.printStackTrace();
                    }
                    break;
                case DIMENSION:
                    if(isResolution == 0){
                        try {
                            Client.app.setDisplayMode(3840, 2160, container.isFullscreen());
                            savedState.setNumber("resolution", isResolution);
                            save();
                        } catch (SlickException e) {
                            e.printStackTrace();
                        }
                    } else if(isResolution == 1) {
                        try {
                            Client.app.setDisplayMode(1920, 1080, container.isFullscreen());
                            savedState.setNumber("resolution", isResolution);
                            save();
                        } catch (SlickException e) {
                            e.printStackTrace();
                        }
                    } else if(isResolution == 2) {
                        try {
                            Client.app.setDisplayMode(1280, 720, container.isFullscreen());
                            savedState.setNumber("resolution", isResolution);
                            save();
                        } catch (SlickException e) {
                            e.printStackTrace();
                        }
                    } else if(isResolution == 3) {
                        try {
                            Client.app.setDisplayMode(800, 600, container.isFullscreen());
                            savedState.setNumber("resolution", isResolution);
                            save();
                        } catch (SlickException e) {
                            e.printStackTrace();
                        }
                    } else if(isResolution == 4) {
                        try {
                            Client.app.setDisplayMode(640, 480, container.isFullscreen());
                            savedState.setNumber("resolution", isResolution);
                            save();
                        } catch (SlickException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case IPADDRESS:
                    typing = true;
                    input.setBackgroundColor(Color.lightGray);
                    input.setCursorPos(input.getText().length());
                    input.setCursorVisible(true);
                    input.setFocus(true);
                    input.setAcceptingInput(true);
                    // Allow modification of the text box
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
