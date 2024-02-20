package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.engine.Utils;
import com.mygdx.engine.io.SoundEffects;
import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.scene.SceneManager;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameSceneType;

public class GameOverWin extends Scene {
    // this class will load all the required entityies using entity manager
    SceneManager sceneManager;
    private Texture background;
    private SoundEffects sound;

    public GameOverWin(SceneManager sceneManager) {
        super();
        this.sceneManager = sceneManager;
        this.sound = GameSceneType.GAME_OVER_WIN.getSound();
    }

    @Override
    public void show() {
        System.out.println("YOU WIN!");
        background = new Texture(Utils.getInternalFilePath(Assets.GAME_OVER_WIN.getFileName()));
        // throw new UnsupportedOperationException("Unimplemented method 'show'");
        sound.play(1.0f);
        if (!GameConfig.isMusicEnabled) {
            sound.stop();
        }        
    }

    @Override
    public void hide() {
        sound.stop();
        // throw new UnsupportedOperationException("Unimplemented method 'hide'");
    }
    @Override
    public void render(float delta) {
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
