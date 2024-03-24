package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.mygdx.engine.scene.SceneManager;
import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.Utils;
import com.mygdx.engine.io.Button;
import com.mygdx.engine.io.ButtonClickListener;
import com.mygdx.engine.io.ButtonManager;
import com.mygdx.engine.io.ButtonType;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameSceneType;
import com.mygdx.game.GameConfig.GameButtonType;

public class MainMenu extends Scene {

    private Texture background;
    private Texture logoTexture;
    private SceneManager sceneManager;
    private Button tutorialBtn;
    private Button settingsBtn;
    private Button quitBtn;
    private ButtonManager buttonManager;

    public MainMenu(SceneManager sceneManager) {
        super(Assets.MAIN_MENU_BG.getFileName(), Assets.MAIN_MENU_SOUND.getFileName());
        this.sceneManager = sceneManager;
    }

    public Texture getLogoTexture() {
        return logoTexture;
    }

    private ButtonClickListener clickListener = new ButtonClickListener() {
        @Override
        public void onClick(ButtonType btnType) {
            if (btnType.equals(GameButtonType.PLAY)) {
                sceneManager.setScene(GameSceneType.TUTORIAL);
            } else if (btnType.equals(GameButtonType.SETTINGS)) {
                sceneManager.setScene(GameSceneType.SETTINGS);
            } else if (btnType.equals(GameButtonType.QUIT)) {
                Gdx.app.exit();
            }
        }
    };

    @Override
    public void show() {
        background = new Texture(Utils.getInternalFilePath(Assets.MAIN_MENU_BG.getFileName()));
        logoTexture = new Texture(Utils.getInternalFilePath(Assets.LOGO.getFileName()));

        float buttonSpacing = 50;
        float buttonWidth = GameConfig.BUTTON_WIDTH;
        float buttonHeight = GameConfig.BUTTON_HEIGHT;
        float totalButtonWidth = 3 * buttonWidth + 2 * buttonSpacing;
        float startX = (GameConfig.SCREEN_WIDTH - totalButtonWidth) / 2;
        float buttonY = GameConfig.SCREEN_HEIGHT / 2 - buttonHeight / 2;
        float playButtonX = startX;
        float settingButtonX = startX + buttonWidth + buttonSpacing;
        float quitButtonX = settingButtonX + buttonWidth + buttonSpacing;

        // Create buttons
        tutorialBtn = new Button(playButtonX, buttonY, buttonWidth, buttonHeight, GameButtonType.PLAY,
                Assets.BUTTON_BG.getFileName(), GameConfig.GameButtonText.PLAY_BTN.getText(),
                GameConfig.Assets.FONT_PATH.getFileName(), GameConfig.BUTTON_FONT_SIZE);

        settingsBtn = new Button(settingButtonX, buttonY, buttonWidth, buttonHeight,
                GameButtonType.SETTINGS, Assets.BUTTON_BG.getFileName(),
                GameConfig.GameButtonText.SETTINGS_BTN.getText(), GameConfig.Assets.FONT_PATH.getFileName(),
                GameConfig.BUTTON_FONT_SIZE);

        quitBtn = new Button(quitButtonX, buttonY, buttonWidth,
                buttonHeight, GameButtonType.QUIT, Assets.BUTTON_BG.getFileName(),
                GameConfig.GameButtonText.QUIT_BTN.getText(), GameConfig.Assets.FONT_PATH.getFileName(),
                GameConfig.BUTTON_FONT_SIZE);

        // Register the buttons
        buttonManager = new ButtonManager(clickListener);
        buttonManager.addButton(tutorialBtn);
        buttonManager.addButton(settingsBtn);
        buttonManager.addButton(quitBtn);

        // Set input processor for buttons
        buttonManager.setButtonsInputProcessor();

        playBackgroundMusic(GameConfig.MUSIC_VOLUME);
        if (!GameConfig.IS_MUSIC_ENABLED) {
            stopBackgroundMusic();
        }
    }

    @Override
    public void hide() {
        if (GameConfig.IS_MUSIC_ENABLED) {
            stopBackgroundMusic();
        }
    }

    @Override
    public void render(float delta) {
        renderBackground(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        buttonManager.drawButtons(batch);
    }

    @Override
    public void dispose() {
        background.dispose();
        logoTexture.dispose();
        buttonManager.dispose();
    }
}
