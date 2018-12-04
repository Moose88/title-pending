package org.titlepending.entities;
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

    public Ship createNewPlayerShip(double x, double y, int[] stats){
        Ship temp = new Ship(x,y);
        temp.setStats(stats);
        return temp;
    }

    public Ship createNewNpcShip(float x, float y){
        Ship temp = new Ship(x,y);
        int[] npcStats = new int[] {2,2,2,2};
        temp.setStats(npcStats);
        temp.addSprites();
        return temp;
    }

}
