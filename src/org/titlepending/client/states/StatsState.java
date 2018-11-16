package org.titlepending.client.states;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.titlepending.client.Client;

public class StatsState extends BasicGameState {
    /*
    * Things present on this page
    * Username
    * Most played Character
    * HighScore
    * Most used ship/sail/cannon
    * back button
    * */
    private final static int NAME = 0;
    private final static int MVG = 1;
    private final static int SCORE = 2;
    private final static int EXIT =3;
    int selection;
    Client client;
    int items;
    int backstate;
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        this.client = (Client)game;
        this.items = 4;
        this.selection = 0;
    }
    @Override
    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException{

    }
    private boolean isSelected(int option){
        if(selection == option)
            return true;
        return false;
    }
    @Override
    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{
        client = (Client)game;
        /*
        * render button*/
        int yTop = (int) (client.ScreenHeight * 0.1); // one third down the string
        int xTop = (int) (client.ScreenWidth * 0.9);
        int itemSpace = 95;
        drawStatsItem("Name", -1,yTop, isSelected(NAME));
        drawStatsItem("MVG", (int) (client.ScreenWidth * 0.25),yTop+MVG*2*itemSpace, isSelected(MVG));
        drawStatsItem("Score", (int) (client.ScreenWidth * 0.75),yTop+MVG*2*itemSpace, isSelected(SCORE));
        drawStatsItem("Exit", xTop,(int) (client.ScreenHeight * 0.9), isSelected(EXIT));
    }
    @Override
    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException {
        Input input = container.getInput();
        if (input.isKeyDown(Input.KEY_ENTER)&& isSelected(EXIT)) {
            client.enterState(Client.MAINMENUSTATE,new FadeOutTransition(),new FadeInTransition());
        }
    }
    protected void drawStatsItem(String text, int xPos,int yPos, boolean isSelected){
        int textWidth = client.fontMenu.getWidth(text);
        // render some text to the screen
        Color textColor;
        if(isSelected){
            textColor = new Color(155,28,31);
        } else{
            textColor = Color.white;
        }
        if(xPos<0) {
            client.fontMenu.drawString((client.ScreenWidth / 2) - (textWidth / 2), (int) yPos, text, textColor);
        }
        else{
            client.fontMenu.drawString((int) (xPos -(textWidth / 2)), (int) yPos, text, textColor);
        }
    }
    public void keyPressed(int key, char c){
        if(key == Input.KEY_ESCAPE){
            //backPressed();
        }
        if (key == Input.KEY_UP) {
            if( selection == 2){
                selection = 0;
            }
            else {
                selection--;
            }
        }
        if (key == Input.KEY_LEFT ) {
            if(selection == 2 || selection == 0) {
                selection = 1;
            }
        }
        if (key == Input.KEY_RIGHT){
            if(selection == 0 || selection ==1){
                selection = 2;
            }
        }
        if (key == Input.KEY_DOWN) {
            if(selection == 1 || selection == 2){
                selection = 3;
            }
            else {
                selection++;
            }
        }
        if (selection < 0)
            selection = items;

        // Stop crash from zero
        if(items > 0)
            selection = selection % items;
    }
    @Override
    public int getID() {
        return Client.STATSSTATE;
    }
}
