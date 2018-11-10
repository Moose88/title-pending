package org.titlepending.client;

import jig.ResourceManager;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineEffect;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;
import org.titlepending.menus.MenuState;
import org.titlepending.menus.OptionsState;
import org.titlepending.client.states.*;

import java.awt.Color;
import java.awt.Font;

public class Client extends StateBasedGame {

    public static final int STARTUPSTATE = 0;
    public static final int CONNECTSTATE = 1;
    public static final int PLAYINGSTATE = 2;
    public static final int LOADSTATE = 3;
    public static final int GAMEOVERSTATE = 4;
    public static final int MAINMENUSTATE = 5;
    public static final int STATSSTATE = 6;
    public static final int OPTIONSMENUSTATE = 7;

    public static final String STARTUP_BANNER_RSC = "org/titlepending/resources/startstatebackground.png";
    public static final String FRONT_MENU_RSC = "org/titlepending/resources/bgnd.png";
    public static final String FONT_RSC = "org/titlepending/resources/Treamd.ttf";

    public static UnicodeFont fontStandard;
    public UnicodeFont fontMenu;

    public final int ScreenWidth;
    public final int ScreenHeight;

    public static AppGameContainer app;

    public Client(String title, int width , int height) throws SlickException{
        super(title);

        ScreenHeight = height;
        ScreenWidth = width;


    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException{
        addState(new MenuState());
        addState(new StartState());
        addState(new ConnectState());
        addState(new PlayingState());
        addState(new LoadState());
        addState(new GameOverState());
        //addState(new StatsState());
        addState(new OptionsState());

        ResourceManager.loadImage(STARTUP_BANNER_RSC);
        ResourceManager.loadImage(FRONT_MENU_RSC);

        try{
            Font UIFont0 = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream(Client.FONT_RSC));
            Font UIFont1 = UIFont0.deriveFont(Font.PLAIN, 30.f); //You can change "PLAIN" to "BOLD" or "ITALIC"... and 16.f is the size of your font
            Font UIFont2 = UIFont0.deriveFont(Font.PLAIN,100f);

            //This font is the standard font for the rest of the game
            fontStandard = new UnicodeFont(UIFont1);
            fontStandard.addAsciiGlyphs();
            fontStandard.getEffects().add(new OutlineEffect(5, Color.black));
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


    }

    public static void main(String[] args){

        try{
            app = new AppGameContainer(new ScalableGame(new Client("Title Pending!!", 1920, 1080),1920,1080, true));
            app.setDisplayMode(1920 ,1080,false);
            app.start();
        }catch (SlickException e){

        }
    }
}
