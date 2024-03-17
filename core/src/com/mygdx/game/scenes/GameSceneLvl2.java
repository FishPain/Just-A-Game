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
import com.mygdx.game.entity.BlockManager;
import com.mygdx.game.entity.Player;
import java.util.ArrayList;

public class GameSceneLvl2 extends Scene {
    private EntityManager entityManager;
    private SceneManager sceneManager;
    private KeyStrokeManager keyStrokeManager;
    private BlockManager blockManager;
    private Timer timer;
    private boolean isPaused;
    private boolean pauseKeyIsPressed;

    public GameSceneLvl2(SceneManager sceneManager, EntityManager entityManager, KeyStrokeManager keyStrokeManager) {
        super(Assets.GAME_SCENE_BG.getFileName(), Assets.GAME_SCENE_SOUND.getFileName());
        this.sceneManager = sceneManager;
        this.entityManager = entityManager;
        this.keyStrokeManager = keyStrokeManager;
        this.blockManager = new BlockManager();
        this.timer = new Timer(GameConfig.SCREEN_WIDTH / 2 - 50, GameConfig.SCREEN_HEIGHT - 50,
                GameConfig.TIME_LIMIT);
        this.isPaused = false;
        this.pauseKeyIsPressed = false;
    }

    @Override
    public void show() {
        // spawn the player
        entityManager.addPlayer(
                new Player(GameConfig.PLAYER_START_POSITION.x, GameConfig.PLAYER_START_POSITION.y,
                        GameConfig.PLAYER_SIZE,
                        GameConfig.PLAYER_SIZE,
                        200, Assets.PLAYER_HEAD.getFileName(), Assets.PLAYER_BODY.getFileName(),
                        GameEntityType.PLAYER_HEAD, keyStrokeManager, entityManager));

        // spawn the block borders
        entityManager.addEntities(blockManager.createBlocks(GameConfig.BLOCK_BORDER_POSITIONS));

        // spawn exit portal
        entityManager.addEntity(blockManager.createExitPortal(GameConfig.EXIT_PORTAL_POSITION));

        // randomly spawn the blocks obstacles
        entityManager.addEntities(blockManager.createRandomBlocks(GameConfig.NUM_OF_OBSTACLES,
                entityManager.getAllEntityPosition(), Assets.BLOCK.getFileName(), GameEntityType.BLOCK));

        // randomly spawn the apples
        entityManager.addEntities(blockManager.createRandomBlocks(GameConfig.NUM_OF_APPLES,
                entityManager.getAllEntityPosition(), Assets.APPLE.getFileName(), GameEntityType.APPLE));

        // randomly spawn the burgers
        entityManager.addEntities(blockManager.createRandomBlocks(GameConfig.NUM_OF_BURGERS,
                entityManager.getAllEntityPosition(), Assets.BURGER.getFileName(), GameEntityType.BURGER));

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
        entityManager.dispose(batch);
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
        GameSceneType nextScene = null;
        for (Entity entity : entities) {
            entity.draw(batch);
            entity.move(entityManager.getAllCollidableEntity(), delta);
            if (entity.isGameEnd()) {
                nextScene = GameSceneType.GAME_OVER_WIN;
            } else if (timer.getRemainingTime() <= 0) {
                nextScene = GameSceneType.GAME_OVER_LOSE;
            }
        }
        entityManager.removeEntities();
        if (nextScene != null)
            sceneManager.setScene(nextScene);
    }

    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            timer.pauseTimer();
            entityManager.setMovability(entityManager.getEntities(GameConfig.GameEntityType.PLAYER_HEAD), false);
        } else {
            timer.resumeTimer();
            entityManager.setMovability(entityManager.getEntities(GameConfig.GameEntityType.PLAYER_HEAD), true);
        }
    }
}
