package org.titlepending.client.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.titlepending.client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ConnectState extends BasicGameState {
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException{

    }

    public void enter(GameContainer container, StateBasedGame game)
        throws SlickException{
        Client client = (Client) game;
        Socket s=null;
        try{
            s = new Socket("localhost",8000);

        } catch (IOException e){
            e.printStackTrace();
            client.enterState(Client.STARTUPSTATE);
        }
        BufferedReader input =null;
        try{
           input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        }catch (IOException e){
            e.printStackTrace();
            client.enterState(Client.STARTUPSTATE);
        }
        String response = null;
        try{
            response = input.readLine();
        }catch (IOException e){
            e.printStackTrace();
            client.enterState(Client.STARTUPSTATE);
        }

        client.enterState(Integer.valueOf(response));
    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{

    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{

    }
    public int getID(){return Client.CONNECTSTATE; }
}
