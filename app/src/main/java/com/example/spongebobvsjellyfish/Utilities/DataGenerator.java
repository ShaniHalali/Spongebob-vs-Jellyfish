package com.example.spongebobvsjellyfish.Utilities;

import com.example.spongebobvsjellyfish.Models.PlayerList;

public class DataGenerator {
    public PlayerList generatePlayerList(){
       PlayerList playerList=new PlayerList();
        playerList.setName("winners");
        return playerList;
    }

    public DataGenerator() {
    }
}
