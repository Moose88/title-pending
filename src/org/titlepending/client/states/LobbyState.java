package org.titlepending.client.states;


import jig.ResourceManager;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.OutlineEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.titlepending.client.Client;
import org.titlepending.client.Updates;
import org.titlepending.shared.Directive;

import java.util.concurrent.TimeUnit;

import static org.newdawn.slick.Color.black;

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
    private int timer;
    private Directive timeUpdate;

    private int[] haulMod = new int[3];
    private int[] sailMod = new int[3];
    private int[] cannonMod = new int[3];
    private String[] crewMod = new String[3];

    private int totalMod;

    private Image[] haul = new Image[3];
    private Image[] sails = new Image[3];
    private Image[] cannons = new Image[3];
    private Image[] crew = new Image[3];

    private String[] haulString = new String[3];
    private String[] sailString = new String[3];
    private String[] cannonString = new String[3];
    private String[] crewString = new String[3];

    private static SpriteSheet ship_RSC_96 = new SpriteSheet(ResourceManager.getImage(Client.SHIP_RSC), 96, 96);
    private static SpriteSheet ship_RSC_32 = new SpriteSheet(ResourceManager.getImage(Client.SHIP_RSC), 64, 16);

    private static Image smallHaul;
    private static Image medHaul;
    private static Image lgHaul;

    private static Image oneSail;
    private static Image twoSails;
    private static Image threeSails;

    private static Image oneCannon;
    private static Image twoCannons;
    private static Image threeCannons;

    private static Image crewOne;
    private static Image crewTwo;
    private static Image crewThree;

    public static int[] finalShip = new int[4];

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
        setCrew = (int) savedShip.getNumber("crew",0);

        setHaul = 2;
        savedShip.setNumber("haul", setHaul);
        setSails = 2;
        savedShip.setNumber("sails", setSails);
        setCannons = 2;
        savedShip.setNumber("cannons", setCannons);
        setCrew = 2;
        savedShip.setNumber("crew", setCrew);
        save();

        if(Client.DEBUG)
            System.out.println("Haul: " + setHaul + " Sails: " + setSails + " Cannons: " + setCannons + " Crew: " + setCrew);

        // Haul Images

        medHaul = ship_RSC_96.getSubImage(0, 0).getScaledCopy(3f);
        medHaul.rotate(90f);

        // Sail Images

        oneSail = ship_RSC_32.getSubImage(3, 0).getScaledCopy(3f);
        oneSail.rotate(90f);

        // Cannon Images

        // Crew Images

        haul[2] = smallHaul;
        haul[1] = medHaul;
        haul[0] = lgHaul;

        haulString[2] = "Small Haul";
        haulString[1] = "Medium Haul";
        haulString[0] = "Large Haul";

        haulMod[2] = 3;
        haulMod[1] = 5;
        haulMod[0] = 7;

        sails[2] = oneSail;
        sails[1] = twoSails;
        sails[0] = threeSails;

        sailString[2] = "One Sail";
        sailString[1] = "Two Sails";
        sailString[0] = "Three Sails";

        sailMod[2] = 3;
        sailMod[1] = 5;
        sailMod[0] = 7;

        cannons[2] = oneCannon;
        cannons[1] = twoCannons;
        cannons[0] = threeCannons;

        cannonString[2] = "One Cannon";
        cannonString[1] = "Two Cannons";
        cannonString[0] = "Three Cannons";

        cannonMod[2] = 3;
        cannonMod[1] = 5;
        cannonMod[0] = 7;

        crew[2] = crewOne;
        crew[1] = crewTwo;
        crew[0] = crewThree;

        crewString[2] = "Zog!";
        crewString[1] = "Other guy!";
        crewString[0] = "Non Binary Gendered Creature thing!";

        crewMod[2] = "+ 2 cannon";
        crewMod[1] = "+ 2 haul";
        crewMod[0] = "+ 2 sail";

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
            textColor = black;
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

        g.pushTransform();
        g.scale(2f,2f);

        String lobbyStats = "Modifiers: " + "\n" +
                "\n" +
                "Haul: " + (haulMod[setHaul]) + "\n" +
                "\n" +
                "Sails: " + (sailMod[setSails]) + "\n" +
                "\n" +
                "Cannons: " + (cannonMod[setCannons]) + "\n" +
                "\n" +
                "Crew: " + crewString[setCrew] + "\n" +
                crewMod[setCrew] + "\n" +
                "Total mod: " + totalMod + " + 2";


        g.setFont(Client.fontStandard);
        g.setColor(Color.black);
        g.drawString(lobbyStats, (float) (client.ScreenWidth*0.4), 0);
        g.popTransform();


        //Display the Menu strings/items
        int yTop = (int) (client.ScreenHeight * 0.6); // two thirds down the string
        int itemSpace = 95;

        drawMenuItem("< " + haulString[setHaul] + " >", yTop, isSelected(HAUL));
        drawMenuItem("< " + sailString[setSails] + " >", yTop + SAILS * itemSpace, isSelected(SAILS));
        drawMenuItem("< " + cannonString[setCannons] + " >", yTop + CANNONS * itemSpace, isSelected(CANNONS));
        drawMenuItem("< " + crewString[setCrew] + " >", yTop + CREW * itemSpace, isSelected(CREW));
        drawMenuItem("Ready", yTop + READY * itemSpace, isSelected(READY));
        drawMenuItem("Exit", yTop + BACK * itemSpace, isSelected(BACK));


        String time = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(timer), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(timer) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timer)));



        g.setFont(client.fontMenu);
        if(timer >= 30000) {
            g.setColor(Color.white);
        }else {
            g.setColor(Color.red);
        }

        g.drawString(time, client.ScreenWidth/2-g.getFont().getWidth(time)/2, 0);
        g.setColor(Color.white);

        g.setFont(Client.fontStandard);

    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{

        if(!Updates.getInstance().getQueue().isEmpty()){
            timeUpdate = Updates.getInstance().getQueue().poll();
            timer = timeUpdate.getTime();
            if(Client.DEBUG)
                System.out.println(timer);
        }

        totalMod = haulMod[setHaul] + sailMod[setSails] + cannonMod[setCannons];
        finalShip[0] = setHaul;
        finalShip[1] = setSails;
        finalShip[2] = setCannons;
        finalShip[3] = setCrew;
    }

    @Override
    public void keyPressed(int key, char c){
        if(Client.DEBUG) {
            System.out.println(selection);
            System.out.println("Haul: " + setHaul + " Sails: " + setSails + " Cannons: " + setCannons + " Crew: " + setCrew);
        }
        /**
         *  TODO: If total modifier goes over 15, you cannot go to the bigger option, only the lower option.
         */


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
            if(setHaul >= 2) {
                if(haulMod[0] + sailMod[setSails] + cannonMod[setCannons] <= 15)
                    setHaul = 0;
            }else {
                if(haulMod[setHaul+1] + sailMod[setSails] + cannonMod[setCannons] <= 15)
                    setHaul++;
            }
            savedShip.setNumber("haul", setHaul);
            save();
        }

        if(key == Input.KEY_RIGHT && selection == HAUL){
            if(setHaul <= 0) {
                if(haulMod[2] + sailMod[setSails] + cannonMod[setCannons] <= 15)
                    setHaul = 2;
            }else {
                if(haulMod[setHaul-1] + sailMod[setSails] + cannonMod[setCannons] <= 15)
                    setHaul--;
            }
            savedShip.setNumber("haul", setHaul);
            save();
        }

        // Left and right selection for Sails
        if(key == Input.KEY_LEFT && selection == SAILS){
            if(setSails >= 2) {
                if(haulMod[setHaul] + sailMod[0] + cannonMod[setCannons] <= 15)
                    setSails = 0;
            }else {
                if(haulMod[setHaul] + sailMod[setSails+1] + cannonMod[setCannons] <= 15)
                    setSails++;
            }
            savedShip.setNumber("sails", setSails);
            save();
        }

        if(key == Input.KEY_RIGHT && selection == SAILS){
            if(setSails <= 0) {
                if(haulMod[setHaul] + sailMod[2] + cannonMod[setCannons] <= 15)
                    setSails = 2;
            }else {
                if(haulMod[setHaul] + sailMod[setSails-1] + cannonMod[setCannons] <= 15)
                    setSails--;
            }
            savedShip.setNumber("sails", setSails);
            save();
        }

        // Left and right selection for Cannons
        if(key == Input.KEY_LEFT && selection == CANNONS){
            if(setCannons >= 2){
                if(haulMod[setHaul] + sailMod[setSails] + cannonMod[0] <= 15)
                    setCannons = 0;
            }else {
                if(haulMod[setHaul] + sailMod[setSails] + cannonMod[setCannons+1] <= 15)
                    setCannons++;
            }
            savedShip.setNumber("cannons", setCannons);
            save();
        }

        if(key == Input.KEY_RIGHT && selection == CANNONS){
            if(setCannons <= 0) {
                if(haulMod[setHaul] + sailMod[setSails] + cannonMod[2] <= 15)
                    setCannons = 2;
            }else {
                if(haulMod[setHaul] + sailMod[setSails] + cannonMod[setCannons-1] <= 15)
                    setCannons--;
            }
            savedShip.setNumber("cannons", setCannons);
            save();
        }

        // Left and right selection for Crew
        if(key == Input.KEY_LEFT && selection == CREW){
            if(setCrew >= 2)
                setCrew = 0;
            else
                setCrew++;
            savedShip.setNumber("crew", setCrew);
            save();
        }

        if(key == Input.KEY_RIGHT && selection == CREW){
            if(setCrew <= 0)
                setCrew = 2;
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
        Updates.getInstance().getThread().stopThread();
        client.enterState(Client.MAINMENUSTATE, new FadeOutTransition(), new FadeInTransition());
    }



    public int getID(){ return Client.LOBBYSTATE; }

}
