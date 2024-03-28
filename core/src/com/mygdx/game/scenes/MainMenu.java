package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.mygdx.engine.scene.SceneManager;
import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.io.button.Button;
import com.mygdx.engine.io.button.ButtonClickListener;
import com.mygdx.engine.io.button.ButtonManager;
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
        super(Assets.MAIN_MENU_BG.getFileName(),
                Assets.MAIN_MENU_SOUND.getFileName(),
                GameSceneType.MAIN_MENU.getValue());

        this.sceneManager = sceneManager;
    }

    public Texture getLogoTexture() {
        return logoTexture;
    }

    private ButtonClickListener clickListener = new ButtonClickListener() {
        @Override
        public void onClick(Button button) {
            GameButtonType btnType = GameButtonType.fromValue(button.getButtonType());
            if (btnType.equals(GameButtonType.PLAY)) {
                sceneManager.setScene(GameSceneType.TUTORIAL.getValue());
            } else if (btnType.equals(GameButtonType.SETTINGS)) {
                sceneManager.setScene(GameSceneType.SETTINGS.getValue());
            } else if (btnType.equals(GameButtonType.QUIT)) {
                Gdx.app.exit();
            }
        }
    };

    @Override
    public void show() {
        float buttonSpacing = 50;
        float buttonWidth = GameConfig.BUTTON_WIDTH;
        float buttonHeight = GameConfig.BUTTON_HEIGHT;
        float totalButtonWidth = 3 * buttonWidth + 2 * buttonSpacing;
        float startX = (GameConfig.SCREEN_WIDTH - totalButtonWidth) / 2;
        float offsetBelowCenter = GameConfig.SCREEN_HEIGHT * 0.2f;
        float buttonY = (GameConfig.SCREEN_HEIGHT / 2 - buttonHeight / 2) - offsetBelowCenter; 
        float playButtonX = startX;
        float settingButtonX = startX + buttonWidth + buttonSpacing;
        float quitButtonX = settingButtonX + buttonWidth + buttonSpacing;

        // Create buttons
        tutorialBtn = new Button(playButtonX, buttonY, buttonWidth, buttonHeight, GameButtonType.PLAY.getValue(),
                Assets.BUTTON_BG.getFileName(), GameConfig.GameButtonText.PLAY_BTN.getText(),
                GameConfig.Assets.FONT_PATH.getFileName(), GameConfig.BUTTON_FONT_SIZE);

        settingsBtn = new Button(settingButtonX, buttonY, buttonWidth, buttonHeight,
                GameButtonType.SETTINGS.getValue(), Assets.BUTTON_BG.getFileName(),
                GameConfig.GameButtonText.SETTINGS_BTN.getText(), GameConfig.Assets.FONT_PATH.getFileName(),
                GameConfig.BUTTON_FONT_SIZE);

        quitBtn = new Button(quitButtonX, buttonY, buttonWidth,
                buttonHeight, GameButtonType.QUIT.getValue(), Assets.BUTTON_BG.getFileName(),
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
        super.dispose();
        if (buttonManager != null)
            buttonManager.dispose();
        if (background != null)
            background.dispose();
        if (logoTexture != null)
            logoTexture.dispose();
    }
}
