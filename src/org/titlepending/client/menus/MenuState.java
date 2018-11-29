package org.titlepending.client.menus;

import jig.ResourceManager;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.titlepending.client.Client;
import org.titlepending.client.states.PlayingState;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;

public class MenuState extends BaseMenuState {

    private static SpriteSheet RSC_32_32 = new SpriteSheet(ResourceManager.getImage(Client.SHIP_RSC), 32, 32);
    private final static int PLAY = 0;
    private final static int HTP = 1;
    private final static int OPTIONS = 2;
    private final static int EXIT =3;


    @Override
    public void init(GameContainer container, StateBasedGame game) {
        this.client = (Client)game;
        this.items = 4;
        this.selection = 0;

    }

    private boolean isSelected(int option){
        return selection == option;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        super.render(container, client, g);

        // The title image on the Main Menu state

        // Left column of scroll
        Image[] leftScroll = new Image[3];
        leftScroll[0] = RSC_32_32.getSubImage(4, 5).getScaledCopy(4f);
        leftScroll[0].setRotation(90f);
        leftScroll[1] = RSC_32_32.getSubImage(5, 5).getScaledCopy(4f);
        leftScroll[1].setRotation(90f);
        leftScroll[2] = RSC_32_32.getSubImage(6, 5).getScaledCopy(4f);
        leftScroll[2].setRotation(90f);

        // Right column of scroll
        Image[] rightScroll = new Image[3];
        rightScroll[0] = RSC_32_32.getSubImage(4, 3).getScaledCopy(4f);
        rightScroll[0].setRotation(90f);
        rightScroll[1] = RSC_32_32.getSubImage(5, 3).getScaledCopy(4f);
        rightScroll[1].setRotation(90f);
        rightScroll[2] = RSC_32_32.getSubImage(6, 3).getScaledCopy(4f);
        rightScroll[2].setRotation(90f);

        // Center of scroll
        Image[] centerScroll = new Image[3];
        centerScroll[0] = RSC_32_32.getSubImage(4, 4).getScaledCopy(4f);
        centerScroll[0].setRotation(90f);
        centerScroll[1] = RSC_32_32.getSubImage(5, 4).getScaledCopy(4f);
        centerScroll[1].setRotation(90f);
        centerScroll[2] = RSC_32_32.getSubImage(6, 4).getScaledCopy(4f);
        centerScroll[2].setRotation(90f);

        // Draws the left and right columns of the title scroll
        String title1 = "TITLE";
        String title2 = "PENDING!!";

        for(int i = 0; i < 3; i++){
            g.drawImage(leftScroll[i], client.ScreenWidth/2 - 500, i*leftScroll[i].getHeight() + 20);

            for(int j = 0; j < 6; j++) {
                g.drawImage(centerScroll[i], client.ScreenWidth / 2 - 500 + j * centerScroll[i].getWidth() + centerScroll[i].getWidth(), i * centerScroll[i].getHeight() + 20);
            }

            g.drawImage(rightScroll[i], client.ScreenWidth/2 - rightScroll[i].getWidth() + 500, i*rightScroll[i].getHeight() + 20);
        }

        // The strings drawn
        g.pushTransform();
        g.scale(1.5f, 1.5f);
        g.setFont(client.fontMenu);
        g.setColor(new Color(97,106,107));
        g.drawString(title1, (client.ScreenWidth/2-g.getFont().getWidth(title1)*.75f)/1.5f, client.ScreenHeight/2 * .10f);
        g.setColor(new Color(39, 59, 70));
        g.drawString(title2, (client.ScreenWidth/2-g.getFont().getWidth(title2)*.75f)/1.5f, client.ScreenHeight/2 *.15f + g.getFont().getHeight(title1)/2f);
        g.setColor(Color.white);
        g.popTransform();

        // Draw menu
        int yTop = (int) (client.ScreenHeight * 0.6); // one third down the string
        int itemSpace = 95;
        drawMenuItem("Join Lobby", yTop, isSelected(PLAY));
        drawMenuItem("How To Play", yTop+ HTP *itemSpace, isSelected(HTP));
        drawMenuItem("Options", yTop+OPTIONS*itemSpace, isSelected(OPTIONS));
        drawMenuItem("Exit", yTop+EXIT*itemSpace, isSelected(EXIT));
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
                    ResourceManager.getMusic(Client.TITLE_MUSIC).stop();
                    client.enterState(Client.CONNECTSTATE, new FadeOutTransition(), new EmptyTransition());
                    break;
                case HTP:
                    client.enterState(Client.STATSSTATE,new FadeOutTransition(), new FadeInTransition());
                    break;
                case OPTIONS:
                    client.enterState(Client.OPTIONSMENUSTATE,new FadeOutTransition(), new FadeInTransition());
                    break;
                case EXIT:
                    ResourceManager.getMusic(Client.TITLE_MUSIC).stop();
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