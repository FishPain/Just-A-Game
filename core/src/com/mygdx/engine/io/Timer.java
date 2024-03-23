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
    private boolean isPaused;
    private long pauseStartTime;
    private long pausedTime;

    public Timer(float x, float y, int countdownSeconds) {
        this.x = x;
        this.y = y;
        this.isPaused = false;
        this.pauseStartTime = 0;
        this.pausedTime = 0;
        this.font = new BitmapFont();
        this.countdownTime = countdownSeconds * 1000; // Convert seconds to milliseconds
        resetTimer();
    }

    public void startTimer() {
        if (!isRunning) {
            long currentTime = TimeUtils.millis();
            if (!isPaused) {
                startTime = currentTime;
            } else {
                long pauseDuration = currentTime - pauseStartTime;
                startTime += pauseDuration; // Adjust start time to account for pause time
            }
            isRunning = true;
            isPaused = false;
        }
    }

    public void pauseTimer() {
        if (isRunning && !isPaused) {
            isPaused = true;
            pauseStartTime = TimeUtils.millis();
        }
    }

    public void resumeTimer() {
        if (isRunning && isPaused) {
            isPaused = false;
            long currentTime = TimeUtils.millis();
            long pauseDuration = currentTime - pauseStartTime;
            pausedTime += pauseDuration;
        }
    }

    public void stopTimer() {
        isRunning = false;
    }

    public void resetTimer() {
        startTime = TimeUtils.millis();
        isRunning = false;
        isPaused = false;
        pausedTime = 0;
    }

    public void updateAndRender(SpriteBatch batch) {
        if (isRunning) {
            if (isPaused) {
                font.draw(batch, "Paused", x, y);
            } else {
                long currentTime = TimeUtils.millis();
                long elapsedTime = currentTime - startTime - pausedTime; // Subtract paused time from elapsed time
                long remainingTime = countdownTime - elapsedTime;
                if (remainingTime < 0) {
                    remainingTime = 0;
                    stopTimer(); // Optionally stop the timer when it reaches 0
                }
                String timeString = "Time: " + (remainingTime / 1000) + " seconds";
                font.draw(batch, timeString, x, y);
            }
        }
    }

    public void updateEffect() {
        if (isRunning) {
            long currentTime = TimeUtils.millis();
            long elapsedTime = currentTime - startTime - pausedTime; // Subtract paused time from elapsed time
            long remainingTime = countdownTime - elapsedTime;
            if (remainingTime < 0) {
                remainingTime = 0;
                stopTimer(); // Optionally stop the timer when it reaches 0
            }
        }
    }

    public int getRemainingTime() {
        long currentTime = TimeUtils.millis();
        long elapsedTime = currentTime - startTime - pausedTime; // Subtract paused time from elapsed time
        long remainingTime = countdownTime - elapsedTime;
        if (remainingTime < 0) {
            remainingTime = 0;
        }
        return (int) (remainingTime / 1000);
    }

    public long getElapsedTime() {
        long currentTime = TimeUtils.millis();
        long elapsedTime = currentTime - startTime - pausedTime; // Subtract paused time from elapsed time
        return elapsedTime / 1000;
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
