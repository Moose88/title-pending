package org.titlepending.client.states;

import jig.ResourceManager;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.titlepending.client.Client;

import static org.newdawn.slick.Color.black;

/**
 * The purpose of this class is to have this state explain the general mechanics of the game
 *
 */

public class HowToPlay extends BasicGameState {
    private Client client;

    private final static int PG1 = 0;
    private final static int PG2 = 1;
    private final static int PG3 = 2;
    private final static int PG4 = 3;
    private final static int PG5 = 4;

    private final static int LEFT = 0;
    private final static int RIGHT = 1;
    private final static int EXIT = 2;

    private int pages;
    private int selection;
    private int lastSelected;

    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        this.client = (Client) game;
        this.pages = 0;
        this.selection = 0;
        this.lastSelected = 0;

    }

    private boolean isSelected(int option){
        return selection == option;
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException{
        ResourceManager.getMusic(Client.HTP_MUSIC).loop();

    }

    private void drawMenuItem(String text, boolean isSelected){
        // render some text to the screen
        Color textColor;
        if(isSelected){
            textColor = new Color(155,28,31);
        } else{
            textColor = black;
        }

        switch (text) {
            case ">":
                client.fontMenu.drawString(client.ScreenWidth / 1.5f - client.fontMenu.getWidth(text) * 2, (client.ScreenHeight/2) / 1.5f - client.fontMenu.getHeight(text)/2, text, textColor);
                break;
            case "<":
                client.fontMenu.drawString(client.fontMenu.getWidth(text), (client.ScreenHeight/2) / 1.5f - client.fontMenu.getHeight(text)/2, text, textColor);
                break;
            case "Back":
                client.fontMenu.drawString(client.ScreenWidth / 1.5f - client.fontMenu.getWidth(text) - 50, client.ScreenHeight / 1.5f - client.fontMenu.getHeight(text), text, textColor);
                break;
            default:
                client.fontMenu.drawString((client.ScreenWidth / 2f) - (client.fontMenu.getWidth(text) / 2f), 0, text, textColor);
                break;
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{
        g.drawImage(ResourceManager.getImage(Client.FRONT_MENU_RSC).getScaledCopy(3f), 0, 0);
        g.pushTransform();
        g.scale(1.5f, 1.5f);
        drawMenuItem("<", isSelected(LEFT));
        drawMenuItem(">", isSelected(RIGHT));
        drawMenuItem("Back", isSelected(EXIT));
        g.popTransform();

        g.setColor(Color.black);
        if(pages == PG1){
            // What is Title Pending!!?
            g.drawString("I'm in 1!!", 0, 500);

            // TODO: Explain what the game generally in regards to genre etc.

        } else if(pages == PG2){
            // Setting an IP Address
            g.drawString("I'm in 2!!", 0, 500);

            // TODO: Setting up connecting to a server.

        } else if(pages == PG3){
            // Joining a Lobby and Building a Ship
            g.drawString("I'm in 3!!", 0, 500);

            // TODO: Building your ship, Captain, and about stats

        } else if(pages == PG4){
            // The Map/Phases/Objective
            g.drawString("I'm in 4!!", 0, 500);

            // TODO: The Map, Fog, Wind, and how to win

        } else if(pages == PG5){
            // Win or Lose
            g.drawString("I'm in 5!!", 0, 500);

            // TODO: When you Win or Lose

        }
        g.setColor(Color.white);

    }

    @Override
    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException {

        // Nada

    }

    public void keyPressed(int key, char c){
        System.out.println(key);

        if(key == Input.KEY_ESCAPE){
            backPressed();
        }

        if(key == Input.KEY_LEFT){
            selection = LEFT;
        }

        if(key == Input.KEY_RIGHT){
            selection = RIGHT;
        }

        if(key == Input.KEY_DOWN){
            lastSelected = selection;
            selection = EXIT;
        }

        if(key == Input.KEY_UP){
            selection = lastSelected;
        }

        // If KEY_UP/KEY_DOWN pressed, isSelected to exit.

        if(key == Input.KEY_ENTER){
            System.out.println("Page: " + pages);
            switch(selection){
                case LEFT:
                    // If KEY_RIGHT pressed, isSelected to the right, but only proceed when enter is pressed
                    if(pages <= 0){
                        pages = 4;
                    } else {
                        pages--;
                    }


                    break;
                case RIGHT:
                    // If KEY_LEFT pressed, isSelected to the left, but only proceed when enter is pressed
                    if(pages >= 4){
                        pages = 0;
                    } else {
                        pages++;
                    }
                    break;
                case EXIT:
                    ResourceManager.getMusic(Client.HTP_MUSIC).stop();
                    ResourceManager.getMusic(Client.TITLE_MUSIC).loop();
                    client.enterState(Client.MAINMENUSTATE, new FadeOutTransition(), new FadeInTransition());
                    break;
                default:
                    System.out.println("Should not happen");
                    break;
            }
        }
    }

    private void backPressed(){
        ResourceManager.getMusic(Client.HTP_MUSIC).stop();
        ResourceManager.getMusic(Client.TITLE_MUSIC).loop();
        client.enterState(Client.MAINMENUSTATE, new FadeOutTransition(), new FadeInTransition());
    }

    @Override
    public int getID() {
        return Client.STATSSTATE;
    }
}
