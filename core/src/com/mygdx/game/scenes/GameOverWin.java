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
        super(Assets.GAME_OVER_WIN.getFileName(),
                Assets.GAME_OVER_WIN_SOUND.getFileName(),
                GameSceneType.GAME_OVER_WIN.getValue());
        this.sceneManager = sceneManager;
    }

    private ButtonClickListener clickListener = new ButtonClickListener() {
        @Override
        public void onClick(Button button) {
            GameButtonType btnType = GameButtonType.fromValue(button.getButtonType());
            if (btnType.equals(GameButtonType.NEXT_LEVEL)) {
                sceneManager.setScene(GameSceneType.GAME_SCENE_LVL2.getValue());
            } else if (btnType.equals(GameButtonType.BACK)) {
                sceneManager.setScene(GameSceneType.MAIN_MENU.getValue());
            }
        }
    };

    @Override
    public void show() {
        buttonManager = new ButtonManager(clickListener);

        float buttonWidth = GameConfig.BUTTON_WIDTH;
        float buttonHeight = GameConfig.BUTTON_HEIGHT;
        float buttonSpacing = 50;
        int numOfBtns = 2;

        // Check if the previous scene is GAME_SCENE_LVL2
        if (sceneManager.getPreviousScene().getSceneType() == GameSceneType.GAME_SCENE_LVL2.getValue()) {
            numOfBtns = 1; // Only show the back button
            buttonSpacing = 0; // No spacing needed between buttons
        }

        float totalButtonWidth = numOfBtns * buttonWidth + (numOfBtns - 1) * buttonSpacing;

        // Calculate the starting position for the buttons to center them horizontally
        float startX = (GameConfig.SCREEN_WIDTH - totalButtonWidth) / 2;

        // Calculate the starting position for the buttons to center them vertically
        float startY = 50;

        // Create the next level game button if the previous scene is not
        // GAME_SCENE_LVL2
        if (numOfBtns == 2) {
            Button startGameBtn = new Button(startX, startY, buttonWidth, buttonHeight,
                    GameButtonType.NEXT_LEVEL.getValue(),
                    Assets.BUTTON_BG.getFileName(), GameConfig.GameButtonText.NEXT_LEVEL_BTN.getText(),
                    GameConfig.Assets.FONT_PATH.getFileName(), GameConfig.BUTTON_FONT_SIZE);
            buttonManager.addButton(startGameBtn);
            startX += buttonWidth + buttonSpacing; // Update startX for the next button
        }

        // Create the back button
        Button backBtn = new Button(startX, startY, buttonWidth, buttonHeight,
                GameButtonType.BACK.getValue(), Assets.BUTTON_BG.getFileName(),
                GameConfig.GameButtonText.BACK_BTN.getText(), GameConfig.Assets.FONT_PATH.getFileName(),
                GameConfig.BUTTON_FONT_SIZE);

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
