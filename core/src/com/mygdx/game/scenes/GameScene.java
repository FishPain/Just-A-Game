package com.mygdx.game.scenes;

import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.engine.io.SoundEffects;
import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.scene.SceneManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.io.Timer;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.GameConfig.GameSceneType;
import com.mygdx.game.ai.AIBot;
import com.mygdx.game.entity.PlatformManager;
import com.mygdx.game.entity.Snake;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

public class GameScene extends Scene {
    private EntityManager entityManager;
    private SceneManager sceneManager;
    private SoundEffects sound;
    private KeyStrokeManager keyStrokeManager;
    private PlatformManager platformManager;
    private Timer timer;

    // Timer timer;

    public GameScene(SceneManager sceneManager, EntityManager entityManager, KeyStrokeManager keyStrokeManager) {
        super();
        this.sceneManager = sceneManager;
        this.entityManager = entityManager;
        this.keyStrokeManager = keyStrokeManager;
        this.platformManager = new PlatformManager();
        this.sound = GameSceneType.GAME_SCENE.getSound();
        this.timer = new Timer(new SpriteBatch(), GameConfig.SCREEN_WIDTH / 2 - 50, GameConfig.SCREEN_HEIGHT - 50,
                GameConfig.TIME_LIMIT);
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

        entityManager.addAIBot(new AIBot(GameConfig.SCREEN_WIDTH / 2 + 100, GameConfig.SCREEN_HEIGHT / 2 + 100, 50, 50,
        200, Assets.SNAKE_HEAD.getFileName(), Assets.SNAKE_BODY.getFileName(),
        GameEntityType.SNAKE_HEAD));

        timer.startTimer();
        sound.play(GameConfig.MUSIC_VOLUME);
        if (!GameConfig.isMusicEnabled) {
            sound.stop();
        }
    }

    @Override
    public void hide() {
        timer.resetTimer();
        sound.stop();
    }

    @Override
    public void render(float delta) {
        ArrayList<Entity> entities = entityManager.getEntities();

        timer.updateAndRender();
        for (Entity entity : entities) {
            entity.draw(batch);
            entity.move(entities);

            if (entity.isGameEnd()) {
                sceneManager.setScene(GameSceneType.GAME_OVER_WIN);
            } else if (timer.getRemainingTime() <= 0) {
                sceneManager.setScene(GameSceneType.GAME_OVER_LOSE);
            }
        }
        entityManager.removeEntities();
    }
}
