package com.example.spongebobvsjellyfish.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PlayerList {

    private ArrayList<Player> allPlayers = new ArrayList<>();
    private int count;
    private final int MaxPlayers = 10;

    public PlayerList() {
    }

    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public PlayerList setAllPlayers(ArrayList<Player> allPlayers) {
        this.allPlayers = allPlayers;
        return this;
    }

    public int getCount() {
        count = allPlayers.size();
        return count;
    }

    public void sortPlayersByScore() {
        Collections.sort(allPlayers, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return Integer.compare(p2.getScore(), p1.getScore()); // Sorting in descending order
            }
        });
    }

    public void removePlayer() {
        if (!allPlayers.isEmpty()) {
            allPlayers.remove(allPlayers.size() - 1);
        }
    }

    public void replaceLastPlayerIfHigherScore(Player newPlayer) {
        if (!allPlayers.isEmpty()) {
            Player lastPlayer = allPlayers.get(allPlayers.size() - 1);

            if (newPlayer.getScore() > lastPlayer.getScore()) {
                allPlayers.set(allPlayers.size() - 1, newPlayer);
            }
        }
    }

    public boolean addPlayer(Player player) {
        // Check if the list is empty
        if (allPlayers.isEmpty()) {
            // If the list is empty, simply add the player
            allPlayers.add(player);
            sortPlayersByScore();
            return true;
        }

        // Proceed if the list is not empty
        if (allPlayers.size() < MaxPlayers) {
            this.allPlayers.add(player);
            sortPlayersByScore();
            return true;
        } else if (player.getScore() > allPlayers.get(allPlayers.size() - 1).getScore()) {
            replaceLastPlayerIfHigherScore(player);
            sortPlayersByScore();
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "PlayerList{" +
                "allPlayers=" + allPlayers +
                ", count=" + count +
                ", MaxPlayers=" + MaxPlayers +
                '}';
    }
}
