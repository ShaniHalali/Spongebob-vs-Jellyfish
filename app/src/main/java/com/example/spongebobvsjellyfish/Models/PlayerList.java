package com.example.spongebobvsjellyfish.Models;

import java.util.ArrayList;

public class PlayerList {
    private String name="";
    private ArrayList<Player> allPlayers = new ArrayList<>();

    public PlayerList(ArrayList<Player> allPlayers) {
        //this.allPlayers = allPlayers;
    }

    public PlayerList() {
    }

    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public PlayerList addPlayer(Player player) {
        this.allPlayers.add(player) ;
        return this;
    }

    public String getName() {
        return name;
    }

    public PlayerList setAllPlayers(ArrayList<Player> allPlayers) {
        this.allPlayers = allPlayers;
        return this;
    }

    public PlayerList setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "PlayerList{" +
                "name='" + name + '\'' +
                ", allPlayers=" + allPlayers +
                '}';
    }
}
