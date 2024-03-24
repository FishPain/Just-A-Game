package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.mygdx.engine.scene.SceneType;
import com.mygdx.engine.io.ButtonType;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

public class GameConfig {
    public static final int SCREEN_WIDTH;
    public static final int SCREEN_HEIGHT;
    public static final boolean RESIZABLE = false;
    public static final boolean FULLSCREEN = false;
    public static final int FPS = 60;
    public static final String TITLE = "My Goopy Foody Game";
    public static final float GRAVITY = -100f;
    public static float MUSIC_VOLUME = 0.2f;
    public static int PLAYER_BODY_LENGTH = 0;
    public static int TIME_LIMIT = 5;
    public static final int NUM_OF_OBSTACLES = 100;
    public static final int NUM_OF_APPLES = 3;
    public static final int APPLE_EFFECT_TIME = 2;
    public static final float APPLE_SPEED_MULTIPLIER = 1.5f;
    public static final int NUM_OF_BURGERS = 6;
    public static final int BURGER_EFFECT_TIME = 1;
    public static final float BURGER_SPEED_MULTIPLIER = 2f;
    public static final int NUM_OF_CARROTS = 3;
    public static final int BLOCK_SIZE = 50;
    public static final int PLAYER_SIZE = 25;
    public static final int AI_PLAYER_SIZE = 30;
    public static final int PLAYER_STAMINA = 100;
    public static int PLAYER_SPEED = 200;

    // increase to slow down the entity
    public static final float SPEED_REDUCTION_FACTOR = 1.5f;
    public static boolean IS_MUSIC_ENABLED = false;
    public static final int BUTTON_FONT_SIZE = 20;

