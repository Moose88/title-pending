package org.titlepending.server.ServerObjects;

import java.util.concurrent.ThreadLocalRandom;

//TODO come back to this later
public class ShipFactory {
    private static ShipFactory instance;
    private ShipFactory(){

    }

    public static ShipFactory getInstance(){
        if(instance == null)
            instance = new ShipFactory();

        return instance;
    }

    public Ship createNewPlayerShip(double x, double y, int[] stats, int playerID){
        Ship temp = new Ship((float) x, (float) y, playerID);
        temp.setStats(stats);
        return temp;
    }

    public Ship createNewNpcShip(float x, float y){
        Ship temp = new Ship(x, y, ThreadLocalRandom.current().nextInt());
        int[] npcStats = new int[] {0,0,0,0};
        temp.setStats(npcStats);
        return temp;
    }

    public Turret createNewTurret(float x, float y){
        Turret temp = new Turret(x, y, ThreadLocalRandom.current().nextInt());
        return temp;
    }

}
