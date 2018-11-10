package org.titlepending.menus;

import jig.ResourceManager;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.titlepending.client.Client;
import org.titlepending.client.states.PlayingState;

public abstract class BaseMenuState extends BasicGameState {
    int selection;
    Client client;
    int items;
    int backstate;

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
        client = (Client)game;

        if(((PlayingState)client.getState(Client.PLAYINGSTATE)).isGameInProgress()){
            game.getState(Client.PLAYINGSTATE).render(container, game, g);

            //Darkens the backgrounds playing state
            g.setColor(new Color(0f, 0f, 0.5f, 0.3f));
            g.fillRect(0, 0, container.getWidth(), container.getHeight());

        } else {
            //Draw menu background here
            g.drawImage(ResourceManager.getImage(Client.FRONT_MENU_RSC).getScaledCopy(3f), 0, 0);

        }
    }

    @Override
    public void keyPressed(int key, char c){
        if(key == Input.KEY_ESCAPE){
            //backPressed();
        }
        if (key == Input.KEY_UP) {
            selection--;
        }
        if (key == Input.KEY_DOWN) {
            selection++;
        }
        if (selection < 0)
            selection = items;

        // Stop crash from zero
        if(items > 0)
            selection = selection % items;
    }

    public void backPressed(){
        if(backstate != -1)
            client.enterState(backstate,new FadeOutTransition(),new FadeInTransition());
    }

    protected void drawMenuItem(String text, int yPos, boolean isSelected){
        int textWidth = client.fontMenu.getWidth(text);
        // render some text to the screen
        Color textColor;
        if(isSelected){
            textColor = new Color(155,28,31);
        } else{
            textColor = Color.black;
        }
        client.fontMenu.drawString((client.ScreenWidth/2)-(textWidth/2), (int)yPos, text ,textColor);
    }
}
