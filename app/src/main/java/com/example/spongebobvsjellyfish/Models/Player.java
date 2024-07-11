package com.example.spongebobvsjellyfish.Models;

public class Player {
    private String name;
    private int score;
    private double latitude;  // Ensure this is added
    private double longitude; // Ensure this is added

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

    public double getLatitude() {
        return latitude;
    }

    public Player setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Player setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }
}
