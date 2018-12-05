package org.titlepending.client.states;

import jig.ResourceManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.titlepending.client.Client;
import org.titlepending.client.Updates;
import org.titlepending.entities.Camera;
import org.titlepending.entities.ClientShip;
import org.titlepending.shared.Directive;
import java.io.IOException;
import java.util.ArrayList;

import org.titlepending.entities.Ship;

public class PlayingState extends BasicGameState {
    private ArrayList<Ship> Ships;
    private ArrayList<ClientShip> CShips;
    private ClientShip myBoat;
    private TiledMap map;
    private Camera camera;



    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        Client client = (Client)game;




    }

    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException{
//        map = new TiledMap(Client.MAP_RSC);
//        if(Client.DEBUG) System.out.println("Successfully imported map");
//        camera = new Camera(container, map);
        this.Ships = Updates.getInstance().getShips();
        this.CShips = new ArrayList<>();

        for(Ship ship : Ships){
            if(Client.DEBUG) System.out.println("Ship x "+ship.getX()+ "Ship y "+ship.getY());
            ClientShip temp =new ClientShip(ship.getX(), ship.getY(), ship.getPlayerID());
            temp.setStats(ship.getStats());
            temp.addSprites();
            CShips.add(temp);

        }

        for(ClientShip ship : CShips) {
            if (ship.getPlayerID() == Updates.getInstance().getThread().getClientId()) {
                myBoat = ship;
            }
        }

        if(Client.DEBUG)
            System.out.println("Boat x: " + myBoat.getX() + " Boat y: " + myBoat.getY() + " Boat id: " + myBoat.getPlayerID());

        // 96 is the boats image height, and 64 is its image width
        //camera.centerOn(myBoat.getX(), myBoat.getY(), 96, 64);

        System.out.println("Made it to the playing state");
    }

    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException{
        Client client = (Client)game;
        int screenX = (int) myBoat.getX() - client.ScreenWidth/2;
        int screenY = (int) myBoat.getY() - client.ScreenHeight/2;

//        camera.drawMap();
//        camera.translateGraphics();
        for(ClientShip ship : CShips){
            ship.render(g);
        }


    }

    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException{

        Input input = container.getInput();


        Directive cmd = new Directive();
        if(input.isKeyDown(Input.KEY_W)){
            // Send raise anchor command to server
            if(Client.DEBUG)
                System.out.println("Sending command to raise anchor");
            cmd.setRaiseAnchor();
            sendCommand(cmd);
        }
        if(input.isKeyDown(Input.KEY_S)){
            // Send lower anchor command to server
            cmd.setLowerAnchor();
            sendCommand(cmd);
        }
        if(input.isKeyDown(Input.KEY_A)){
            // Send command to turn left
            cmd.setTurnLeft();
            sendCommand(cmd);
        }
        if(input.isKeyDown(Input.KEY_D)){
            cmd.setTurnRight();
            sendCommand(cmd);
        }

    }

    private void sendCommand(Directive cmd){
        cmd.setId(Updates.getInstance().getThread().getClientId());
        try{
            Updates.getInstance().getThread().sendCommand(cmd);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean isGameInProgress(){
        return false;
    }

    public int getID(){return Client.PLAYINGSTATE; }
}
