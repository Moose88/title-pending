package org.titlepending.entities;

import org.titlepending.client.Updates;
import org.titlepending.server.ServerObjects.Ship;
import org.titlepending.server.ServerObjects.TurretObject;

import java.util.concurrent.ThreadLocalRandom;

public class turretFactory {
    private static turretFactory instance;

    private turretFactory(){

    }

    public static turretFactory getInstance(){
        if(instance == null)
            instance = new turretFactory();

        return instance;
    }

    public TurretObject createNewTurret(float x, float y){
        TurretObject temp = new TurretObject(x, y, ThreadLocalRandom.current().nextInt());
        return temp;
    }


}
