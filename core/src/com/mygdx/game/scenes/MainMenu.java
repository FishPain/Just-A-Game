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

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (!sceneActive) {
                    return false;
                }
                int worldX = screenX;
                int worldY = GameConfig.SCREEN_HEIGHT - screenY;

                float buttonWidth = Gdx.graphics.getWidth() / 16;
                float buttonHeight = GameConfig.SCREEN_HEIGHT / 9;
                float buttonSpacing = 20;
                float startX = (Gdx.graphics.getWidth() - 2 * buttonWidth - buttonSpacing) / 2;

                // Check if the touch event is within the play button's area
                if (worldX >= startX && worldX <= startX + buttonWidth &&
                        worldY >= GameConfig.SCREEN_HEIGHT / 2 - buttonHeight / 2 &&
                        worldY <= GameConfig.SCREEN_HEIGHT / 2 + buttonHeight / 2) {

                    sceneManager.setScene(GameSceneType.GAME_SCENE);
                    sceneActive = false;
                }
                // Check if the touch event is within the setting button's area
                else if (worldX >= startX + buttonWidth + buttonSpacing &&
                        worldX <= startX + 2 * buttonWidth + buttonSpacing &&
                        worldY >= GameConfig.SCREEN_HEIGHT / 2 - buttonHeight / 2 &&
                        worldY <= GameConfig.SCREEN_HEIGHT / 2 + buttonHeight / 2) {

                    sceneManager.setScene(GameSceneType.SETTINGS);
                    sceneActive = false;
                }
                return super.touchUp(screenX, screenY, pointer, button);
            }
        });

        sound.play(GameConfig.MUSIC_VOLUME);
        if (!GameConfig.isMusicEnabled) {
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
        float buttonSpacing = 50;
        int Xscale = GameConfig.SCREEN_WIDTH / 16;
        int Yscale = GameConfig.SCREEN_HEIGHT / 9;
        renderBackground(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);

        // Calculate button positions based on screen size percentages
        float buttonWidth = Xscale;
        float buttonHeight = Yscale;
        float totalButtonWidth = 2 * buttonWidth + buttonSpacing;
        float startX = (GameConfig.SCREEN_WIDTH - totalButtonWidth) / 2;
        float playButtonX = startX;
        float playButtonY = GameConfig.SCREEN_HEIGHT / 2 - buttonHeight / 2;
        float settingButtonX = startX + buttonWidth + buttonSpacing;
        float settingButtonY = GameConfig.SCREEN_HEIGHT / 2 - buttonHeight / 2;

        batch.draw(playButton, playButtonX, playButtonY, buttonWidth, buttonHeight);
        batch.draw(settingButton, settingButtonX, settingButtonY, buttonWidth, buttonHeight);
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
        settingButton.dispose();
    }
}
