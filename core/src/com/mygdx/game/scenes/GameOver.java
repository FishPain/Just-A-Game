package com.mygdx.game.scenes;

import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.scene.SceneManager;

public class GameOver extends Scene {
    // this class will load all the required entityies using entity manager
    SceneManager sceneManager;

    public GameOver(SceneManager sceneManager) {
        super();
        this.sceneManager = sceneManager;
    }

    @Override
    public void show() {
        // throw new UnsupportedOperationException("Unimplemented method 'show'");
    }

    @Override
    public void hide() {
        // throw new UnsupportedOperationException("Unimplemented method 'hide'");
    }

}
