package org.titlepending.map;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.titlepending.client.Client;


public class Map {
    private static Map instance = null;
    private TiledMap map;

    private Map() throws SlickException {



    }

    public void new_level(){
        try {
            map = new TiledMap(Client.LEVEL_RSC);
        } catch (org.newdawn.slick.SlickException e){
            e.printStackTrace();
        }

        try {
            // Add players here, and set their placements

        } catch (SlickException e) {
            e.printStackTrace();
        }



    }

    public static Map getInstance(){
        if(instance == null){
            try {
                instance = new Map();
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }
}
