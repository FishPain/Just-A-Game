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
    private SceneManager sceneManager;

    public MainMenu(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @Override
    public void show() {
        background = new Texture("main_menu_background.png");
        playButton = new Texture("play_button.png");

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                int worldX = screenX;
                int worldY = Gdx.graphics.getHeight() - screenY;

                if (worldX >= Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2 &&
                        worldX <= Gdx.graphics.getWidth() / 2 + playButton.getWidth() / 2 &&
                        worldY >= Gdx.graphics.getHeight() / 2 - playButton.getHeight() / 2 &&
                        worldY <= Gdx.graphics.getHeight() / 2 + playButton.getHeight() / 2) {

                    sceneManager.setScene(GameSceneType.GAME_SCENE);
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

        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(playButton, Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - playButton.getHeight() / 2);
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
    }

    public Texture getPlayButton() {
        return playButton;
    }
}
