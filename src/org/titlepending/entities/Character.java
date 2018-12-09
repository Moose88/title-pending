package org.titlepending.entities;

import jig.Entity;
import jig.ResourceManager;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.titlepending.client.Client;

public class Character extends Entity {
    int baseHP;
    SpriteSheet RSC_128_128 = new SpriteSheet(ResourceManager.getImage(Client.SS2_RSC), 128, 128);
    private Image[] Dudeface;

    public Character (int baseHP, int characterID, float x, float y){
        super(x, y);
        this.baseHP = baseHP;
        Dudeface = new Image[3];
        Dudeface[0] = RSC_128_128.getSubImage(0, 1).getScaledCopy(3f);
        Dudeface[1] = RSC_128_128.getSubImage(1, 1).getScaledCopy(3f);
        Dudeface[2] = RSC_128_128.getSubImage(2, 1).getScaledCopy(3f);

        addImage(Dudeface[0]);

    }

    public void update(int currrentHP){

        System.out.println("My health is: " + currrentHP);

        removeImage(Dudeface[0]);
        removeImage(Dudeface[1]);
        removeImage(Dudeface[2]);
        if(currrentHP > baseHP*2/3) {
            addImage(Dudeface[0]);
        } else if(currrentHP <= baseHP*2/3 && currrentHP > baseHP/3){
            addImage(Dudeface[1]);
        } else {
            addImage(Dudeface[2]);
        }


    }
}