    static {
        String osName = System.getProperty("os.name").toLowerCase();
        System.out.println("Operating System Name: " + osName);
        if (osName.contains("windows")) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = screenSize.width;
            int height = screenSize.height;

            if (Math.abs(width - 1920) < Math.abs(width - 800) && // Resolution set based on screen size
                    Math.abs(height - 800) < Math.abs(height - 600)) {
                SCREEN_WIDTH = 1920;
                SCREEN_HEIGHT = 1080;
            } else {
                SCREEN_WIDTH = 800;
                SCREEN_HEIGHT = 600;
            }

            System.out.println("SCREEN_WIDTH: " + SCREEN_WIDTH);
            System.out.println("SCREEN_HEIGHT: " + SCREEN_HEIGHT);
            float widthScaleFactor = (float) SCREEN_WIDTH / 800;
            float heightScaleFactor = (float) SCREEN_HEIGHT / 600;
            TIME_LIMIT = Math.round(TIME_LIMIT * widthScaleFactor * heightScaleFactor);
            System.out.println("TIME_LIMIT: " + TIME_LIMIT);

        } else {
            SCREEN_WIDTH = 800;
            SCREEN_HEIGHT = 600;
        }
    }
    public static final Point PLAYER_START_POSITION = new Point(SCREEN_WIDTH / 2, BLOCK_SIZE);
    public static final Point AI_PLAYER_START_POSITION = new Point(SCREEN_WIDTH / 3, BLOCK_SIZE);
    public static final Point EXIT_PORTAL_POSITION = new Point(SCREEN_WIDTH / 2, BLOCK_SIZE);
    public static final float BUTTON_WIDTH = SCREEN_WIDTH / 16 + 60;
    public static final float BUTTON_HEIGHT = SCREEN_HEIGHT / 9 - 60;

    public static final ArrayList<Point> BLOCK_BORDER_POSITIONS = new ArrayList<Point>() {
        {
            // Bottom edge
            int i = 0;
            while (i * BLOCK_SIZE < SCREEN_WIDTH) {
                add(new Point(i * BLOCK_SIZE, 0));
                i++;
            }

            // Left edge (excluding corners to avoid duplicates)
            i = 1; // Start from 1 to avoid the bottom-left corner
            while (i * BLOCK_SIZE < SCREEN_HEIGHT) {
                add(new Point(0, i * BLOCK_SIZE));
                i++;
            }

            // Right edge (excluding corners to avoid duplicates)
            i = 1; // Start from 1 to avoid the bottom-right corner
            while (i * BLOCK_SIZE < SCREEN_HEIGHT) {
                add(new Point(SCREEN_WIDTH - BLOCK_SIZE, i * BLOCK_SIZE));
                i++;
            }

            // Top edge
            i = 1;
            while (i * BLOCK_SIZE < SCREEN_WIDTH) {
                add(new Point(i * BLOCK_SIZE, SCREEN_HEIGHT - BLOCK_SIZE));
                i++;
            }
        }
    };

    public enum GameEntityType {
        PLAYER_HEAD("PLAYER_HEAD"),
        PLAYER_BODY("PLAYER_BODY"),
        BLOCK("BLOCK"),
        APPLE("APPLE"),
        BURGER("BURGER"),
        RAIN("RAIN"),
        EXIT_PORTAL("EXIT_PORTAL"),
        CARROT("CARROT");

        private final String value;

        GameEntityType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        // Method to get enum based on string value
        public static GameEntityType fromValue(String value) {
            for (GameEntityType type : GameEntityType.values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No enum constant with value: " + value);
        }
    }

    public static enum GameSceneType implements SceneType {
        MAIN_MENU,
        GAME_SCENE_LVL1,
        GAME_SCENE_LVL2,
        GAME_OVER_WIN,
        GAME_OVER_LOSE,
        SETTINGS,
        TUTORIAL
    }

    public static enum Keystroke {
        UP("UP"),
        DOWN("DOWN"),
        LEFT("LEFT"),
        RIGHT("RIGHT"),
        JUMP("JUMP"),
        SHOOT("SHOOT"),
        PAUSE_RESUME("PAUSE_RESUME"),
        FILE_PATH("Settings.json");

        private String keyStrokeName;

        private Keystroke(String keyStrokeName) {
            this.keyStrokeName = keyStrokeName;
        }

        public String getKeystrokeName() {
            return keyStrokeName;
        }
    }

    public static enum GameButtonType implements ButtonType {
        PLAY, SETTINGS, QUIT, START, RESTART, BACK, SOUND_ON, SOUND_OFF
    }

    public static enum GameButtonText {
        BACK_BTN("BACK"),
        PLAY_BTN("PLAY"),
        START_BTN("START"),
        RESTART_BTN("RESTART"),
        SETTINGS_BTN("SETTINGS"),
        SOUND_OFF_BTN("sound_off_btn.png"),
        SOUND_ON_BTN("sound_on_btn.png"),
        QUIT_BTN("QUIT");

        private String text;

        private GameButtonText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public static enum Assets {
        // Backgrounds
        GAME_OVER_LOSE("game_over_lose.png"),
        GAME_OVER_WIN("game_over_win.png"),
        GAME_SCENE_BG("game_scene_bg.png"),
        MAIN_MENU_BG("main_menu_bg.jpg"),
        TUTORIAL_BG("tutorial.png"),
        TIMER_BG("timer_bg.png"),

        // Entity textures
        BLOCK("block.jpg"),
        PLAYER_BODY("player_body.jpg"),
        PLAYER_HEAD("player.png"),
        APPLE("apple.png"),
        CARROT("droplet.png"),
        BURGER("burger.png"),
        RAIN("droplet.png"),
        LOGO("logo.png"),
        EXIT_PORTAL("droplet.png"),

        // Buttons
        BACK_BTN("back_btn.png"),
        PLAY_BTN("play_btn.png"),
        START_BTN("start_btn.png"),
        RESTART_BTN("restart_btn.png"),
        SETTINGS_BTN("settings_btn.png"),
        SOUND_OFF_BTN("sound_off_btn.png"),
        SOUND_ON_BTN("sound_on_btn.png"),
        BUTTON_BG("ui_pack/PNG/buttonLong_beige.png"),

        // Sounds
        MAIN_MENU_SOUND("sounds/mainMenuSound.ogg"),
        GAME_SCENE_SOUND("sounds/gameSceneSound.ogg"),
        GAME_OVER_WIN_SOUND("sounds/gameOverWinSound.ogg"),
        GAME_OVER_LOSE_SOUND("sounds/gameOverLoseSound.ogg"),
        SETTINGS_SOUND("sounds/settingsSound.ogg"),
        TUTORIAL_SOUND("sounds/settingsSound.ogg"),

        // Fonts
        FONT_PATH("fonts/JungleAdventurer.ttf");

        private String fileName;

        private Assets(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }

    public static int Xscale() {
        return Gdx.graphics.getWidth() / 16;
    }

    public static int Yscale() {
        return Gdx.graphics.getHeight() / 9;
    }

    public static float PLAYER_SPEED() {
        PLAYER_SPEED = 200;
        throw new UnsupportedOperationException("Unimplemented method 'PLAYER_SPEED'");
    }
}
