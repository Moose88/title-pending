package org.titlepending.client;

import jig.Entity;
import jig.ResourceManager;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;
import org.titlepending.client.menus.MenuState;
import org.titlepending.client.menus.OptionsState;
import org.titlepending.client.states.*;

import java.awt.Color;
import java.awt.Font;

public class Client extends StateBasedGame {
    public static final boolean DEBUG = true;
    public static final int PORT = 8000;
    public static final int LOADSTATE = 0;
    public static final int CONNECTSTATE = 1;
    public static final int PLAYINGSTATE = 2;
    public static final int WAITINGSTATE = 3;
    public static final int GAMEOVERSTATE = 4;
    public static final int MAINMENUSTATE = 5;
    public static final int STATSSTATE = 6;
    public static final int OPTIONSMENUSTATE = 7;
    public static final int LOBBYSTATE = 8;
    public static final int REJECTSTATE= 9;

    /**
     * These are all the game resources to include-
     * Images, sounds, and music
     */

    // Map
    public static final String MAP_RSC = "org/titlepending/resources/images/Map.tmx";

    // Backgrounds and images
    public static final String STARTUP_BANNER_RSC = "org/titlepending/resources/startstatebackground.png";
    public static final String LOADING_SKY_RSC = "org/titlepending/resources/LoadSky.png";
    public static final String LOADING_SEA_RSC = "org/titlepending/resources/LoadOcean.png";
    public static final String SHIP_RSC = "org/titlepending/resources/images/ShipSS.png";
    public static final String FRONT_MENU_RSC = "org/titlepending/resources/bgnd.png";
    private static final String TEST_RSC = "org/titlepending/resources/PVCwAb3.png";
    public static final String CHARACTER_RSC = "org/titlepending/resources/images/Characters.png";

    // Fonts
    private static final String FONT_RSC = "org/titlepending/resources/Treamd.ttf";

    // Music
    public static final String TITLE_MUSIC = "org/titlepending/resources/TitleMusic.wav";
    public static final String LOBBY_MUSIC = "org/titlepending/resources/lobby_music.wav";
    public static final String HTP_MUSIC = "org/titlepending/resources/HTP_Music.wav";

    // Sounds
    public static final String LOADING_SOUND = "org/titlepending/resources/WaveSound.wav";
    public static final String SCREAM_SOUND = "org/titlepending/resources/AAAGH1.wav";
    public static final String MENU_CLICK = "org/titlepending/resources/MenuClick.wav";

    // Testing resources go here
    public static final String SOUND1 = "org/titlepending/resources/explosion sounds/Explosion1.wav";
    public static final String SOUND2 = "org/titlepending/resources/explosion sounds/Explosion2.wav";
    public static final String SOUND3 = "org/titlepending/resources/explosion sounds/Explosion3.wav";
    public static final String SOUND4 = "org/titlepending/resources/explosion sounds/Explosion4.wav";
    public static final String SOUND5 = "org/titlepending/resources/explosion sounds/Explosion5.wav";
    public static final String SOUND6 = "org/titlepending/resources/explosion sounds/Explosion6.wav";
    public static final String SOUND7 = "org/titlepending/resources/explosion sounds/Explosion7.wav";
    public static final String SOUND8 = "org/titlepending/resources/explosion sounds/Explosion8.wav";
    public static final String SOUND9 = "org/titlepending/resources/explosion sounds/Explosion9.wav";
    public static final String SOUND10 = "org/titlepending/resources/explosion sounds/Explosion10.wav";

    public static UnicodeFont fontStandard;
    public UnicodeFont fontMenu;

    public final int ScreenWidth;
    public final int ScreenHeight;

    public static AppGameContainer app;

    public Client(String title, int width , int height){
        super(title);

        ScreenHeight = height;
        ScreenWidth = width;
        Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);

    }

    @Override
    public void initStatesList(GameContainer container){

        ResourceManager.setFilterMethod(ResourceManager.FILTER_NEAREST);

        // Resources being used/during loading
        ResourceManager.loadImage(TEST_RSC);
        ResourceManager.loadImage(SHIP_RSC);
        ResourceManager.loadImage(LOADING_SEA_RSC);
        ResourceManager.loadImage(LOADING_SKY_RSC);
        ResourceManager.loadSound(LOADING_SOUND);
        ResourceManager.loadSound(SCREAM_SOUND);
        ResourceManager.loadSound(MENU_CLICK);

        addState(new LoadState());
        addState(new MenuState());
        addState(new WaitingState());
        addState(new ConnectState());
        addState(new PlayingState());
        addState(new GameOverState());
        addState(new HowToPlay());
        addState(new OptionsState());
        addState(new LobbyState());
        addState(new RejectedState());

        try{
            Font UIFont0 = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream(Client.FONT_RSC));
            Font UIFont1 = UIFont0.deriveFont(Font.PLAIN, 30.f); //You can change "PLAIN" to "BOLD" or "ITALIC"... and 16.f is the size of your font
            Font UIFont2 = UIFont0.deriveFont(Font.PLAIN,100f);

            //This font is the standard font for the rest of the game
            fontStandard = new UnicodeFont(UIFont1);
            fontStandard.addAsciiGlyphs();
            //fontStandard.getEffects().add(new OutlineEffect(1, Color.black));
            fontStandard.getEffects().add(new ColorEffect(Color.white)); //You can change your color here, but you can also change it in the render{ ... }
            fontStandard.addAsciiGlyphs();
            fontStandard.loadGlyphs();

            //This font works for the front menu
            fontMenu = new UnicodeFont(UIFont2);
            fontMenu.addAsciiGlyphs();
            //fontMenu.getEffects().add(new OutlineEffect(10, Color.black));
            fontMenu.getEffects().add(new ColorEffect(Color.white)); //You can change your color here, but you can also change it in the render{ ... }
            fontMenu.addAsciiGlyphs();
            fontMenu.loadGlyphs();

        }catch(Exception e){
            e.printStackTrace();
        }
        Updates.getInstance();


    }

    public static void main(String[] args){

        try{
            app = new AppGameContainer(new ScalableGame(new Client("Title Pending!!", 1920, 1080),1920,1080, true));
            app.setDisplayMode(1920 ,1080,false);
            app.start();

        }catch (SlickException ignored){

        }
    }
}
