package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;

import com.mygdx.engine.scene.SceneManager;
import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.Utils;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameSceneType;

public class Tutorial extends Scene {

    private Texture background;
    private SceneManager sceneManager;
    boolean sceneActive = true;

    public Tutorial(SceneManager sceneManager) {
        super(Assets.TUTORIAL_BG.getFileName(), Assets.TUTORIAL_SOUND.getFileName());
        this.sceneManager = sceneManager;
    }

    @Override
    public void show() {
        background = new Texture(Utils.getInternalFilePath(Assets.TUTORIAL_BG.getFileName()));

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyUp(int keycode) {
                if (!sceneActive) {
                    return false;
                }
                if (keycode == Input.Keys.ESCAPE) {
                    sceneManager.setScene(GameSceneType.MAIN_MENU);
                    sceneActive = false;
                } else if (keycode != Input.Keys.ESCAPE) {
                    sceneManager.setScene(GameSceneType.GAME_SCENE);
                    sceneActive = false;
                }
                return true;
            }
        });

        playBackgroundMusic(GameConfig.MUSIC_VOLUME);
        if (!GameConfig.isMusicEnabled) {
            stopBackgroundMusic();
        }
    }

    @Override
    public void hide() {
        if (GameConfig.isMusicEnabled) {
            stopBackgroundMusic();
        }
    }

    @Override
    public void render(float delta) {
        sceneActive = true;
        renderBackground(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Placeholder for printing things
        // font.draw(batch, "<print things here>", Gdx.graphics.getWidth() / 2,
        // Gdx.graphics.getHeight() / 2);

    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
