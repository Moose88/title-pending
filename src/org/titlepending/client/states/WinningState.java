package org.titlepending.client.states;

import jig.ResourceManager;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.titlepending.client.Client;

public class WinningState extends BasicGameState {
    private int timer;
    private Animation whitesparkle;

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        timer = 5000;
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        if(Client.DEBUG){
            System.out.println("I'm da bes");
        }
        
        SpriteSheet w_spark = new SpriteSheet(ResourceManager.getImage(Client.WHITE_SPARKLE_RSC).getScaledCopy(4f), 32 * 4, 32 * 4);
        whitesparkle = new Animation(w_spark, 0, 0, 3, 3, true, 50, true);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        Client client = (Client) game;

        // Background
        g.drawImage(ResourceManager.getImage(Client.FRONT_MENU_RSC).getScaledCopy(3f), 0, ResourceManager.getImage(Client.FRONT_MENU_RSC).getHeight() - client.ScreenHeight);

        // String
        String statement = "You Survived!";
        String statement2 = "Now Find The None Piece!";
        int textWidth = client.fontMenu.getWidth(statement);
        int textWidth2 = client.fontMenu.getWidth(statement2);
        int textHeight = client.fontMenu.getHeight(statement);
        g.setFont(client.fontMenu);
        g.setColor(Color.black);

        g.drawString(statement, (client.ScreenWidth/2 - textWidth/2), client.ScreenHeight/2);
        g.drawString(statement2, (client.ScreenWidth/2 - textWidth2/2), client.ScreenHeight/2 + textHeight);
        g.drawAnimation(whitesparkle, client.ScreenWidth * .75f, client.ScreenHeight * .85f);

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Client client = (Client) game;
        timer-=delta;
        if(timer<=0){
            client.enterState(Client.MAINMENUSTATE);
        }
    }

    @Override
    public int getID() {
        return Client.WINNINGSTATE;
    }
}
