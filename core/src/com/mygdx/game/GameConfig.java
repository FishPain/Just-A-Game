package com.mygdx.game;

import com.mygdx.engine.entity.EntityType;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

public class GameConfig {
    public static final int SCREEN_WIDTH;
    public static final int SCREEN_HEIGHT;
    public static final boolean RESIZABLE = false;
    public static final int FPS = 60;
    public static final String TITLE = "My GDX Game";

    // Static initializer block to set screen size based on the operating system
    static {
        String osName = System.getProperty("os.name").toLowerCase();
        System.out.println("Operating System Name: " + osName);

        // Assuming you want to use GraphicsEnvironment for non-Windows systems as an
        // example
        if (osName.contains("windows")) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            SCREEN_WIDTH = screenSize.width;
            SCREEN_HEIGHT = screenSize.height;
        } else {
            SCREEN_WIDTH = 800;
            SCREEN_HEIGHT = 600;
        }
    }

    public static enum GameEntityType implements EntityType {
        SNAKE_HEAD, SNAKE_BODY, PLATFORM
    }

}
