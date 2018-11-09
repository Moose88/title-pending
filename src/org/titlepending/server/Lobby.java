package org.titlepending.server;

import java.util.LinkedList;

public class Lobby {

    private boolean isMatchOver;

    private static LinkedList<Player> players = new LinkedList();

    public Lobby(){
        isMatchOver=false;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public int getNumberOfPlayers(){
        return players.size();
    }


}
