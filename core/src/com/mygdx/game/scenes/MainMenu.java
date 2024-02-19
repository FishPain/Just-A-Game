package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;

import com.mygdx.engine.scene.SceneManager;
import com.mygdx.engine.scene.Scene;

import com.mygdx.game.GameConfig.GameSceneType;

public class MainMenu extends Scene {

    private Texture background;
    private Texture playButton;
    private Texture settingButton;
    private SceneManager sceneManager;

    public MainMenu(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @Override
    public void show() {
        background = new Texture("assets/main_menu_background.jpg");
        playButton = new Texture("assets/play_button.jpg");
        settingButton = new Texture("assets/settings_button.jpg");

        final float buttonSpacing = 20;

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                int worldX = screenX;
                int worldY = Gdx.graphics.getHeight() - screenY;

                if (worldX >= Gdx.graphics.getWidth() / 2 - playButton.getWidth() - buttonSpacing / 2 &&
                        worldX <= Gdx.graphics.getWidth() / 2 + playButton.getWidth() / 2 &&
                        worldY >= Gdx.graphics.getHeight() / 2 - playButton.getHeight() / 2 &&
                        worldY <= Gdx.graphics.getHeight() / 2 + playButton.getHeight() / 2) {

                    sceneManager.setScene(GameSceneType.GAME_SCENE);
                }
                else if (worldX >= Gdx.graphics.getWidth() / 2 + playButton.getWidth() + buttonSpacing / 2 &&
                worldX <= Gdx.graphics.getWidth() / 2 + playButton.getWidth() / 2 + settingButton.getWidth() &&
                worldY >= Gdx.graphics.getHeight() / 2 - settingButton.getHeight() / 2 &&
                worldY <= Gdx.graphics.getHeight() / 2 + settingButton.getHeight() / 2) {

                 // Add logic to handle settings button click
                }

                return super.touchUp(screenX, screenY, pointer, button);
            }
        });
    }

    @Override
    public void hide() {
        // Add logic to hide the scene
    }

    @Override
    public void render(float delta) {

        float buttonSpacing = 20;

        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(playButton, Gdx.graphics.getWidth() / 2 - playButton.getWidth() - buttonSpacing / 2,
                Gdx.graphics.getHeight() / 2 - playButton.getHeight() / 2);
        batch.draw(settingButton,Gdx.graphics.getWidth() / 2 + playButton.getWidth() + buttonSpacing / 2,
                Gdx.graphics.getHeight() / 2 - settingButton.getHeight() / 2);
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
        settingButton.dispose();
    }

    public Texture getPlayButton() {
        return playButton;
    }
    
    public Texture getSettingButton() {
        return settingButton;
    }
}
