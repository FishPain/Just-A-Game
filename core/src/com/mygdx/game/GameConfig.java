package com.mygdx.game;

import com.mygdx.engine.entity.EntityType;
import com.mygdx.engine.scene.SceneType;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

public class GameConfig {
    public static final int SCREEN_WIDTH;
    public static final int SCREEN_HEIGHT;
    public static final boolean RESIZABLE = false;
    public static final boolean FULLSCREEN = false;
    public static final int ASSET_SIZE = 50;
    public static final int FPS = 60;
    public static final String TITLE = "My GDX Game";
    public static final float GRAVITY = -100f;

    static {
        String osName = System.getProperty("os.name").toLowerCase();
        System.out.println("Operating System Name: " + osName);
        if (osName.contains("windows")) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            SCREEN_WIDTH = screenSize.width / 2;
            SCREEN_HEIGHT = screenSize.height / 2;
        } else {
            SCREEN_WIDTH = 800;
            SCREEN_HEIGHT = 600;
        }
    }

    public static final ArrayList<Point> PLATFORM_POSITIONS = new ArrayList<Point>() {
        {
            // Bottom edge
            int i = 0;
            while (i * ASSET_SIZE < SCREEN_WIDTH) {
                add(new Point(i * ASSET_SIZE, 0));
                i++;
            }

            // Left edge (excluding corners to avoid duplicates)
            i = 1; // Start from 1 to avoid the bottom-left corner
            while (i * ASSET_SIZE < SCREEN_HEIGHT) {
                add(new Point(0, i * ASSET_SIZE));
                i++;
            }

            // Right edge (excluding corners to avoid duplicates)
            i = 1; // Start from 1 to avoid the bottom-right corner
            while (i * ASSET_SIZE < SCREEN_HEIGHT) {
                add(new Point(SCREEN_WIDTH - ASSET_SIZE, i * ASSET_SIZE));
                i++;
            }

            // middle platforms
            i = 5;
            while (i * ASSET_SIZE < (SCREEN_WIDTH - ASSET_SIZE * 6)) {
                add(new Point(i * ASSET_SIZE, SCREEN_HEIGHT / 4));
                i++;
            }

        }
    };

    public static enum GameEntityType implements EntityType {
        SNAKE_HEAD, SNAKE_BODY, PLATFORM, TARGET
    }

    public static enum GameSceneType implements SceneType {
        MAIN_MENU, GAME_SCENE, GAME_OVER_WIN, GAME_OVER_LOSE, SETTINGS
    }

    public static enum Keystroke {
        UP("UP"),
        DOWN("DOWN"),
        LEFT("LEFT"),
        RIGHT("RIGHT"),
        JUMP("JUMP"),
        SHOOT("SHOOT"),
        FILE_PATH("Settings.json");

        private String keyStrokeName;

        private Keystroke(String keyStrokeName) {
            this.keyStrokeName = keyStrokeName;
        }

        public String getKeystrokeName() {
            return keyStrokeName;
        }
    }

    public static enum Assets {
        SNAKE_HEAD("snakeHead.jpg"),
        SNAKE_BODY("snakeBody.jpg"),
        PLATFORM("stoneTex.jpg"),
        TARGET("badlogic.jpg"),
        MAIN_MENU_BG("mainMenuBg.jpg"),
        PLAY_BTN("playBtn.png"),
        RESTART_BTN("restartBtn.png"),
        SETTINGS_BTN("settingsBtn.png"),
        GAME_OVER_WIN("Game_over.png"),
        GAME_OVER_LOSE("Game_over_lose.png");

        private String fileName;

        private Assets(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }
}
