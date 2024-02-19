package com.mygdx.game;

import com.mygdx.engine.entity.EntityType;
import com.mygdx.engine.scene.SceneType;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.engine.io.KeyStrokeType;
import com.badlogic.gdx.Input.Keys;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.RenderingHints.Key;
import java.util.HashMap;

public class GameConfig {
    public static final int SCREEN_WIDTH;
    public static final int SCREEN_HEIGHT;
    public static final boolean RESIZABLE = false;
    public static final int FPS = 60;
    public static final String TITLE = "My GDX Game";
    public static final float GRAVITY = 200f;

    public static enum GameEntityType implements EntityType {
        SNAKE_HEAD, SNAKE_BODY, PLATFORM, TARGET
    }

    public static enum GameSceneType implements SceneType {
        MAIN_MENU, GAME_SCENE, GAME_OVER_WIN, GAME_OVER_LOSE, SETTINGS
    }

    public static enum GameKeyStrokeType implements KeyStrokeType {
        UP, DOWN, LEFT, RIGHT, JUMP, SHOOT
    }

    public static HashMap<GameKeyStrokeType, Integer> keyStrokeMap = new HashMap<GameKeyStrokeType, Integer>() {
        {
            put(GameKeyStrokeType.UP, KeyStrokeManager.W);
            put(GameKeyStrokeType.DOWN, KeyStrokeManager.S);
            put(GameKeyStrokeType.LEFT, KeyStrokeManager.A);
            put(GameKeyStrokeType.RIGHT, KeyStrokeManager.D);
            put(GameKeyStrokeType.JUMP, KeyStrokeManager.SPACE);
            put(GameKeyStrokeType.SHOOT, KeyStrokeManager.SHIFT_LEFT);
        }
    };

    public static enum Assets {
        SNAKE_HEAD("snakeHead.jpg"),
        SNAKE_BODY("snakeBody.jpg"),
        PLATFORM("stoneTex.jpg"),
        TARGET("badlogic.jpg"),
        MAIN_MENU_BG("mainMenuBg.jpg"),
        PLAY_BTN("playBtn.jpg"),
        RESTART_BTN("restartBtn.jpg"),
        SETTINGS_BTN("settingsBtn.jpg");

        private String fileName;

        private Assets(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }

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

}
