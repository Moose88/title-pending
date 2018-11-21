package org.titlepending.shared;

import org.titlepending.server.Server;

public class CmdProcessor {
    private boolean isServer;
    public CmdProcessor(boolean isServer){
        this.isServer = isServer;
    }

    public void processCommand(Directive cmd){
        if(isServer){
            if(Server.DEBUG){
                System.out.println("Received the following command from client: "+cmd.getId() +
                        "\nTurn left: "+cmd.isTurnLeft()+
                        "\nTurn right: "+cmd.isTurnRight()+
                        "\nRaise Anchor: "+cmd.isRaiseAnchor()+
                        "\nLower Anchor: "+cmd.isLowerAnchor());
            }
        }else{
            //process client information
        }

    }
}
