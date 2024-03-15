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
import com.mygdx.game.GameConfig.Assets;

public class Settings extends Scene {

    private Texture background;
    private Texture backButton;
    private SceneManager sceneManager;
    private BitmapFont font;

    public Settings(SceneManager sceneManager) {
        super(Assets.MAIN_MENU_BG.getFileName(), Assets.MAIN_MENU_SOUND.getFileName());
        this.sceneManager = sceneManager;
        font = new BitmapFont();
    }

    @Override
    public void show() {
        backButton = new Texture(Utils.getInternalFilePath(Assets.BACK_BTN.getFileName()));

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                int worldX = screenX;
                int worldY = GameConfig.SCREEN_HEIGHT - screenY;

                // Calculate button positions based on screen size
                float backButtonX = GameConfig.SCREEN_WIDTH * 0.05f;
                float backButtonY = GameConfig.SCREEN_HEIGHT - backButton.getHeight()
                        - (GameConfig.SCREEN_HEIGHT * 0.05f);
                float toggleButtonX = GameConfig.SCREEN_WIDTH * 0.2f;
                float toggleButtonY = GameConfig.SCREEN_HEIGHT * 0.6f;

                if (worldX >= backButtonX && worldX <= backButtonX + backButton.getWidth() &&
                        worldY >= backButtonY && worldY <= backButtonY + backButton.getHeight()) {

                    // Go back to the main menu
                    sceneManager.setScene(GameSceneType.MAIN_MENU);
                } else if (worldX >= toggleButtonX && worldX <= toggleButtonX + 200 &&
                        worldY >= toggleButtonY && worldY <= toggleButtonY + 50) {

                    // Toggle music on/off
                    GameConfig.isMusicEnabled = !GameConfig.isMusicEnabled;

                    // Play or stop music based on the toggle
                    if (GameConfig.isMusicEnabled) {
                        playBackgroundMusic(GameConfig.MUSIC_VOLUME);
                    } else {
                        stopBackgroundMusic();
                    }
                }
                return super.touchUp(screenX, screenY, pointer, button);
            }
        });
        if (GameConfig.isMusicEnabled)
            playBackgroundMusic(GameConfig.MUSIC_VOLUME);
    }

    @Override
    public void hide() {
        if (GameConfig.isMusicEnabled) {
            stopBackgroundMusic();
        }
    }

    @Override
    public void render(float delta) {
        renderBackground(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);

        // Calculate button positions based on screen size
        float backButtonX = GameConfig.SCREEN_WIDTH * 0.05f;
        float backButtonY = GameConfig.SCREEN_HEIGHT - backButton.getHeight() - (GameConfig.SCREEN_HEIGHT * 0.05f);
        float soundLabelX = GameConfig.SCREEN_WIDTH * 0.2f;
        float soundLabelY = GameConfig.SCREEN_HEIGHT * 0.7f;
        float toggleButtonX = GameConfig.SCREEN_WIDTH * 0.2f;
        float toggleButtonY = GameConfig.SCREEN_HEIGHT * 0.6f;

        // Draw buttons
        batch.draw(backButton, backButtonX, backButtonY);
        font.draw(batch, "Sound " + (GameConfig.isMusicEnabled ? "On" : "Off"), soundLabelX, soundLabelY);
        batch.draw(
                new Texture(Utils.getInternalFilePath(
                        GameConfig.isMusicEnabled ? Assets.SOUND_ON_BTN.getFileName()
                                : Assets.SOUND_OFF_BTN.getFileName())),
                toggleButtonX, toggleButtonY, 200, 50);
    }

    @Override
    public void dispose() {
        background.dispose();
        backButton.dispose();
        font.dispose();
    }
}
