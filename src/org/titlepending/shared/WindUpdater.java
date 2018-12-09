package org.titlepending.shared;

public class WindUpdater extends CommandObject{
    float vx;
    float vy;
    public WindUpdater(int id){
        super(id,3);
    }

    public void setVx(float vx){this.vx=vx;}
    public void setVy(float vy){this.vy=vy;}
    public float getVy() {
        return vy;
    }

    public float getVx(){
        return vx;
    }
}
