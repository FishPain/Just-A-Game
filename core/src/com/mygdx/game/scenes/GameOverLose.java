package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.engine.Utils;
import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.io.SoundEffects;
import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.scene.SceneManager;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameSceneType;

public class GameOverLose extends Scene {
    // this class will load all the required entityies using entity manager
    SceneManager sceneManager;
    private Texture background;
    private Texture restartButton;
    private SoundEffects sound;
    private EntityManager entityManager;

    public GameOverLose(SceneManager sceneManager, EntityManager entityManager) {
        super(Assets.GAME_OVER_LOSE.getFileName());
        this.sceneManager = sceneManager;
        this.entityManager = entityManager;
        this.sound = GameSceneType.GAME_OVER_LOSE.getSound();
    }

    @Override
    public void show() {
        background = new Texture(Utils.getInternalFilePath(Assets.GAME_OVER_LOSE.getFileName()));
        restartButton = new Texture(Utils.getInternalFilePath(Assets.RESTART_BTN.getFileName()));

        if (!GameConfig.isMusicEnabled) {
            sound.stop();
        }

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                int worldX = screenX;
                int worldY = Gdx.graphics.getHeight() - screenY;

                // Check if the restart button is pressed
                if (worldX >= Gdx.graphics.getWidth() / 2 - restartButton.getWidth() / 2 &&
                        worldX <= Gdx.graphics.getWidth() / 2 + restartButton.getWidth() / 2 &&
                        worldY >= 50 && worldY <= 50 + restartButton.getHeight()) {

                    // Go back to the main menu
                    sceneManager.setScene(GameSceneType.MAIN_MENU);
                }
                return super.touchUp(screenX, screenY, pointer, button);
            }
        });

        if (GameConfig.isMusicEnabled)
            sound.play(GameConfig.MUSIC_VOLUME);
    }

    @Override
    public void hide() {
        sound.stop();
        entityManager.dispose(batch);
    }

    @Override
    public void render(float delta) {
        renderBackground(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        batch.draw(restartButton, GameConfig.SCREEN_WIDTH / 2 - restartButton.getWidth() / 2, 50);
    }

    @Override
    public void dispose() {
        background.dispose();
        restartButton.dispose();
    }
}
