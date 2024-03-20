package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.mygdx.engine.scene.SceneManager;
import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.Utils;
import com.mygdx.engine.io.Button;
import com.mygdx.engine.io.ButtonClickListener;
import com.mygdx.engine.io.ButtonManager;
import com.mygdx.engine.io.ButtonType;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameButtonType;
import com.mygdx.game.GameConfig.GameSceneType;

public class Tutorial extends Scene {
    private Texture burger;
    private Texture rain;
    private Texture block;
    private Stage stage;
    private SceneManager sceneManager;
    boolean sceneActive = true;
    private ButtonManager buttonManager;

    public Tutorial(SceneManager sceneManager) {
        super(Assets.TUTORIAL_BG.getFileName(), Assets.TUTORIAL_SOUND.getFileName());
        this.sceneManager = sceneManager;
    }

    private ButtonClickListener clickListener = new ButtonClickListener() {
        @Override
        public void onClick(ButtonType btnType) {
            if (btnType.equals(GameButtonType.START)) {
                sceneManager.setScene(GameSceneType.GAME_SCENE_LVL1);
            } else if (btnType.equals(GameButtonType.BACK)) {
                sceneManager.setScene(GameSceneType.MAIN_MENU);
            }
        }
    };

    @Override
    public void show() {
        burger = new Texture(Utils.getInternalFilePath(Assets.BURGER.getFileName()));
        rain = new Texture(Utils.getInternalFilePath(Assets.RAIN.getFileName()));
        block = new Texture(Utils.getInternalFilePath(Assets.BLOCK.getFileName()));

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        // Create labels for texture descriptions
        Label burgerLabel = new Label("Burger: Delicious burger with lettuce, tomato, and cheese", labelStyle);
        Label rainLabel = new Label("rain: Crispy golden rain with a touch of salt", labelStyle);
        Label blockLabel = new Label("block: Refreshing soda with ice cubes", labelStyle);

        // Position labels below their respective textures
        burgerLabel.setPosition(Gdx.graphics.getWidth() / 2 - GameConfig.Xscale(),
                Gdx.graphics.getHeight() / 2 - burger.getHeight() / 2 - 20);
        rainLabel.setPosition(Gdx.graphics.getWidth() / 2 - GameConfig.Xscale(),
                Gdx.graphics.getHeight() / 2 - rain.getHeight() / 2 - 20 - rain.getHeight());
        blockLabel.setPosition(Gdx.graphics.getWidth() / 2 - GameConfig.Xscale(),
                Gdx.graphics.getHeight() / 2 - block.getHeight() / 2 - 20 - block.getHeight() - 20
                        - block.getHeight());

        // Add labels to the stage
        stage.addActor(burgerLabel);
        stage.addActor(rainLabel);
        stage.addActor(blockLabel);

        float buttonSpacing = 50;
        float buttonWidth = GameConfig.BUTTON_WIDTH;
        float buttonHeight = GameConfig.BUTTON_HEIGHT;
        float totalButtonWidth = 3 * buttonWidth + 2 * buttonSpacing;
        float startX = (GameConfig.SCREEN_WIDTH - totalButtonWidth) / 2;
        float buttonY = GameConfig.SCREEN_HEIGHT / 2 - buttonHeight / 2;
        float playButtonX = startX;
        float settingButtonX = startX + buttonWidth + buttonSpacing;

        // Create buttons
        Button startGameBtn = new Button(playButtonX, buttonY, buttonWidth, buttonHeight, GameButtonType.START,
                Assets.BUTTON_BG.getFileName(), GameConfig.GameButtonText.START_BTN.getText(),
                GameConfig.Assets.FONT_PATH.getFileName(), GameConfig.BUTTON_FONT_SIZE);

        Button backBtn = new Button(settingButtonX, buttonY, buttonWidth,
                buttonHeight, GameButtonType.BACK, Assets.BUTTON_BG.getFileName(),
                GameConfig.GameButtonText.BACK_BTN.getText(), GameConfig.Assets.FONT_PATH.getFileName(),
                GameConfig.BUTTON_FONT_SIZE);

        // Register the buttons
        buttonManager = new ButtonManager(clickListener);
        buttonManager.addButton(startGameBtn);
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
        sceneActive = true;
        renderBackground(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.act(delta);

        // Draw the buttons
        buttonManager.drawButtons(batch);

        drawAndPrint(burger, "Burger", 0);
        drawAndPrint(rain, "Rain", 100);
        drawAndPrint(block, "Block", 100 + rain.getHeight());
    }

    private void drawAndPrint(Texture texture, String name, float yOffset) {
        float x = Gdx.graphics.getWidth() / 2 - GameConfig.Xscale();
        float y = Gdx.graphics.getHeight() / 2 - burger.getHeight() / 2 - yOffset;
        batch.draw(texture, x, y, GameConfig.Xscale(), GameConfig.Yscale());
    }

    @Override
    public void dispose() {
        super.dispose();
        /*
         * stage.dispose();
         * burger.dispose();
         * rain.dispose();
         * block.dispose();
         */
    }
}
