package com.mygdx.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
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

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // Add common rendering logic for scenes
        batch.end();
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }
    }
}
