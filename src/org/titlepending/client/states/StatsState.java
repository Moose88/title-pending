package org.titlepending.client.states;

<<<<<<< HEAD
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

class StatsState extends StartState{
    /*
    * Things present on this page
    * Username
    * Most played Character
    * HighScore
    * Most used ship/sail/cannon
    * back button
    * */
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {

    }
    @Override
    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException{

    }
    @Override
    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{

        /*
        * render button*/
    }
    @Override
    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException {
        Input input = container.getInput();
        if (input.isKeyDown(Input.KEY_ENTER)) {
            //Go back to start state
        }
//=======
//import jig.ResourceManager;
//import org.newdawn.slick.*;
//import org.newdawn.slick.state.BasicGameState;
//import org.newdawn.slick.state.StateBasedGame;
//import org.newdawn.slick.state.transition.FadeInTransition;
//import org.newdawn.slick.state.transition.FadeOutTransition;
//import org.titlepending.client.Client;
//
//public abstract class StatsState extends BasicGameState {
//    int selection;
//    Client client;
//    int items;
//    int backstate;
//
//    @Override
//    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
//        client = (Client)game;
//        //Draw menu background here
//
//        //g.drawImage(ResourceManager.getImage(Client.STARTUP_BANNER_RSC), client.ScreenWidth/2 - ResourceManager.getImage(Client.STARTUP_BANNER_RSC).getWidth()/2, 100);
//
//        // Draw menu
//        int yTop = (int) (client.ScreenHeight * 0.6); // one third down the string
//        int itemSpace = 95;
//        //drawMenuItem("Join Lobby", yTop, isSelected(PLAY));
//        //drawMenuItem("Options", yTop+OPTIONS*itemSpace, isSelected(OPTIONS));
//        //drawMenuItem("Stats", yTop+STATS*itemSpace, isSelected(STATS));
//        drawMenuItem("Exit", yTop+EXIT*itemSpace, isSelected(EXIT));
//
//
//
//    }
//
//    @Override
//    public void keyPressed(int key, char c){
//        if(key == Input.KEY_ESCAPE){
//            //backPressed();
//        }
//        if (key == Input.KEY_UP) {
//            selection--;
//        }
//        if (key == Input.KEY_DOWN) {
//            selection++;
//        }
//        if (selection < 0)
//            selection = items;
//
//        // Stop crash from zero
//        if(items > 0)
//            selection = selection % items;
//    }
//
//    public void backPressed(){
//        if(backstate != -1)
//            //client.enterState(backstate,new FadeOutTransition(),new FadeInTransition());
//    }
//
//    protected void drawMenuItem(String text, int yPos, boolean isSelected){
//        int textWidth = client.fontMenu.getWidth(text);
//        // render some text to the screen
//        Color textColor;
//        if(isSelected){
//            textColor = new Color(155,28,31);
//        } else{
//            textColor = Color.black;
//        }
//        client.fontMenu.drawString((client.ScreenWidth/2)-(textWidth/2), (int)yPos, text ,textColor);
//>>>>>>> Stashed changes
    }
=======
import org.newdawn.slick.state.BasicGameState;

public abstract class StatsState extends BasicGameState {
>>>>>>> develop
}
