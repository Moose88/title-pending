package org.titlepending.client.states;


import jig.ResourceManager;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.titlepending.client.Client;
import org.titlepending.client.Updates;
import org.titlepending.shared.Directive;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.newdawn.slick.Color.black;

public class LobbyState extends BasicGameState {
    private Client client;
    private int selection;
    private int items;
    private boolean readyset = false;

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

    private static SpriteSheet ship_RSC_96 = new SpriteSheet(ResourceManager.getImage(Client.SHIP_RSC), 64, 96);
    private static SpriteSheet RSC_32_32 = new SpriteSheet(ResourceManager.getImage(Client.SHIP_RSC), 32, 32);
    private static SpriteSheet CHAR_256_256 = new SpriteSheet(ResourceManager.getImage(Client.CHARACTER_RSC), 256, 256);

    private static Image crewOne = CHAR_256_256.getSubImage(0, 0).getScaledCopy(3f);
    private static Image crewTwo = CHAR_256_256.getSubImage(1, 0).getScaledCopy(3f);
    private static Image crewThree = CHAR_256_256.getSubImage(2, 0).getScaledCopy(3f);

    private static int[] finalShip = new int[4];

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

//        setHaul = 2;
//        savedShip.setNumber("haul", setHaul);
//        setSails = 2;
//        savedShip.setNumber("sails", setSails);
//        setCannons = 2;
//        savedShip.setNumber("cannons", setCannons);
//        setCrew = 2;
//        savedShip.setNumber("crew", setCrew);
//        save();

        if(Client.DEBUG) {
            System.out.println("Haul: " + setHaul + " Sails: " + setSails + " Cannons: " + setCannons + " Crew: " + setCrew);
        }

        // Haul Images, Mod values and Names/Arrays

        Image lgHaul = ship_RSC_96.getSubImage(1, 0).getScaledCopy(3f);
        Image medHaul = ship_RSC_96.getSubImage(0, 0).getScaledCopy(3f);
        Image smallHaul = ship_RSC_96.getSubImage(2, 0).getScaledCopy(3f);

        haul[2] = smallHaul;
        haul[1] = medHaul;
        haul[0] = lgHaul;

        haulString[2] = "Small Hull";
        haulString[1] = "Medium Hull";
        haulString[0] = "Large Hull";

        haulMod[2] = 3;
        haulMod[1] = 5;
        haulMod[0] = 7;

        // Sail Images, Mod values and Names/Arrays

        Image oneSail = ship_RSC_96.getSubImage(5, 0).getScaledCopy(3f);
        Image twoSails = ship_RSC_96.getSubImage(4, 0).getScaledCopy(3f);
        Image threeSails = ship_RSC_96.getSubImage(3, 0).getScaledCopy(3f);

        sails[2] = oneSail;
        sails[1] = twoSails;
        sails[0] = threeSails;

        sailString[2] = "One Sail";
        sailString[1] = "Two Sails";
        sailString[0] = "Three Sails";

        sailMod[2] = 3;
        sailMod[1] = 5;
        sailMod[0] = 7;

        // Cannon Images, Mod values and Names/Arrays

        Image oneCannon = ship_RSC_96.getSubImage(6, 1).getScaledCopy(2.5f);
        Image twoCannons = ship_RSC_96.getSubImage(5, 1).getScaledCopy(2.5f);
        Image threeCannons = ship_RSC_96.getSubImage(4, 1).getScaledCopy(2.5f);

        cannons[2] = oneCannon;
        cannons[1] = twoCannons;
        cannons[0] = threeCannons;

        cannonString[2] = "One Cannon";
        cannonString[1] = "Two Cannons";
        cannonString[0] = "Three Cannons";

        cannonMod[2] = 3;
        cannonMod[1] = 5;
        cannonMod[0] = 7;

        // Crew Images, Mod values and Names/Arrays

        // Assign images to crew here

        crew[2] = crewOne;
        crew[1] = crewTwo;
        crew[0] = crewThree;

        crewString[2] = "Zog!";
        crewString[1] = "Dirk!";
        crewString[0] = "Terry!";

        crewMod[2] = "Bonus to cannons";
        crewMod[1] = "Bonus to hull";
        crewMod[0] = "Bonus to sails";

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

    public void enter(GameContainer container, StateBasedGame game) {
        ResourceManager.getMusic(Client.LOBBY_MUSIC).loop(1, 3f);
        if(setCrew == 2){
            ResourceManager.getSound(Client.SCREAM_SOUND).loop(1.2f, 0.5f);
        }

    }

