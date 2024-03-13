package com.mygdx.game.scenes;

import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.scene.SceneManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.io.Timer;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.GameConfig.GameSceneType;
import com.mygdx.game.entity.PlatformManager;
import com.mygdx.game.entity.Snake;
import java.util.ArrayList;

public class GameScene extends Scene {
    private EntityManager entityManager;
    private SceneManager sceneManager;
    private KeyStrokeManager keyStrokeManager;
    private PlatformManager platformManager;
    private Timer timer;
    private boolean isPaused;
    private boolean pauseKeyIsPressed;

    public GameScene(SceneManager sceneManager, EntityManager entityManager, KeyStrokeManager keyStrokeManager) {
        super(Assets.GAME_SCENE_BG.getFileName(), Assets.GAME_SCENE_SOUND.getFileName());
        this.sceneManager = sceneManager;
        this.entityManager = entityManager;
        this.keyStrokeManager = keyStrokeManager;
        this.platformManager = new PlatformManager();
        this.timer = new Timer(GameConfig.SCREEN_WIDTH / 2 - 50, GameConfig.SCREEN_HEIGHT - 50,
                GameConfig.TIME_LIMIT);
        this.isPaused = false;
        this.pauseKeyIsPressed = false;
    }

    @Override
    public void show() {
        // spawn the player
        entityManager.addPlayer(
                new Snake(GameConfig.SNAKE_START_POSITION.x, GameConfig.SNAKE_START_POSITION.y, GameConfig.SNAKE_SIZE,
                        GameConfig.SNAKE_SIZE,
                        200, Assets.SNAKE_HEAD.getFileName(), Assets.SNAKE_BODY.getFileName(),
                        GameEntityType.SNAKE_HEAD, keyStrokeManager, entityManager));

        // spawn the platform borders
        entityManager.addEntities(platformManager.createPlatforms(GameConfig.PLATFORM_BORDER_POSITIONS));

        // randomly spawn the platforms obstacles
        entityManager.addEntities(platformManager.createRandomPlatforms(GameConfig.NUM_OF_OBSTACLES,
                entityManager.getAllEntityPosition(), Assets.PLATFORM.getFileName(), GameEntityType.PLATFORM));

        // randomly spawn the target obstacles
        entityManager.addEntities(platformManager.createRandomPlatforms(GameConfig.NUM_OF_TARGETS,
                entityManager.getAllEntityPosition(), Assets.TARGET.getFileName(), GameEntityType.TARGET));

        timer.startTimer();
        if (GameConfig.isMusicEnabled)
        playBackgroundMusic(GameConfig.MUSIC_VOLUME);
    }

    @Override
    public void hide() {
        timer.resetTimer();
        if (GameConfig.isMusicEnabled) {
            stopBackgroundMusic();
        }
    }

    @Override
    public void render(float delta) {
        renderBackground(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        timer.updateAndRender(batch);

        if (keyStrokeManager.isKeyPressed(GameConfig.Keystroke.PAUSE_RESUME.getKeystrokeName())) {
            if (!pauseKeyIsPressed) {
                togglePause();
                pauseKeyIsPressed = true;
            }
        } else {
            pauseKeyIsPressed = false;
        }

        ArrayList<Entity> entities = entityManager.getEntities();
        for (Entity entity : entities) {
            entity.draw(batch);
            entity.move(entities, delta);
            if (entity.isGameEnd()) {
                sceneManager.setScene(GameSceneType.GAME_OVER_WIN);
            } else if (timer.getRemainingTime() <= 0) {
                sceneManager.setScene(GameSceneType.GAME_OVER_LOSE);
            }
        }
        entityManager.removeEntities();
    }

    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            timer.pauseTimer();
            entityManager.setMovability(entityManager.getEntities(GameConfig.GameEntityType.SNAKE_HEAD), false);
        } else {
            timer.resumeTimer();
            entityManager.setMovability(entityManager.getEntities(GameConfig.GameEntityType.SNAKE_HEAD), true);
        }
    }
}
