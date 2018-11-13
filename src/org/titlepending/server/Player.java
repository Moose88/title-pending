package org.titlepending.server;

import org.titlepending.entities.Ship;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Player implements Serializable{
    private int id;
    private Socket socket;
    private boolean is_alive;

    private Ship ship;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Player(int id,ObjectOutputStream out,ObjectInputStream in){

    }
}
