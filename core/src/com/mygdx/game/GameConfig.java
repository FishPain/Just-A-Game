package com.mygdx.game;

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
    public static final int NUM_OF_OBSTACLES = 40;
    public static final int NUM_OF_APPLES = 3;
    public static final int APPLE_EFFECT_TIME = 2;
    public static final float APPLE_SPEED_MULTIPLIER = 1.5f;
    public static final int NUM_OF_BURGERS = 6;
    public static final int BURGER_EFFECT_TIME = 1;
    public static final float BURGER_SPEED_MULTIPLIER = 2f;
    public static final int NUM_OF_CARROTS = 3;
    public static final int CARROT_EFFECT_TIME = 10;
    public static final int BLOCK_SIZE = 50;
    public static final int PLAYER_WIDTH = 25;
    public static final int PLAYER_HEIGHT = 40;
    public static final int PLAYER_STAMINA = 100;
    public static final int PLAYER_POINTS_TO_WIN = NUM_OF_APPLES;
    public static final int AI_PLAYER_POINTS_TO_WIN = NUM_OF_APPLES + 1;
    public static final int TIME_LIMIT_LEVEL_1 = 15;
    public static final int TIME_LIMIT_LEVEL_2 = 25;
    public static final int PLAYER_SPEED = 200;
    public static final int AI_SPEED = 80;
    public static float MUSIC_VOLUME = 0.2f;
    public static boolean IS_MUSIC_ENABLED = true;

    // increase to slow down the entity
    public static final float SPEED_REDUCTION_FACTOR = 1.5f;
    public static final int BUTTON_FONT_SIZE = 20;
    public static final String SETTINGS_FILE_PATH = "settings.json";
    public static final String SETTINGS_SOUND_KEY = "SoundEffects";
    public static final String SETTINGS_KEYSTROKES_KEY = "KeyStrokes";

    static {
        String osName = System.getProperty("os.name").toLowerCase();
        System.out.println("Operating System Name: " + osName);

        if (osName.contains("windows")) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = screenSize.width;
            int height = screenSize.height;

            if (Math.abs(width - 1920) < Math.abs(width - 800) &&
                    Math.abs(height - 1080) < Math.abs(height - 600)) { // Adjusted resolution check
                SCREEN_WIDTH = 1920;
                SCREEN_HEIGHT = 1080;
            } else {
                SCREEN_WIDTH = 800;
                SCREEN_HEIGHT = 600;
            }

            System.out.println("SCREEN_WIDTH: " + SCREEN_WIDTH);
            System.out.println("SCREEN_HEIGHT: " + SCREEN_HEIGHT);

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
        PLAYER("PLAYER"),
        AI_PLAYER("AI_PLAYER"),
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

    public static enum GameSceneType {
        MAIN_MENU("MAIN_MENU"),
        GAME_SCENE_LVL1("GAME_SCENE_LVL1"),
        GAME_SCENE_LVL2("GAME_SCENE_LVL2"),
        GAME_OVER_WIN("GAME_OVER_WIN"),
        GAME_OVER_LOSE("GAME_OVER_LOSE"),
        SETTINGS("SETTINGS"),
        TUTORIAL("TUTORIAL");

        private String value;

        private GameSceneType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        // Method to get enum based on string value
        public static GameSceneType fromValue(String value) {
            for (GameSceneType type : GameSceneType.values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No enum constant with value: " + value);
        }
    }

    public static enum Keystroke {
        UP("UP"),
        DOWN("DOWN"),
        LEFT("LEFT"),
        RIGHT("RIGHT"),
        JUMP("JUMP"),
        SHOOT("SHOOT"),
        PAUSE_RESUME("PAUSE_RESUME"),
        FILE_PATH(SETTINGS_FILE_PATH);

        private String keyStrokeName;

        private Keystroke(String keyStrokeName) {
            this.keyStrokeName = keyStrokeName;
        }

        public String getKeystrokeName() {
            return keyStrokeName;
        }
    }

    public enum GameButtonType {
        PLAY("PLAY_BTN"),
        SETTINGS("SETTINGS_BTN"),
        QUIT("QUIT_BTN"),
        START("START_BTN"),
        RESTART("RESTART_BTN"),
        BACK("BACK_BTN"),
        NEXT_LEVEL("NEXT_LEVEL"),
        SOUND_TOGGLE("SOUND_TOGGLE"),
        RESUME("RESUME_BTN"),
        MAIN_MENU("MAIN_MENU_BTN");

        private final String value;

        GameButtonType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        // Method to get enum based on string value
        public static GameButtonType fromValue(String value) {
            for (GameButtonType type : GameButtonType.values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No enum constant with value: " + value);
        }
    }

    public static enum GameButtonText {
        BACK_BTN("BACK"),
        PLAY_BTN("PLAY"),
        START_BTN("START"),
        RESTART_BTN("RESTART"),
        SETTINGS_BTN("SETTINGS"),
        NEXT_LEVEL_BTN("NEXT LEVEL"),
        SOUND_OFF_BTN("sound_off_btn.png"),
        SOUND_ON_BTN("sound_on_btn.png"),
        QUIT_BTN("QUIT"),
        RESUME_BTN("RESUME"),
        MAIN_MENU_BTN("MAIN MENU");

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
        MAIN_MENU_BG("main_menu_bg.png"),
        TUTORIAL_BG("tutorial.png"),
        TIMER_BG("timer_bg.png"),
        PAUSE_OVERLAY_BG("pause_game.jpg"),
        SETTINGS_BG("settings_bg.png"),

        // Entity textures
        BLOCK("block.jpg"),
        PLAYER("player1.png"),
        APPLE("apple.png"),
        CARROT("carrot.png"),
        BURGER("burger.png"),
        EXIT_PORTAL("exit_portal.png"),

        // Buttons
        BACK_BTN("back_btn.png"),
        PLAY_BTN("play_btn.png"),
        START_BTN("start_btn.png"),
        RESTART_BTN("restart_btn.png"),
        SETTINGS_BTN("settings_btn.png"),
        SOUND_OFF_BTN("sound_off_btn.png"),
        SOUND_ON_BTN("sound_on_btn.png"),
        BUTTON_BG("buttonLong_beige.png"),

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

}
