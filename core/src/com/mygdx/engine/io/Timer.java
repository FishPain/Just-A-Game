package com.mygdx.engine.io;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;

public class Timer implements Disposable {
    private long startTime;
    private long countdownTime;
    private boolean isRunning;
    private BitmapFont font;
    private float x, y;

    public Timer(float x, float y, int countdownSeconds) {
        this.x = x;
        this.y = y;
        this.font = new BitmapFont();
        this.countdownTime = countdownSeconds * 1000; // Convert seconds to milliseconds
        resetTimer();
    }

    public void startTimer() {
        if (!isRunning) {
            startTime = TimeUtils.millis();
            isRunning = true;
        }
    }

    public void stopTimer() {
        isRunning = false;
    }

    public void resetTimer() {
        startTime = TimeUtils.millis();
        isRunning = false;
    }

    public void updateAndRender(SpriteBatch batch) {
        if (isRunning) {
            long currentTime = TimeUtils.millis();
            long elapsedTime = currentTime - startTime;
            long remainingTime = countdownTime - elapsedTime;
            if (remainingTime < 0) {
                remainingTime = 0;
                stopTimer(); // Optionally stop the timer when it reaches 0
            }
            String timeString = "Time: " + (remainingTime / 1000) + " seconds";
            font.draw(batch, timeString, x, y);
        }
    }

    public int getRemainingTime() {
        long currentTime = TimeUtils.millis();
        long elapsedTime = currentTime - startTime;
        long remainingTime = countdownTime - elapsedTime;
        if (remainingTime < 0) {
            remainingTime = 0;
        }
        return (int) (remainingTime / 1000);
    }

    public long getElapsedTime() {
        return TimeUtils.timeSinceMillis(startTime) / 1000;
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
