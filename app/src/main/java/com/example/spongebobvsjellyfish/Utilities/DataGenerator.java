package com.example.spongebobvsjellyfish.Utilities;

import com.example.spongebobvsjellyfish.Models.Player;
import com.example.spongebobvsjellyfish.Models.PlayerList;

public class DataGenerator {
    public PlayerList generatePlayerList(String playerName,int score){
       PlayerList playerList=new PlayerList();
        playerList
                .addPlayer(
                        new Player()
                                .setName(playerName)
                                .setScore(score)


                )        ;
        return playerList;
    }

    public DataGenerator() {
    }
}
