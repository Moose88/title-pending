package org.titlepending.shared;

import java.io.Serializable;

public class TurretUpdater extends CommandObject implements Serializable {

    private float heading;
    private int updatedTurret;

    public TurretUpdater(int id){
        super(id, 4);
    }

    public float getHeading() { return heading; }
    public int getUpdatedTurret() { return updatedTurret; }

    public void setUpdatedTurret(int updatedTurret) { this.updatedTurret = updatedTurret; }
    public void setHeading(float heading) { this.heading = heading; }

}
