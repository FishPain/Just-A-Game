package com.mygdx.game;

import com.mygdx.engine.entity.EntityType;
import com.mygdx.engine.scene.SceneType;

import java.awt.Dimension;
import java.awt.Toolkit;

public class GameConfig {
    public static final int SCREEN_WIDTH;
    public static final int SCREEN_HEIGHT;
    public static final boolean RESIZABLE = false;
    public static final int FPS = 60;
    public static final String TITLE = "My GDX Game";
    public static final float GRAVITY = 200f;

    // Static initializer block to set screen size based on the operating system
    static {
        String osName = System.getProperty("os.name").toLowerCase();
        System.out.println("Operating System Name: " + osName);

        // Assuming you want to use GraphicsEnvironment for non-Windows systems as an
        // example
        if (osName.contains("windows")) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            SCREEN_WIDTH = screenSize.width / 2;
            SCREEN_HEIGHT = screenSize.height / 2;
        } else {
            SCREEN_WIDTH = 800;
            SCREEN_HEIGHT = 600;
        }
    }

    public static enum GameEntityType implements EntityType {
        SNAKE_HEAD, SNAKE_BODY, PLATFORM, TARGET
    }

    public static enum GameSceneType implements SceneType {
        MAIN_MENU, GAME_SCENE, GAME_OVER_WIN, GAME_OVER_LOSE, SETTINGS
    }

    public static enum Assets {
        SNAKE_HEAD("snakeHead.jpg"),
        SNAKE_BODY("snakeBody.jpg"),
        PLATFORM("stoneTex.jpg"),
        TARGET("badlogic.jpg"),
        MAIN_MENU_BG("mainMenuBg.jpg"),
        PLAY_BTN("playBtn.png"),
        RESTART_BTN("restartBtn.png"),
        SETTINGS_BTN("settingsBtn.png");

        private String fileName;

        private Assets(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }
}
