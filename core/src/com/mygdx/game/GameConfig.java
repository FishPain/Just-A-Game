package com.mygdx.game;

<<<<<<< Updated upstream
=======
import com.mygdx.engine.entity.EntityType;
import com.mygdx.engine.io.SoundEffects;
import com.mygdx.engine.scene.SceneType;

>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======
    public static final float GRAVITY = -100f;

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
>>>>>>> Stashed changes

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

}
