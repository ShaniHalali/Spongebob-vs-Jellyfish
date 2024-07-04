package com.example.spongebobvsjellyfish.Models;

import java.util.ArrayList;

public class Player {
    private String name="";
    private int score=0;
    private ArrayList<String> tags=new ArrayList<>();

    public String getName() {
        return name;
    }

    public Player setName(String name) {
        this.name = name;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Player setScore(int score) {
        this.score = score;
        return this;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public Player setTags(ArrayList<String> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", tags=" + tags +
                '}';
    }

    public Player() { //must have because of the Json
    }
}