    private void drawMenuItem(String text, int yPos, boolean isSelected){
                int textWidth = client.fontMenu.getWidth(text);
        // render some text to the screen
        Color textColor;
        if(isSelected){
            textColor = new Color(155,28,31);
        } else if(isSelected && readyset) {
            textColor = new Color(Color.green);
        }else{
            textColor = black;
        }

        switch (text) {
            case "Ready":
                client.fontMenu.drawString((client.ScreenWidth * 4f / 5f) - (textWidth / 2f), client.ScreenHeight - client.fontMenu.getHeight(text) - 20, text, textColor);
                break;
            case "Exit":
                client.fontMenu.drawString((client.ScreenWidth / 5f) - (textWidth / 2f), client.ScreenHeight - client.fontMenu.getHeight(text) - 20, text, textColor);
                break;
            default:
                client.fontMenu.drawString((client.ScreenWidth / 2f) - (textWidth / 2f), yPos, text, textColor);
                break;
        }
    }


    public void render(GameContainer container, StateBasedGame game, Graphics g) {

        //Draw menu background here
        g.drawImage(ResourceManager.getImage(Client.FRONT_MENU_RSC).getScaledCopy(3f), 0, 0);



        //Draw the model ship here for the center of the screen

        g.drawImage(cannons[setCannons], client.ScreenWidth/2 - cannons[setCannons].getWidth()/2, client.ScreenHeight*.25f);
        g.drawImage(haul[setHaul], client.ScreenWidth/2 - haul[setHaul].getWidth()/2, client.ScreenHeight*.25f);
        g.drawImage(sails[setSails], client.ScreenWidth/2 - sails[setSails].getWidth()/2, client.ScreenHeight*.25f);

        //Draw the crew on the left
        /*
           TODO: Render a splash of the crew image
         */

        g.drawImage(crew[setCrew], client.ScreenWidth/6 - crew[setCrew].getWidth()/2, client.ScreenHeight/2 - crew[setCrew].getHeight()/2);



        //Display the stats on the right

        g.pushTransform();
        g.scale(2f,2f);

        String lobbyStats = "Construction Cost: " + "\n" +
                "\n" +
                "Hull: " + (haulMod[setHaul]) + " gold coins." + "\n" +
                "Sails: " + (sailMod[setSails]) + " gold coins." +  "\n" +
                "Cannons: " + (cannonMod[setCannons]) + " gold coins." +  "\n" +
                "\n" +
                "Captain:\n" +
                "   " + crewString[setCrew] + "\n" +
                "   " + crewMod[setCrew] + "\n" +
                "\n" +
                "Total cost: " + "\n" +
                "   " + totalMod + " gold coins.";


        // Top row of scroll
        g.drawImage(RSC_32_32.getSubImage(4, 3).getScaledCopy(2f), (float) (client.ScreenWidth*0.4) - 75, 0);
        g.drawImage(RSC_32_32.getSubImage(5, 3).getScaledCopy(2f), (float) (client.ScreenWidth*0.4) +
                RSC_32_32.getSubImage(4,3).getScaledCopy(2f).getWidth() - 75, 0);
        g.drawImage(RSC_32_32.getSubImage(5, 3).getScaledCopy(2f), (float) (client.ScreenWidth*0.4) +
                RSC_32_32.getSubImage(4,3).getScaledCopy(2f).getWidth() * 2 - 75, 0);
        g.drawImage(RSC_32_32.getSubImage(6, 3).getScaledCopy(2f), (float) (client.ScreenWidth*0.4) +
                RSC_32_32.getSubImage(4,3).getScaledCopy(2f).getWidth() * 3 - 75, 0);

        // Center filling for scroll
        for(int i = 1; i < 11; i++) {
            g.drawImage(RSC_32_32.getSubImage(4, 4).getScaledCopy(2f), (float) (client.ScreenWidth * 0.4) - 75,
                    RSC_32_32.getSubImage(4, 3).getHeight() * i);
            g.drawImage(RSC_32_32.getSubImage(5, 4).getScaledCopy(2f), (float) (client.ScreenWidth * 0.4) +
                            RSC_32_32.getSubImage(4, 3).getScaledCopy(2f).getWidth() - 75,
                    RSC_32_32.getSubImage(5, 3).getHeight() * i);
            g.drawImage(RSC_32_32.getSubImage(5, 4).getScaledCopy(2f), (float) (client.ScreenWidth * 0.4) +
                            RSC_32_32.getSubImage(4, 3).getScaledCopy(2f).getWidth() * 2 - 75,
                    RSC_32_32.getSubImage(5, 3).getHeight() * i);
            g.drawImage(RSC_32_32.getSubImage(6, 4).getScaledCopy(2f), (float) (client.ScreenWidth * 0.4) +
                            RSC_32_32.getSubImage(4, 3).getScaledCopy(2f).getWidth() * 3 - 75,
                    RSC_32_32.getSubImage(6, 3).getHeight() * i);
        }

        //Bottom filling for scroll
        g.drawImage(RSC_32_32.getSubImage(4, 5).getScaledCopy(2f), (float) (client.ScreenWidth * 0.4) - 75,
                RSC_32_32.getSubImage(4, 3).getHeight() * 12);
        g.drawImage(RSC_32_32.getSubImage(5, 5).getScaledCopy(2f), (float) (client.ScreenWidth * 0.4) +
                        RSC_32_32.getSubImage(4, 3).getScaledCopy(2f).getWidth() - 75,
                RSC_32_32.getSubImage(5, 3).getHeight() * 12);
        g.drawImage(RSC_32_32.getSubImage(5, 5).getScaledCopy(2f), (float) (client.ScreenWidth * 0.4) +
                        RSC_32_32.getSubImage(4, 3).getScaledCopy(2f).getWidth() * 2 - 75,
                RSC_32_32.getSubImage(5, 3).getHeight() * 12);
        g.drawImage(RSC_32_32.getSubImage(6, 5).getScaledCopy(2f), (float) (client.ScreenWidth * 0.4) +
                        RSC_32_32.getSubImage(4, 3).getScaledCopy(2f).getWidth() * 3 - 75,
                RSC_32_32.getSubImage(6, 3).getHeight() * 12);

        g.setFont(Client.fontStandard);
        g.setColor(Color.black);
        g.drawString(lobbyStats, (float) (client.ScreenWidth*0.365), (float) (client.ScreenHeight*0.035));
        g.popTransform();


        //Display the Menu strings/items
        int yTop = (int) (client.ScreenHeight * 0.55); // two thirds down the string
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
                       int delta) {

        if(!Updates.getInstance().getQueue().isEmpty()){
            Directive timeUpdate = Updates.getInstance().getQueue().poll();
            assert timeUpdate != null;
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
        Directive ready = new Directive();

        if(readyset && key != Input.KEY_ESCAPE)
            return;

        if(Client.DEBUG) {
            System.out.println("Haul: " + setHaul + " Sails: " + setSails + " Cannons: " + setCannons + " Crew: " + setCrew);
        }

        if(key == Input.KEY_ESCAPE && readyset){
            readyset = false;
            ready.setReady(false);
            sendCommand(ready);
        } else if (key == Input.KEY_ESCAPE){
            backPressed();
        }

        if (key == Input.KEY_UP && selection != BACK){
            ResourceManager.getSound(Client.MENU_CLICK).play();
            selection--;
        } else if(selection == BACK && key == Input.KEY_RIGHT){
            ResourceManager.getSound(Client.MENU_CLICK).play();
            selection--;
        } else if(selection == BACK && key == Input.KEY_UP){
            ResourceManager.getSound(Client.MENU_CLICK).play();
            selection = CREW;
        } else if(key == Input.KEY_LEFT && selection == READY){
            ResourceManager.getSound(Client.MENU_CLICK).play();
            selection += 1;
        }

        if (key == Input.KEY_DOWN && selection != READY) {
            ResourceManager.getSound(Client.MENU_CLICK).play();
            selection++;
        }

        if (selection < 0)
            selection += items;

        // Stop crash from zero
        if(items > 0)
            selection = selection % items;

        // Left and right selection for Haul
        if(key == Input.KEY_LEFT && selection == HAUL){
            if(setHaul >= 2) {
                if(haulMod[0] + sailMod[setSails] + cannonMod[setCannons] <= 15){
                    ResourceManager.getSound(Client.MENU_CLICK).play();
                    setHaul = 0;
                }
            }else {
                if (haulMod[setHaul + 1] + sailMod[setSails] + cannonMod[setCannons] <= 15){
                    ResourceManager.getSound(Client.MENU_CLICK).play();
                    setHaul++;
                }
            }
            savedShip.setNumber("haul", setHaul);
            save();
        }

        if(key == Input.KEY_RIGHT && selection == HAUL){
            if(setHaul <= 0) {
                if(haulMod[2] + sailMod[setSails] + cannonMod[setCannons] <= 15) {
                    ResourceManager.getSound(Client.MENU_CLICK).play();
                    setHaul = 2;
                }
            }else {
                if(haulMod[setHaul-1] + sailMod[setSails] + cannonMod[setCannons] <= 15) {
                    ResourceManager.getSound(Client.MENU_CLICK).play();
                    setHaul--;
                }
            }
            savedShip.setNumber("haul", setHaul);
            save();
        }

        // Left and right selection for Sails
        if(key == Input.KEY_LEFT && selection == SAILS){
            if(setSails >= 2) {
                if(haulMod[setHaul] + sailMod[0] + cannonMod[setCannons] <= 15) {
                    ResourceManager.getSound(Client.MENU_CLICK).play();
                    setSails = 0;
                }
            }else {
                if(haulMod[setHaul] + sailMod[setSails+1] + cannonMod[setCannons] <= 15) {
                    ResourceManager.getSound(Client.MENU_CLICK).play();
                    setSails++;
                }
            }
            savedShip.setNumber("sails", setSails);
            save();
        }

        if(key == Input.KEY_RIGHT && selection == SAILS){
            if(setSails <= 0) {
                if(haulMod[setHaul] + sailMod[2] + cannonMod[setCannons] <= 15) {
                    ResourceManager.getSound(Client.MENU_CLICK).play();
                    setSails = 2;
                }
            }else {
                if(haulMod[setHaul] + sailMod[setSails-1] + cannonMod[setCannons] <= 15) {
                    ResourceManager.getSound(Client.MENU_CLICK).play();
                    setSails--;
                }
            }
            savedShip.setNumber("sails", setSails);
            save();
        }

        // Left and right selection for Cannons
        if(key == Input.KEY_LEFT && selection == CANNONS){
            if(setCannons >= 2){
                if(haulMod[setHaul] + sailMod[setSails] + cannonMod[0] <= 15) {
                    ResourceManager.getSound(Client.MENU_CLICK).play();
                    setCannons = 0;
                }
            }else {
                if(haulMod[setHaul] + sailMod[setSails] + cannonMod[setCannons+1] <= 15) {
                    ResourceManager.getSound(Client.MENU_CLICK).play();
                    setCannons++;
                }
            }
            savedShip.setNumber("cannons", setCannons);
            save();
        }

        if(key == Input.KEY_RIGHT && selection == CANNONS){
            if(setCannons <= 0) {
                if(haulMod[setHaul] + sailMod[setSails] + cannonMod[2] <= 15) {
                    ResourceManager.getSound(Client.MENU_CLICK).play();
                    setCannons = 2;
                }
            }else {
                if(haulMod[setHaul] + sailMod[setSails] + cannonMod[setCannons-1] <= 15) {
                    ResourceManager.getSound(Client.MENU_CLICK).play();
                    setCannons--;
                }
            }
            savedShip.setNumber("cannons", setCannons);
            save();
        }

        // Left and right selection for Crew
        if(key == Input.KEY_LEFT && selection == CREW){
            if(setCrew >= 2) {
                ResourceManager.getSound(Client.MENU_CLICK).play();
                setCrew = 0;
            }else {
                ResourceManager.getSound(Client.MENU_CLICK).play();
                setCrew++;
            }

            savedShip.setNumber("crew", setCrew);
            save();
        }

        if(key == Input.KEY_RIGHT && selection == CREW){
            if(setCrew <= 0) {
                ResourceManager.getSound(Client.MENU_CLICK).play();
                setCrew = 2;
            }else{
                ResourceManager.getSound(Client.MENU_CLICK).play();
                setCrew--;
            }


            savedShip.setNumber("crew", setCrew);
            save();
        }

        if(setCrew ==  2){
            ResourceManager.getSound(Client.SCREAM_SOUND).loop(1.6f, 0.5f);
        } else {
            ResourceManager.getSound(Client.SCREAM_SOUND).stop();
        }

        if(key == Input.KEY_ENTER){
            switch(selection){
                case READY:
                    // Check if all 8 players ready, go when true

                    ready.setReady(true);
                    sendCommand(ready);

                    readyset = true;
                    // TODO: Lock the keyboard here, so that players cannot make any addition input until game starts.


                    if(Client.DEBUG)
                        System.out.println("Final ship values: " + finalShip[0] + " " + finalShip[1] + " " + finalShip[2] + " " + finalShip[3]);


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
        ResourceManager.getSound(Client.SCREAM_SOUND).stop();
        ResourceManager.getMusic(Client.LOBBY_MUSIC).stop();
        ResourceManager.getMusic(Client.TITLE_MUSIC).loop();
        Updates.getInstance().getThread().stopThread();
        client.enterState(Client.MAINMENUSTATE, new FadeOutTransition(), new FadeInTransition());
    }

    private void sendCommand(Directive cmd){
        cmd.setId(Updates.getInstance().getThread().getClientId());
        try{
            Updates.getInstance().getThread().sendCommand(cmd);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public int getID(){ return Client.LOBBYSTATE; }

}
