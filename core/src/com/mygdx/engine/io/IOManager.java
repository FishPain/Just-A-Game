package com.mygdx.engine.io;

public class IOManager {
    private String inputType;
    private String inputSource;

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getInputSource() {
        return inputSource;
    }

    public void setInputSource(String inputSource) {
        this.inputSource = inputSource;
    }

    public void processInput() {
        // Code to process input
    }

    public void print(String message) {
        System.out.println(message);
    }

    public void display(String image) {
        // Code to display the image on the screen
    }

    public void playSound(String sound) {
        // Code to play the specified sound
    }

    public void showMessage(String message) {
        // Code to display a message on the screen
    }

    public void playSoundEffect(String filePath, float volume) {
        SoundEffects soundEffect = new SoundEffects(filePath);
        soundEffect.play(volume);
        soundEffect.dispose();
    }

    public void incrementScore(Scoreboard scoreBoard, int points) {
        scoreBoard.incrementScore(points);
    }

    public int getScore(Scoreboard scoreBoard) {
        return scoreBoard.getScore();
    }

    public void incrementPlayerScore(Player player, int points) {
        player.incrementScore(points);
    }

    public int getPlayerScore(Player player) {
        return player.getScore();
    }

    public void resetPlayerScore(Player player) {
        player.resetScore();
    }

    public void resetPlayerDifficulty(Player player) {
        player.resetDifficulty();
    }

    public void resetPlayer(Player player) {
        player.resetPlayer();
    }
}
