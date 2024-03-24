package com.mygdx.game.scenes;

import com.mygdx.engine.scene.SceneManager;
import com.mygdx.engine.scene.Scene;

import com.mygdx.engine.io.button.Button;
import com.mygdx.engine.io.button.ToggleButton;
import com.mygdx.engine.io.button.ButtonClickListener;
import com.mygdx.engine.io.button.ButtonManager;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.GameSceneType;
import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameButtonType;

public class Settings extends Scene {
    private SceneManager sceneManager;
    private ButtonManager buttonManager;
    private ToggleButton soundBtn;
    private Button backBtn;

    public Settings(SceneManager sceneManager) {
        super(Assets.MAIN_MENU_BG.getFileName(), Assets.MAIN_MENU_SOUND.getFileName());
        this.sceneManager = sceneManager;

        float buttonSpacing = 50;
        float buttonWidth = GameConfig.BUTTON_WIDTH;
        float buttonHeight = GameConfig.BUTTON_HEIGHT;
        float totalButtonHeight = 2 * buttonHeight + buttonSpacing;

        float X = (GameConfig.SCREEN_WIDTH - buttonWidth) / 2;
        float backButtonY = (GameConfig.SCREEN_HEIGHT - totalButtonHeight) / 2;
        float soundButtonY = backButtonY + buttonHeight + buttonSpacing;

        this.soundBtn = createSoundButton(X, soundButtonY);
        this.backBtn = createBackButton(X, backButtonY);
    }

    private ButtonClickListener clickListener = new ButtonClickListener() {
        @Override
        public void onClick(Button button) {
            GameButtonType btnType = GameButtonType.fromValue(button.getButtonType());

            if (btnType.equals(GameButtonType.SOUND_TOGGLE)) {
                GameConfig.IS_MUSIC_ENABLED = !GameConfig.IS_MUSIC_ENABLED;
                if (GameConfig.IS_MUSIC_ENABLED) {
                    playBackgroundMusic(GameConfig.MUSIC_VOLUME);
                } else {
                    stopBackgroundMusic();
                }
                soundBtn.toggleTexture();
            } else if (btnType.equals(GameButtonType.BACK)) {
                sceneManager.setScene(GameSceneType.MAIN_MENU);
            }
        }
    };

    @Override
    public void show() {
        // Register the buttons
        buttonManager = new ButtonManager(clickListener);
        buttonManager.addButton(soundBtn);
        buttonManager.addButton(backBtn);

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
        buttonManager.dispose();
    }

    private ToggleButton createSoundButton(float X, float Y) {
        float buttonWidth = GameConfig.BUTTON_WIDTH;
        float buttonHeight = GameConfig.BUTTON_HEIGHT;

        // Create the sound button
        return new ToggleButton(X, Y, buttonWidth, buttonHeight,
                GameButtonType.SOUND_TOGGLE.getValue(),
                new String[] { Assets.SOUND_ON_BTN.getFileName(), Assets.SOUND_OFF_BTN.getFileName() },
                "", GameConfig.Assets.FONT_PATH.getFileName(),
                GameConfig.BUTTON_FONT_SIZE);
    }

    private Button createBackButton(float X, float Y) {
        float buttonWidth = GameConfig.BUTTON_WIDTH;
        float buttonHeight = GameConfig.BUTTON_HEIGHT;

        // Create the back button
        return new Button(X, Y, buttonWidth, buttonHeight,
                GameButtonType.BACK.getValue(), Assets.BUTTON_BG.getFileName(),
                GameConfig.GameButtonText.BACK_BTN.getText(), GameConfig.Assets.FONT_PATH.getFileName(),
                GameConfig.BUTTON_FONT_SIZE);
    }
}
