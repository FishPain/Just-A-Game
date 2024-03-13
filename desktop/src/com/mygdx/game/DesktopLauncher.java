package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;


public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

        // Set fixed window size
        config.setWindowedMode(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT); // Windowed mode
        // config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode()); // Full screen
        
        config.setResizable(GameConfig.RESIZABLE); // Make window not resizable

        // Configure other settings
        config.setForegroundFPS(GameConfig.FPS);
        config.setTitle(GameConfig.TITLE);

        // Note: For macOS, ensure to start the application with -XstartOnFirstThread
        // JVM argument
        new Lwjgl3Application(new Game(), config);
    }
}
