package org.titlepending.shared;

import org.titlepending.server.Server;

public class CmdProcessor {
    private boolean isServer;
    Action cmd;
    public CmdProcessor(boolean isServer){
        this.isServer = isServer;
    }

    public void processCommand(CommandObject cmd){
        this.cmd=(Action)cmd;
        if(isServer){
            if(Server.DEBUG){
                System.out.println("Received the following command from client: "+this.cmd.getId() +
                        "\nTurn left: "+this.cmd.isTurnLeft()+
                        "\nTurn right: "+this.cmd.isTurnRight()+
                        "\nRaise Anchor: "+this.cmd.isRaiseAnchor()+
                        "\nLower Anchor: "+this.cmd.isLowerAnchor());
            }
        }else{
            //process client information
        }

    }
}
