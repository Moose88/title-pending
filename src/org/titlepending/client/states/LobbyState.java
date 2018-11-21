package org.titlepending.client.states;


import jig.ResourceManager;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.titlepending.client.Client;

import java.util.prefs.BackingStoreException;

public class LobbyState extends BasicGameState {
    private Client client;
    private int selection;
    private int items;

    private SavedState savedShip;

    private final static int HAUL = 0;
    private final static int SAILS = 1;
    private final static int CANNONS = 2;
    private final static int CREW = 3;
    private final static int READY = 4;
    private final static int BACK = 5;

    private int setHaul;
    private int setSails;
    private int setCannons;
    private int setCrew;

    public void init(GameContainer container, StateBasedGame game)
            throws SlickException{

        this.items = 6;
        this.selection = 0;
        this.setHaul = 0;
        this.setSails = 0;
        this.setCannons = 0;
        this.setCrew = 0;
        this.client = (Client)game;

        savedShip = new SavedState("ship");

        setHaul = (int) savedShip.getNumber("haul",0);
        setSails = (int) savedShip.getNumber("sails",0);
        setCannons = (int) savedShip.getNumber("cannons",0);
        setCannons = (int) savedShip.getNumber("crew",0);

    }

    private void save() {
        try {
            savedShip.save();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isSelected(int option){
        return selection == option;
    }

    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException{

    }

    private void drawMenuItem(String text, int yPos, boolean isSelected){
                int textWidth = client.fontMenu.getWidth(text);
        // render some text to the screen
        Color textColor;
        if(isSelected){
            textColor = new Color(155,28,31);
        } else{
            textColor = Color.black;
        }

        if(text.equals("Ready"))
            client.fontMenu.drawString((client.ScreenWidth*4f/5f)-(textWidth/2f), client.ScreenHeight-client.fontMenu.getHeight(text)-20, text ,textColor);
        else if(text.equals("Exit"))
            client.fontMenu.drawString((client.ScreenWidth/5f)-(textWidth/2f), client.ScreenHeight-client.fontMenu.getHeight(text)-20, text ,textColor);
        else
            client.fontMenu.drawString((client.ScreenWidth/2f)-(textWidth/2f), yPos, text ,textColor);
    }


    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{

        //Draw menu background here
        g.drawImage(ResourceManager.getImage(Client.FRONT_MENU_RSC).getScaledCopy(3f), 0, 0);

        //Draw the model ship here for the center of the screen
        /**
         *  TODO: Center will be a model of the ship
         *  should show the haul
         *  sails, cannons, etc
         */

        //Draw the crew on the left
        /**
         *  TODO: Render a splash of the crew image
         */

        //Display the stats on the right
        /**
         *  TODO: Display the stats on the right side
         */


        int yTop = (int) (client.ScreenHeight * 0.6); // two thirds down the string
        int itemSpace = 95;

        drawMenuItem("< " + setHaul + " >", yTop, isSelected(HAUL));
        drawMenuItem("< " + setSails + " >", yTop + SAILS * itemSpace, isSelected(SAILS));
        drawMenuItem("< " + setCannons + " >", yTop + CANNONS * itemSpace, isSelected(CANNONS));
        drawMenuItem("< " + setCrew + " >", yTop + CREW * itemSpace, isSelected(CREW));
        drawMenuItem("Ready", yTop + READY * itemSpace, isSelected(READY));
        drawMenuItem("Exit", yTop + BACK * itemSpace, isSelected(BACK));


    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{

    }

    @Override
    public void keyPressed(int key, char c){
        System.out.println(selection);
        if(key == Input.KEY_ESCAPE){
            backPressed();
        }
        if (key == Input.KEY_UP && selection != BACK){
                selection--;
        } else if(selection == BACK && key == Input.KEY_RIGHT){
            selection--;
        } else if(selection == BACK && key == Input.KEY_UP){
            selection = CREW;
        } else if(key == Input.KEY_LEFT && selection == READY){
            selection += 1;
        }

        if (key == Input.KEY_DOWN && selection != READY) {
                selection++;
        }

        if (selection < 0)
            selection += items;

        System.out.println(selection);

        // Stop crash from zero
        if(items > 0)
            selection = selection % items;

        // Left and right selection for Haul
        if(key == Input.KEY_LEFT && selection == HAUL){
            if(setHaul >= 4)
                setHaul = 0;
            else
                setHaul++;
            savedShip.setNumber("haul", setHaul);
            save();
        }

        if(key == Input.KEY_RIGHT && selection == HAUL){
            if(setHaul <= 0)
                setHaul = 4;
            else
                setHaul--;
            savedShip.setNumber("haul", setHaul);
            save();
        }

        // Left and right selection for Sails
        if(key == Input.KEY_LEFT && selection == SAILS){
            if(setSails >= 4)
                setSails = 0;
            else
                setSails++;
            savedShip.setNumber("sails", setSails);
            save();
        }

        if(key == Input.KEY_RIGHT && selection == SAILS){
            if(setSails <= 0)
                setSails = 4;
            else
                setSails--;
            savedShip.setNumber("sails", setSails);
            save();
        }

        // Left and right selection for Cannons
        if(key == Input.KEY_LEFT && selection == CANNONS){
            if(setCannons >= 4)
                setCannons = 0;
            else
                setCannons++;
            savedShip.setNumber("cannons", setCannons);
            save();
        }

        if(key == Input.KEY_RIGHT && selection == CANNONS){
            if(setCannons <= 0)
                setCannons = 4;
            else
                setCannons--;
            savedShip.setNumber("cannons", setCannons);
            save();
        }

        // Left and right selection for Crew
        if(key == Input.KEY_LEFT && selection == CREW){
            if(setCrew >= 4)
                setCrew = 0;
            else
                setCrew++;
            savedShip.setNumber("crew", setCrew);
            save();
        }

        if(key == Input.KEY_RIGHT && selection == CREW){
            if(setCrew <= 0)
                setCrew = 4;
            else
                setCrew--;
            savedShip.setNumber("crew", setCrew);
            save();
        }

        if(key == Input.KEY_ENTER){
            switch(selection){
                case READY:
                    // Check if all 8 players ready, go when true

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

    private void backPressed(){
        client.enterState(Client.MAINMENUSTATE, new FadeOutTransition(), new FadeInTransition());
    }



    public int getID(){ return Client.LOBBYSTATE; }

}
