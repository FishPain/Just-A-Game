package com.mygdx.game.scenes;

import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.scene.SceneManager;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.io.Timer;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.GameConfig.GameSceneType;
import com.mygdx.game.entity.BlockManager;
import com.mygdx.game.entity.Player;
import java.util.ArrayList;

public class GameSceneLvl1 extends Scene {
    private EntityManager entityManager;
    private SceneManager sceneManager;
    private KeyStrokeManager keyStrokeManager;
    private BlockManager blockManager;
    private Timer timer;
    private Player player;
    private GameSceneType nextScene;
    private boolean isPaused;
    private boolean pauseKeyIsPressed;

    public GameSceneLvl1(SceneManager sceneManager, EntityManager entityManager, KeyStrokeManager keyStrokeManager) {
        super(Assets.GAME_SCENE_BG.getFileName(), Assets.GAME_SCENE_SOUND.getFileName());
        this.sceneManager = sceneManager;
        this.entityManager = entityManager;
        this.keyStrokeManager = keyStrokeManager;
        this.blockManager = new BlockManager();
        this.timer = new Timer(GameConfig.SCREEN_WIDTH / 2 - 50, GameConfig.SCREEN_HEIGHT - 50,
                GameConfig.TIME_LIMIT);
        this.isPaused = false;
        this.pauseKeyIsPressed = false;
        this.nextScene = null;
    }

    @Override
    public void show() {
        this.player = new Player(GameConfig.PLAYER_START_POSITION.x, GameConfig.PLAYER_START_POSITION.y,
                GameConfig.PLAYER_SIZE,
                GameConfig.PLAYER_SIZE,
                GameConfig.PLAYER_SPEED, Assets.PLAYER_HEAD.getFileName(), Assets.PLAYER_BODY.getFileName(),
                GameEntityType.PLAYER_HEAD.getValue(), keyStrokeManager, entityManager);

        entityManager.addEntity(player);
        // spawn the block borders
        entityManager.addEntities(blockManager.createBlocks(GameConfig.BLOCK_BORDER_POSITIONS));

        // spawn exit portal
        entityManager.addEntity(blockManager.createExitPortal(GameConfig.EXIT_PORTAL_POSITION));

        // randomly spawn the blocks obstacles
        entityManager.addEntities(blockManager.createRandomBlocks(GameConfig.NUM_OF_OBSTACLES,
                entityManager.getAllEntityPosition(), Assets.BLOCK.getFileName(), GameEntityType.BLOCK.getValue()));

        // randomly spawn the apples
        entityManager.addEntities(blockManager.createRandomBlocks(GameConfig.NUM_OF_APPLES,
                entityManager.getAllEntityPosition(), Assets.APPLE.getFileName(), GameEntityType.APPLE.getValue()));

        // randomly spawn the burgers
        entityManager.addEntities(blockManager.createRandomBlocks(GameConfig.NUM_OF_BURGERS,
                entityManager.getAllEntityPosition(), Assets.BURGER.getFileName(), GameEntityType.BURGER.getValue()));

        timer.startTimer();
        if (GameConfig.IS_MUSIC_ENABLED)
            playBackgroundMusic(GameConfig.MUSIC_VOLUME);
    }

    @Override
    public void hide() {
        this.nextScene = null;
        timer.resetTimer();
        entityManager.dispose(batch);
        if (GameConfig.IS_MUSIC_ENABLED) {
            stopBackgroundMusic();
        }
    }

    @Override
    public void render(float delta) {
        renderBackground(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        // update timer
        timer.updateAndRender(batch);
        if (timer.isTimerEnded()) {
            nextScene = GameSceneType.GAME_OVER_LOSE;
        }

        // press esc key to pause the game and resume the game
        if (keyStrokeManager.isKeyPressed(GameConfig.Keystroke.PAUSE_RESUME.getKeystrokeName())) {
            if (!pauseKeyIsPressed) {
                togglePause();
                pauseKeyIsPressed = true;
            }
        } else {
            pauseKeyIsPressed = false;
        }

        // draw and move the entities
        ArrayList<Entity> entities = entityManager.getEntities();
        for (Entity entity : entities) {
            entity.draw(batch);
            entity.move(entityManager.getAllCollidableEntity(), delta);

            // If the player has eaten all the apples, the game ends
            if (entityManager.getEntities(GameEntityType.APPLE.getValue()).size() == 0) {
                for (Entity exitPortal : entityManager.getEntities(GameEntityType.EXIT_PORTAL.getValue())) {
                    if (!exitPortal.isVisable()) {
                        exitPortal.setVisable(true);
                    } else if (CollisionManager.isCollidingWith(entity, exitPortal)) {
                        System.out
                                .println("apple: " + entityManager.getEntities(GameEntityType.APPLE.getValue()).size());
                        nextScene = GameSceneType.GAME_SCENE_LVL2;
                    }
                }
            }
        }

        // bulk remove entity to prevent concurrent modification
        entityManager.removeEntities();

        // set the next scene
        if (nextScene != null)
            sceneManager.setScene(nextScene);
    }

    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            timer.pauseTimer();
            entityManager.setMovability(entityManager.getEntities(GameEntityType.PLAYER_HEAD.getValue()), false);
        } else {
            timer.resumeTimer();
            entityManager.setMovability(entityManager.getEntities(GameEntityType.PLAYER_HEAD.getValue()), true);
        }
    }
}
