package org.titlepending.shared;

public class Finalizer extends CommandObject {
    private int stateTransition;

    public Finalizer(int id){
        super(id,5);
    }

    public void setStateTransition(int stateTransition) {
        this.stateTransition = stateTransition;
    }

    public int getStateTransition() {
        return stateTransition;
    }
}
