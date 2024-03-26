package com.mygdx.engine.scene;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.engine.Utils;
import com.mygdx.engine.io.SoundEffects;

public abstract class Scene extends ScreenAdapter {

    protected SpriteBatch batch;
    private Texture background;
    private SoundEffects backgroundMusic;
    private String sceneType;

    public Scene(String backgroundFileName, String backgroundMusicFileName, String sceneType) {
        this.background = new Texture(Utils.getInternalFilePath(backgroundFileName));
        this.backgroundMusic = new SoundEffects(backgroundMusicFileName);
        this.sceneType = sceneType;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public String getSceneType() {
        return sceneType;
    }

    public void setSceneType(String sceneType) {
        this.sceneType = sceneType;
    }

    public abstract void show();

    public abstract void hide();

    public abstract void render(float delta);

    public void renderBackground(float x, float y, float width, float height) {
        batch.draw(background, x, y, width, height);
    }

    public void playBackgroundMusic(float volume) {
        backgroundMusic.play(volume);
    }

    public void stopBackgroundMusic() {
        backgroundMusic.stop();
    }

    public void disposeBackgroundMusic() {
        backgroundMusic.dispose();
    }

}
