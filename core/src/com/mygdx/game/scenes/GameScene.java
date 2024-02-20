package com.mygdx.game.scenes;

import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.engine.io.SoundEffects;
import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.scene.SceneManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.engine.entity.Entity;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.GameConfig.GameSceneType;
import com.mygdx.game.entity.Platform;
import com.mygdx.game.entity.PlatformManager;
import com.mygdx.game.entity.Snake;
import com.mygdx.engine.io.Timer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class GameScene extends Scene {
    // this class will load all the required entityies using entity manager
    private EntityManager entityManager;
    private SceneManager sceneManager;
    private SoundEffects sound;
    private KeyStrokeManager keyStrokeManager;
    private Timer timer;

    // Timer timer;

    public GameScene(SceneManager sceneManager, EntityManager entityManager, KeyStrokeManager keyStrokeManager) {
        super();
        this.sceneManager = sceneManager;
        this.entityManager = entityManager;
        this.keyStrokeManager = keyStrokeManager;
        this.sound = GameSceneType.GAME_SCENE.getSound();
        this.timer = new Timer(new SpriteBatch(), GameConfig.SCREEN_WIDTH / 2 - 50, GameConfig.SCREEN_HEIGHT - 50,
                GameConfig.TIME_LIMIT);

        entityManager.addEntities(PlatformManager.createPlatforms(GameConfig.PLATFORM_POSITIONS));

        entityManager.addPlayer(
                new Snake(GameConfig.SCREEN_WIDTH / 2, GameConfig.SCREEN_HEIGHT / 2, 50, 50,
                        200, Assets.SNAKE_HEAD.getFileName(), Assets.SNAKE_BODY.getFileName(),
                        GameEntityType.SNAKE_HEAD, keyStrokeManager));
        entityManager.addEntity(
                new Platform(600, 200, 50, 50,
                        Assets.TARGET.getFileName(), GameEntityType.TARGET));
    }

    @Override
    public void show() {
        timer.startTimer();

        // throw new UnsupportedOperationException("Unimplemented method 'show'");
        sound.play(GameConfig.MUSIC_VOLUME);
        if (!GameConfig.isMusicEnabled) {
            sound.stop();
        }
    }

    @Override
    public void hide() {
        timer.resetTimer();
        // throw new UnsupportedOperationException("Unimplemented method 'hide'");
        sound.stop();
    }

    @Override
    public void render(float delta) {
        ArrayList<Entity> entities = entityManager.getEntities();
        timer.updateAndRender();

        for (Entity entity : entities) {
            entity.draw(batch);
            entity.move(entities);
            if (entity.isReachEnd(entityManager.getEntities(GameEntityType.TARGET))) {
                System.err.println("Reached the end");
                sceneManager.setScene(GameSceneType.GAME_OVER_WIN);
            } else if (timer.getRemainingTime() <= 0) {
                sceneManager.setScene(GameSceneType.GAME_OVER_LOSE);
            }

            else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) { // Temporary placeholder to test GAME OVER
                                                                  // LOSE scene
                System.err.println("YOU LOST!");
                sceneManager.setScene(GameSceneType.GAME_OVER_LOSE);
            }

        }

    }

}
