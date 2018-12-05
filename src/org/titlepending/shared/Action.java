package org.titlepending.shared;

import java.io.Serializable;

public class Action extends CommandObject implements Serializable {
    private boolean turnLeft;
    private boolean turnRight;
    private boolean raiseAnchor;
    private boolean lowerAnchor;
    private boolean fireCannons;
    public  Action(int id){
        super(id);
        turnLeft=turnRight=raiseAnchor=lowerAnchor=fireCannons=false;
    }

    public void setTurnLeft(){turnLeft = true;}
    public void setTurnRight(){turnRight = true;}
    public void setRaiseAnchor(){raiseAnchor = true;}
    public void setLowerAnchor(){lowerAnchor = true;}
    public void setFireCannons(){fireCannons=true;}
    public boolean isTurnRight(){return turnRight;}
    public boolean isTurnLeft(){return turnLeft;}
    public boolean isRaiseAnchor(){return raiseAnchor;}
    public boolean isLowerAnchor(){return lowerAnchor;}
    public boolean isFireCannons(){return fireCannons;}
}
