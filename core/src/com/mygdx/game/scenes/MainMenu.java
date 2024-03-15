package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;

import com.mygdx.engine.scene.SceneManager;
import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.Utils;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameSceneType;

public class MainMenu extends Scene {

    private Texture background;
    private Texture playButton;
    private Texture settingButton;
    private Texture logoTexture;
    private SceneManager sceneManager;
    boolean sceneActive = true;
    protected int Xscale = GameConfig.SCREEN_WIDTH / 16;
    protected int Yscale = GameConfig.SCREEN_HEIGHT / 9;

    public MainMenu(SceneManager sceneManager) {
        super(Assets.MAIN_MENU_BG.getFileName(), Assets.MAIN_MENU_SOUND.getFileName());
        this.sceneManager = sceneManager;
    }

    public Texture getPlayButton() {
        return playButton;
    }

    public Texture getSettingButton() {
        return settingButton;
    }

    public Texture getLogoTexture() {
        return logoTexture;
    }

    @Override
    public void show() {
        background = new Texture(Utils.getInternalFilePath(Assets.MAIN_MENU_BG.getFileName()));
        playButton = new Texture(Utils.getInternalFilePath(Assets.PLAY_BTN.getFileName()));
        settingButton = new Texture(Utils.getInternalFilePath(Assets.SETTINGS_BTN.getFileName()));
        logoTexture = new Texture(Utils.getInternalFilePath(Assets.LOGO.getFileName()));

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override

            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (!sceneActive) {
                    return false;
                }
                int worldX = screenX;
                int worldY = GameConfig.SCREEN_HEIGHT - screenY;

                float buttonWidth = GameConfig.SCREEN_WIDTH / 16;
                float buttonHeight = GameConfig.SCREEN_HEIGHT / 9;
                float buttonSpacing = 20;
                float startX = (GameConfig.SCREEN_WIDTH - 2 * buttonWidth - buttonSpacing) / 2;

                // Check if the touch event is within the play button's area
                if (worldX >= startX && worldX <= startX + buttonWidth &&
                        worldY >= GameConfig.SCREEN_HEIGHT / 2 - buttonHeight / 2 &&
                        worldY <= GameConfig.SCREEN_HEIGHT / 2 + buttonHeight / 2) {

                    sceneManager.setScene(GameSceneType.TUTORIAL);
                    sceneActive = false;
                }
                // Check if the touch event is within the setting button's area
                else if (worldX >= startX + buttonWidth + buttonSpacing &&
                        worldX <= startX + 2 * buttonWidth + buttonSpacing &&
                        worldY >= GameConfig.SCREEN_HEIGHT / 2 - buttonHeight / 2 &&
                        worldY <= GameConfig.SCREEN_HEIGHT / 2 + buttonHeight / 2) {

                    sceneManager.setScene(GameSceneType.SETTINGS);
                    sceneActive = false;
                } else if (worldX >= startX + 2 * buttonWidth + 2 * buttonSpacing && // Check if the touch event is
                                                                                     // within the quit button's area
                        worldX <= startX + 3 * buttonWidth + 2 * buttonSpacing &&
                        worldY >= GameConfig.SCREEN_HEIGHT / 2 - buttonHeight / 2 &&
                        worldY <= GameConfig.SCREEN_HEIGHT / 2 + buttonHeight / 2) {

                    Gdx.app.exit(); // Exit the application
                }

                return super.touchUp(screenX, screenY, pointer, button);
            }
        });

        playBackgroundMusic(GameConfig.MUSIC_VOLUME);
        if (!GameConfig.isMusicEnabled) {
            stopBackgroundMusic();
        }
    }

    @Override
    public void hide() {
        if (GameConfig.isMusicEnabled) {
            stopBackgroundMusic();
        }
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
        float logoWidth = 420;
        float logoHeight = 240;
        float totalButtonWidth = 3 * buttonWidth + 2 * buttonSpacing; // Update totalButtonWidth for three buttons
        float startX = (GameConfig.SCREEN_WIDTH - totalButtonWidth) / 2; // Calculate startX for center alignment
        float buttonY = GameConfig.SCREEN_HEIGHT / 2 - buttonHeight / 2; // Common Y for buttons
        float playButtonX = startX;
        float settingButtonX = startX + buttonWidth + buttonSpacing;
        float quitButtonX = settingButtonX + buttonWidth + buttonSpacing;
        float logoX = (GameConfig.SCREEN_WIDTH - logoWidth) / 2;
        float logoY = GameConfig.SCREEN_HEIGHT / 3 - logoHeight / 2;

        batch.draw(playButton, playButtonX, buttonY, buttonWidth, buttonHeight);
        batch.draw(settingButton, settingButtonX, buttonY, buttonWidth, buttonHeight);
        batch.draw(playButton, quitButtonX, buttonY, buttonWidth, buttonHeight);
        batch.draw(logoTexture, logoX, logoY, logoWidth, logoHeight);

    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
        settingButton.dispose();
        logoTexture.dispose();
    }
}
