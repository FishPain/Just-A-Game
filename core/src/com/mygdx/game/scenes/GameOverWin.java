package com.mygdx.game.scenes;

import com.mygdx.engine.io.button.Button;
import com.mygdx.engine.io.button.ButtonClickListener;
import com.mygdx.engine.io.button.ButtonManager;
import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.scene.SceneManager;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameButtonType;
import com.mygdx.game.GameConfig.GameSceneType;

public class GameOverWin extends Scene {
    // this class will load all the required entityies using entity manager
    SceneManager sceneManager;
    private ButtonManager buttonManager;

    public GameOverWin(SceneManager sceneManager) {
        super(Assets.GAME_OVER_WIN.getFileName(), Assets.GAME_OVER_WIN_SOUND.getFileName());
        this.sceneManager = sceneManager;
    }

    private ButtonClickListener clickListener = new ButtonClickListener() {
        @Override
        public void onClick(Button button) {
            GameButtonType btnType = GameButtonType.fromValue(button.getButtonType());
            if (btnType.equals(GameButtonType.BACK)) {
                sceneManager.setScene(GameSceneType.MAIN_MENU);
            }
        }
    };

    @Override
    public void show() {
        float buttonWidth = GameConfig.BUTTON_WIDTH;
        float buttonHeight = GameConfig.BUTTON_HEIGHT;

        float backButtonX = (GameConfig.SCREEN_WIDTH - buttonWidth) / 2;
        float backButtonY = 50;

        // Create the back button
        Button backBtn = new Button(backButtonX, backButtonY, buttonWidth, buttonHeight,
                GameButtonType.BACK.getValue(), Assets.BUTTON_BG.getFileName(),
                GameConfig.GameButtonText.BACK_BTN.getText(), GameConfig.Assets.FONT_PATH.getFileName(),
                GameConfig.BUTTON_FONT_SIZE);

        // Register the buttons
        buttonManager = new ButtonManager(clickListener);
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
}
