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
    private Texture platform;
    private Stage stage;
    private SceneManager sceneManager;
    boolean sceneActive = true;
    protected int Xscale = GameConfig.SCREEN_WIDTH / 16;
    protected int Yscale = GameConfig.SCREEN_HEIGHT / 9;
    private ButtonManager buttonManager;

    public Tutorial(SceneManager sceneManager) {
        super(Assets.TUTORIAL_BG.getFileName(), Assets.TUTORIAL_SOUND.getFileName());
        this.sceneManager = sceneManager;
    }

    private ButtonClickListener clickListener = new ButtonClickListener() {
        @Override
        public void onClick(ButtonType btnType) {
            if (btnType.equals(GameButtonType.START)) {
                sceneManager.setScene(GameSceneType.GAME_SCENE);
            } else if (btnType.equals(GameButtonType.BACK)) {
                sceneManager.setScene(GameSceneType.MAIN_MENU);
            }
        }
    };

    @Override
    public void show() {
        burger = new Texture(Utils.getInternalFilePath(Assets.BURGER.getFileName()));
        rain = new Texture(Utils.getInternalFilePath(Assets.RAIN.getFileName()));
        platform = new Texture(Utils.getInternalFilePath(Assets.PLATFORM.getFileName()));

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        // Create labels for texture descriptions
        Label burgerLabel = new Label("Burger: Delicious burger with lettuce, tomato, and cheese", labelStyle);
        Label rainLabel = new Label("rain: Crispy golden rain with a touch of salt", labelStyle);
        Label platformLabel = new Label("platform: Refreshing soda with ice cubes", labelStyle);

        // Position labels below their respective textures
        burgerLabel.setPosition(Gdx.graphics.getWidth() / 2 - GameConfig.Xscale(),
                Gdx.graphics.getHeight() / 2 - burger.getHeight() / 2 - 20);
        rainLabel.setPosition(Gdx.graphics.getWidth() / 2 - GameConfig.Xscale(),
                Gdx.graphics.getHeight() / 2 - rain.getHeight() / 2 - 20 - rain.getHeight());
        platformLabel.setPosition(Gdx.graphics.getWidth() / 2 - GameConfig.Xscale(),
                Gdx.graphics.getHeight() / 2 - platform.getHeight() / 2 - 20 - platform.getHeight() - 20
                        - platform.getHeight());

        // Add labels to the stage
        stage.addActor(burgerLabel);
        stage.addActor(rainLabel);
        stage.addActor(platformLabel);

        float buttonSpacing = 50;
        float buttonWidth = Xscale;
        float buttonHeight = Yscale;
        float totalButtonWidth = 3 * buttonWidth + 2 * buttonSpacing;
        float startX = (GameConfig.SCREEN_WIDTH - totalButtonWidth) / 2;
        float buttonY = GameConfig.SCREEN_HEIGHT / 2 - buttonHeight / 2;
        float playButtonX = startX;
        float settingButtonX = startX + buttonWidth + buttonSpacing;

        // Create buttons
        Button startGameBtn = new Button(playButtonX, buttonY, buttonWidth, buttonHeight, GameButtonType.START,
                Assets.START_BTN.getFileName());
        Button backBtn = new Button(settingButtonX, buttonY, buttonWidth,
                buttonHeight, GameButtonType.BACK, Assets.BACK_BTN.getFileName());

        // Register the buttons
        buttonManager = new ButtonManager(clickListener);
        buttonManager.addButton(startGameBtn);
        buttonManager.addButton(backBtn);

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
        sceneActive = true;
        renderBackground(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.act(delta);

        // Draw the buttons
        buttonManager.drawButtons(batch);

        drawAndPrint(burger, "Burger", 0);
        drawAndPrint(rain, "Rain", 100);
        drawAndPrint(platform, "Platform", 100 + rain.getHeight());
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
         * platform.dispose();
         */
    }
}
