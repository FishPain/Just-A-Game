package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.engine.Utils;
import com.mygdx.engine.entity.EntityManager;
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

    public GameOverWin(SceneManager sceneManager, EntityManager entityManager) {
        super(Assets.GAME_OVER_WIN.getFileName(), Assets.GAME_OVER_WIN_SOUND.getFileName());
        this.sceneManager = sceneManager;
    }

    private ButtonClickListener clickListener = new ButtonClickListener() {
        @Override
        public void onClick(Button button) {
            GameButtonType btnType = GameButtonType.fromValue(button.getButtonType());
            if (btnType.equals(GameButtonType.START)) {
                sceneManager.setScene(GameSceneType.GAME_SCENE_LVL1);
            } else if (btnType.equals(GameButtonType.BACK)) {
                sceneManager.setScene(GameSceneType.MAIN_MENU);
            }
        }
    };

    @Override
    public void show() {
        float buttonWidth = GameConfig.BUTTON_WIDTH;
        float buttonHeight = GameConfig.BUTTON_HEIGHT;
        float buttonSpacing = 50;

        // Calculate the total width occupied by the buttons
        float totalButtonWidth = 2 * buttonWidth + buttonSpacing;

        // Calculate the starting position for the buttons to center them horizontally
        float startX = (GameConfig.SCREEN_WIDTH - totalButtonWidth) / 2;

        // Calculate the starting position for the buttons to place it at the bottom
        // center vertically
        float startY = 50; // Adjust this value to position the button higher or lower

        // Calculate the starting position for the back button
        float backButtonX = startX + buttonWidth + buttonSpacing;

        // Create the back button
        Button backBtn = new Button(backButtonX, startY, buttonWidth, buttonHeight,
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
