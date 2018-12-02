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

    public Ship createNewPlayerShip(float x, float y, float vx, float vy){
        Ship temp = new Ship(x,y,vx,vy);

        return temp;
    }

    public Ship createNewNpcShip(float x, float y, float vx, float vy){
        Ship temp = new Ship(x,y,vx,vy);

        return temp;
    }

}
