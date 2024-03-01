package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.mygdx.engine.entity.EntityType;
import com.mygdx.engine.scene.SceneType;
import com.mygdx.engine.io.SoundEffects;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

public class GameConfig {
    public static final int SCREEN_WIDTH;
    public static final int SCREEN_HEIGHT;
    public static final boolean RESIZABLE = false;
    public static final boolean FULLSCREEN = false;
    public static final int FPS = 60;
    public static final String TITLE = "My GDX Game";
    public static final float GRAVITY = -100f;
    public static float MUSIC_VOLUME = 0.2f;
    public static int SNAKE_BODY_LENGTH = 0;
    
    public static int TIME_LIMIT = 7;
    public static final int NUM_OF_OBSTACLES = 100;
    public static final int NUM_OF_TARGETS = 2;
    public static final int PLATFORM_SIZE = 50;
    public static final int SNAKE_SIZE = 25;

    static {
        String osName = System.getProperty("os.name").toLowerCase();
        System.out.println("Operating System Name: " + osName);
        if (osName.contains("windows")) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            SCREEN_WIDTH = screenSize.width;
            System.out.println("SCREEN_WIDTH: " + SCREEN_WIDTH);
            SCREEN_HEIGHT = screenSize.height;
            System.out.println("SCREEN_HEIGHT: " + SCREEN_HEIGHT);
            float widthScaleFactor = (float) SCREEN_WIDTH / 800 ;
            float heightScaleFactor = (float) SCREEN_HEIGHT / 600;
            TIME_LIMIT = Math.round(TIME_LIMIT * widthScaleFactor * heightScaleFactor);
            System.out.println("TIME_LIMIT: " + TIME_LIMIT);

        } else {
            SCREEN_WIDTH = 800;
            SCREEN_HEIGHT = 600;
        }
        // Object soundSettings =
        // Settings.loadSettings(Keystroke.FILE_PATH.getKeystrokeName(),
        // "SoundEffects");
        // MUSIC_VOLUME = (int) ((JSONObject) soundSettings).get("Volume");
    }

    public static final Point SNAKE_START_POSITION = new Point(SCREEN_WIDTH / 2, PLATFORM_SIZE);

    public static final ArrayList<Point> PLATFORM_BORDER_POSITIONS = new ArrayList<Point>() {
        {
            // Bottom edge
            int i = 0;
            while (i * PLATFORM_SIZE < SCREEN_WIDTH) {
                add(new Point(i * PLATFORM_SIZE, 0));
                i++;
            }

            // Left edge (excluding corners to avoid duplicates)
            i = 1; // Start from 1 to avoid the bottom-left corner
            while (i * PLATFORM_SIZE < SCREEN_HEIGHT) {
                add(new Point(0, i * PLATFORM_SIZE));
                i++;
            }

            // Right edge (excluding corners to avoid duplicates)
            i = 1; // Start from 1 to avoid the bottom-right corner
            while (i * PLATFORM_SIZE < SCREEN_HEIGHT) {
                add(new Point(SCREEN_WIDTH - PLATFORM_SIZE, i * PLATFORM_SIZE));
                i++;
            }

            // Top edge
            i = 1;
            while (i * PLATFORM_SIZE < SCREEN_WIDTH) {
                add(new Point(i * PLATFORM_SIZE, SCREEN_HEIGHT - PLATFORM_SIZE));
                i++;
            }

            // // middle platforms
            // for (i = 7; i < 13; i++) {
            // add(new Point(i * PLATFORM_SIZE, SCREEN_HEIGHT / 4));
            // }
        }
    };

    public static enum GameEntityType implements EntityType {
        SNAKE_HEAD, SNAKE_BODY, PLATFORM, TARGET
    }

    public static enum GameSceneType implements SceneType {
        MAIN_MENU {
            private final SoundEffects sound = new SoundEffects("sounds/mainMenuSound.mp3");

            @Override
            public SoundEffects getSound() {
                return sound;
            }
        },
        GAME_SCENE {
            private final SoundEffects sound = new SoundEffects("sounds/gameSceneSound.mp3");

            @Override
            public SoundEffects getSound() {
                return sound;
            }
        },
        GAME_OVER_WIN {
            private final SoundEffects sound = new SoundEffects("sounds/gameOverWinSound.mp3");

            @Override
            public SoundEffects getSound() {
                return sound;
            }
        },
        GAME_OVER_LOSE {
            private final SoundEffects sound = new SoundEffects("sounds/gameOverLoseSound.mp3");

            @Override
            public SoundEffects getSound() {
                return sound;
            }
        },
        SETTINGS {
            private final SoundEffects sound = new SoundEffects("sounds/settingsSound.mp3");

            @Override
            public SoundEffects getSound() {
                return sound;
            }
        };
    }

    public static boolean isMusicEnabled = true;

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
        BACK_BTN("back_btn.png"),
        GAME_OVER_LOSE("game_over_lose.png"),
        GAME_OVER_WIN("game_over_win.png"),
        GAME_SCENE_BG("game_scene_bg.png"),
        MAIN_MENU_BG("main_menu_bg.jpg"),
        PLAY_BTN("play_btn.png"),
        PLATFORM("platform.jpg"),
        RESTART_BTN("restart_btn.png"),
        SETTINGS_BTN("settings_btn.png"),
        SNAKE_BODY("snake_body.jpg"),
        SNAKE_HEAD("snake.png"),
        SOUND_OFF_BTN("sound_off_btn.png"),
        SOUND_ON_BTN("sound_on_btn.png"),
        TARGET("target.png"),
        TIMER_BG("timer_bg.png");

        private String fileName;

        private Assets(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }
}
