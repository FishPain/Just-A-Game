package com.mygdx.engine.scene;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Scene extends ScreenAdapter {

    protected SpriteBatch batch;

    public Scene() {
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public abstract void show();

    public abstract void hide();

    public abstract void render(float delta);

}
