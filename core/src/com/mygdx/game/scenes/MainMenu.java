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
    protected int Xscale = GameConfig.SCREEN_WIDTH / 16;
    protected int Yscale = GameConfig.SCREEN_HEIGHT / 9;
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
        float buttonWidth = Xscale;
        float buttonHeight = Yscale;
        float totalButtonWidth = 3 * buttonWidth + 2 * buttonSpacing;
        float startX = (GameConfig.SCREEN_WIDTH - totalButtonWidth) / 2;
        float buttonY = GameConfig.SCREEN_HEIGHT / 2 - buttonHeight / 2;

        Button[] buttons = new Button[3];
        GameButtonType[] buttonTypes = { GameButtonType.PLAY, GameButtonType.SETTINGS, GameButtonType.QUIT };
        String[] buttonAssets = { Assets.PLAY_BTN.getFileName(), Assets.SETTINGS_BTN.getFileName(),
                Assets.QUIT_BTN.getFileName() };

        for (int i = 0; i < buttons.length; i++) {
            float buttonX = startX + i * (buttonWidth + buttonSpacing);
            buttons[i] = new Button(buttonX, buttonY, buttonWidth, buttonHeight, buttonTypes[i], buttonAssets[i]);
        }

        tutorialBtn = buttons[0];
        settingsBtn = buttons[1];
        quitBtn = buttons[2];

        // Register the buttons
        buttonManager = new ButtonManager(clickListener);
        buttonManager.addButton(tutorialBtn);
        buttonManager.addButton(settingsBtn);
        buttonManager.addButton(quitBtn);

        // Set input processor for buttons
        buttonManager.setButtonsInputProcessor();

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
        renderBackground(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        // float buttonSpacing = 50;
        // int Xscale = GameConfig.SCREEN_WIDTH / 16;
        // int Yscale = GameConfig.SCREEN_HEIGHT / 9;

        // Calculate button positions based on screen size percentages

        // float buttonWidth = Xscale;
        // float buttonHeight = Yscale;
        // float logoWidth = 420;
        // float logoHeight = 240;
        // float totalButtonWidth = 3 * buttonWidth + 2 * buttonSpacing; // Update
        // totalButtonWidth for three buttons
        // float startX = (GameConfig.SCREEN_WIDTH - totalButtonWidth) / 2; // Calculate
        // startX for center alignment
        // float buttonY = GameConfig.SCREEN_HEIGHT / 2 - buttonHeight / 2; // Common Y
        // for buttons
        // float playButtonX = startX;
        // float settingButtonX = startX + buttonWidth + buttonSpacing;
        // float quitButtonX = settingButtonX + buttonWidth + buttonSpacing;
        // float logoX = (GameConfig.SCREEN_WIDTH - logoWidth) / 2;
        // float logoY = GameConfig.SCREEN_HEIGHT / 3 - logoHeight / 2;

        // Draw buttons
        buttonManager.drawButtons(batch);

        // batch.draw(logoTexture, logoX, logoY, logoWidth, logoHeight);
    }

    @Override
    public void dispose() {
        background.dispose();
        logoTexture.dispose();
        buttonManager.dispose();
    }
}
