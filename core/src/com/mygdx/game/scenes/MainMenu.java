package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;

import com.mygdx.engine.scene.SceneManager;
import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.Utils;
import com.mygdx.engine.io.SoundEffects;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameSceneType;

public class MainMenu extends Scene {

    private Texture background;
    private Texture playButton;
    private Texture settingButton;
    private SceneManager sceneManager;
    private SoundEffects sound;
    boolean sceneActive = true;
    protected int Xscale = Gdx.graphics.getWidth() / 16;
    protected int Yscale = Gdx.graphics.getHeight() / 9;

    public MainMenu(SceneManager sceneManager) {
        super(Assets.MAIN_MENU_BG.getFileName());
        this.sceneManager = sceneManager;
        this.sound = GameSceneType.MAIN_MENU.getSound();
    }

    public Texture getPlayButton() {
        return playButton;
    }

    public Texture getSettingButton() {
        return settingButton;
    }

    @Override
    public void show() {
        background = new Texture(Utils.getInternalFilePath(Assets.MAIN_MENU_BG.getFileName()));
        playButton = new Texture(Utils.getInternalFilePath(Assets.PLAY_BTN.getFileName()));
        settingButton = new Texture(Utils.getInternalFilePath(Assets.SETTINGS_BTN.getFileName()));

        final float buttonSpacing = 20;
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override

            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (!sceneActive) {
                    return false;
                }
                int worldX = screenX;
                int worldY = Gdx.graphics.getHeight() - screenY;

                if (worldX >= Gdx.graphics.getWidth() / 2 - (Xscale) - buttonSpacing / 2 &&
                        worldX <= Gdx.graphics.getWidth() / 2 + (Xscale) / 2 &&
                        worldY >= Gdx.graphics.getHeight() / 2 - (Yscale / 2) &&
                        worldY <= Gdx.graphics.getHeight() / 2 + (Yscale)) {
                    sceneManager.setScene(GameSceneType.TUTORIAL);

                    sceneActive = false;
                    System.out.println("sceneActive: " + sceneActive);
                } else if (worldX >= Gdx.graphics.getWidth() / 2 + (Xscale) + buttonSpacing / 2 &&
                        worldX <= Gdx.graphics.getWidth() / 2 + (Xscale)
                                + Xscale
                        &&
                        worldY >= Gdx.graphics.getHeight() / 2 - (Yscale / 2) &&
                        worldY <= Gdx.graphics.getHeight() / 2 + (Yscale)) {

                    sceneManager.setScene(GameSceneType.SETTINGS);
                    sceneActive = false;
                }
                return super.touchUp(screenX, screenY, pointer, button);
            }
        });

        sound.play(GameConfig.MUSIC_VOLUME);
        if (!GameConfig.isMusicEnabled){
            sound.stop();
        }
    }

    @Override
    public void hide() {
        sound.stop();
    }

    @Override
    public void render(float delta) {
        sceneActive = true;
        float buttonSpacing = 20;
        renderBackground(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(playButton, Gdx.graphics.getWidth() / 2 - Xscale - buttonSpacing / 2,
                Gdx.graphics.getHeight() / 2 - playButton.getHeight() / 2, Xscale, Yscale);
        batch.draw(settingButton, Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 16 + buttonSpacing / 2,
                Gdx.graphics.getHeight() / 2 - settingButton.getHeight() / 2, Xscale, Yscale);
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
        settingButton.dispose();
    }
}
