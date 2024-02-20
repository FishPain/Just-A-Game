package com.mygdx.game.scenes;

import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.scene.SceneManager;
import com.mygdx.game.GameConfig.GameSceneType;
import com.mygdx.engine.io.SoundEffects;

public class Settings extends Scene {
    // this class will load all the required entityies using entity manager
    private SceneManager sceneManager;
    private SoundEffects sound;

    public Settings(SceneManager sceneManager) {
        super();
        this.sceneManager = sceneManager;
        this.sound = GameSceneType.SETTINGS.getSound();
    }

    @Override
    public void show() {
        sound.play(1.0f);
        // throw new UnsupportedOperationException("Unimplemented method 'show'");
    }

    @Override
    public void hide() {
        sound.stop();
        // throw new UnsupportedOperationException("Unimplemented method 'hide'");
    }

}
