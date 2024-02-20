package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.engine.scene.SceneManager;
import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.Utils;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.GameSceneType;
import com.mygdx.engine.io.SoundEffects;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameSceneType;

public class Settings extends Scene {

    private Texture background;
    private Texture backButton;
    private SceneManager sceneManager;
    private SoundEffects sound;

    private BitmapFont font;

    public Settings(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.sound = GameSceneType.SETTINGS.getSound();
        font = new BitmapFont();
    }

    @Override
    public void show() {
        background = new Texture(Utils.getInternalFilePath(Assets.MAIN_MENU_BG.getFileName()));
        backButton = new Texture(Utils.getInternalFilePath(Assets.BACK_BTN.getFileName()));

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                int worldX = screenX;
                int worldY = Gdx.graphics.getHeight() - screenY;

                if (worldX >= 50 && worldX <= 50 + backButton.getWidth() &&
                        worldY >= Gdx.graphics.getHeight() - backButton.getHeight() - 50 &&
                        worldY <= Gdx.graphics.getHeight() - 50) {

                    // Go back to the main menu
                    sceneManager.setScene(GameSceneType.MAIN_MENU);
                } else if (worldX >= 200 && worldX <= 400 &&
                        worldY >= 300 && worldY <= 350) {

                    // Toggle music on/off
                    GameConfig.isMusicEnabled = !GameConfig.isMusicEnabled;

                    // Play or stop music based on the toggle
                    if (GameConfig.isMusicEnabled) {
                        sound.play(1.0f);
                    } else {
                        sound.stop();
                    }
                }
                return super.touchUp(screenX, screenY, pointer, button);
            }
        });

        sound.play(GameConfig.MUSIC_VOLUME);
    }

    @Override
    public void hide() {
        sound.stop();
        // Add logic to hide the scene
    }

    @Override
    public void render(float delta) {

        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(backButton, 50, Gdx.graphics.getHeight() - backButton.getHeight() - 50);

        // Draw sound label
        font.draw(batch, "Sound " + (GameConfig.isMusicEnabled ? "On" : "Off"), 200, 400);

        // Draw toggle button
        batch.draw(
                new Texture(Utils.getInternalFilePath(
                        GameConfig.isMusicEnabled ? Assets.SOUND_ON_BTN.getFileName() : Assets.SOUND_OFF_BTN.getFileName())),
                200, 300, 200, 50);
    }

    @Override
    public void dispose() {
        background.dispose();
        backButton.dispose();
        font.dispose();
    }
}
