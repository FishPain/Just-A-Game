package com.mygdx.engine.io;

public class Scoreboard {
    private int score;

    public Scoreboard() {
        score = 0;
    }

    public void incrementScore(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }
}
