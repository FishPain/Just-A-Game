package com.mygdx.engine.io;

public class Player {
    private String name;
    private int score;
    private int difficulty;

    public Player(String name) {
        this.name = name;
        score = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void incrementScore(int points) {
        score += points;
    }

    public void resetScore() {
        score = 0;
    }

    public void resetDifficulty() {
        difficulty = 0;
    }

    public void resetPlayer() {
        resetScore();
        resetDifficulty();
    }
}
