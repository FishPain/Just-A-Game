package com.mygdx.engine.scene;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.engine.Utils;

public abstract class Scene extends ScreenAdapter {

    protected SpriteBatch batch;
    private Texture background;

    public Scene(String backgroundFileName) {
        this.background = new Texture(Utils.getInternalFilePath(backgroundFileName));
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public abstract void show();

    public abstract void hide();

    public abstract void render(float delta);

    public void renderBackground(float x, float y, float width, float height) {
        batch.draw(background, x, y, width, height);
    }

}
