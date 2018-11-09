package org.titlepending.Menus;

import org.titlepending.client.Client;
import org.titlepending.client.states.PlayingState;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;

public class ManuState extends BaseMenuState {

    private final static int PLAY = 0;
    private final static int OPTIONS = 1;
    private final static int STATS = 2;
    private final static int EXIT =3;


    @Override
    public void init(GameContainer container, StateBasedGame game) {
        this.client = (Client)game;
        this.items = 4;
        this.selection = 0;
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
        int yTop = (int) (client.ScreenHeight * 0.3); // one third down the string
        int itemSpace = 95;

        if(((PlayingState)client.getState(Client.PLAYINGSTATE)).isGameInProgress()){
            drawMenuItem("Continue", yTop,isSelected(PLAY) );
        } else {
            drawMenuItem("New Game", yTop,isSelected(PLAY));
        }

        drawMenuItem("Options",yTop+OPTIONS*itemSpace,isSelected(OPTIONS));
        //drawMenuItem("Help",yTop+HELP*itemSpace,isSelected(HELP));
        drawMenuItem("Stats",yTop+STATS*itemSpace,isSelected(STATS));
        //drawMenuItem("Credits",yTop+CREDITS*itemSpace,isSelected(CREDITS));
        drawMenuItem("Exit",yTop+EXIT*itemSpace,isSelected(EXIT));
    }

    public void update(GameContainer container, StateBasedGame game, int delta){
        // Nothing to do here

    }

    @Override
    public void keyPressed(int key, char c){
        super.keyPressed(key,c);

        if(key == Input.KEY_ENTER){
            switch(selection){
                case PLAY:
                    client.enterState(Client.PLAYINGSTATE,new EmptyTransition(), new FadeInTransition());
                    break;
                case OPTIONS:
                    client.enterState(Client.OPTIONSMENUSTATE,new EmptyTransition(), new FadeInTransition());
                    break;
                case STATS:
                    client.enterState(Client.STATSSTATE,new EmptyTransition(), new FadeInTransition());
                    break;
                case EXIT:
                    client.getContainer().exit();
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

        if(((PlayingState)client.getState(Client.PLAYINGSTATE)).isGameInProgress())
            this.backstate = Client.PLAYINGSTATE;
        else
            this.backstate = -1;
    }


    @Override
    public int getID() {
        return Client.MAINMENUSTATE;
    }
}