package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.mygdx.engine.scene.SceneManager;
import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.Utils;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameSceneType;

public class Tutorial extends Scene {

    private Texture burger;
    private Texture rain;
    private Texture platform;
    private Stage stage;
    private SceneManager sceneManager;
    boolean sceneActive = true;

    public Tutorial(SceneManager sceneManager) {
        super(Assets.TUTORIAL_BG.getFileName(), Assets.TUTORIAL_SOUND.getFileName());
        this.sceneManager = sceneManager;
    }

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
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyUp(int keycode) {
                if (!sceneActive) {
                    return false;
                }
                if (keycode == Input.Keys.ESCAPE) {
                    sceneManager.setScene(GameSceneType.MAIN_MENU);
                } else {
                    sceneManager.setScene(GameSceneType.GAME_SCENE);
                }
                sceneActive = false;
                return true;
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
        renderBackground(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.act(delta);

        // Placeholder for printing things
        /*
         * batch.draw(burger, Gdx.graphics.getWidth() / 2 - GameConfig.Xscale(),
         * Gdx.graphics.getHeight() / 2 - burger.getHeight() / 2,
         * GameConfig.Xscale(), GameConfig.Yscale());
         * System.out.println("Burger: X = " + (Gdx.graphics.getWidth() / 2 -
         * GameConfig.Xscale()) +
         * ", Y = " + (Gdx.graphics.getHeight() / 2 - burger.getHeight() / 2));
         * 
         * // Draw the second texture (fries) below the burger
         * batch.draw(rain, Gdx.graphics.getWidth() / 2 - GameConfig.Xscale(),
         * Gdx.graphics.getHeight() / 2 - burger.getHeight() / 2 - 50,
         * GameConfig.Xscale(), GameConfig.Yscale());
         * System.out.println("Rain: X = " + (Gdx.graphics.getWidth() / 2 -
         * GameConfig.Xscale()) +
         * ", Y = " + (Gdx.graphics.getHeight() / 2 - burger.getHeight() / 2 - 50));
         * 
         * // Draw the third texture (drink) below the fries
         * batch.draw(platform, Gdx.graphics.getWidth() / 2 - GameConfig.Xscale(),
         * Gdx.graphics.getHeight() / 2 - burger.getHeight() / 2 - 50 -
         * rain.getHeight(),
         * GameConfig.Xscale(), GameConfig.Yscale());
         * System.out.println("Platform: X = " + (Gdx.graphics.getWidth() / 2 -
         * GameConfig.Xscale()) +
         * ", Y = " + (Gdx.graphics.getHeight() / 2 - burger.getHeight() / 2 - 50 -
         * rain.getHeight()));
         */

        drawAndPrint(burger, "Burger", 0);
        drawAndPrint(rain, "Rain", 100);
        drawAndPrint(platform, "Platform", 100 + rain.getHeight());
    }

    private void drawAndPrint(Texture texture, String name, float yOffset) {
        float x = Gdx.graphics.getWidth() / 2 - GameConfig.Xscale();
        float y = Gdx.graphics.getHeight() / 2 - burger.getHeight() / 2 - yOffset;
        batch.draw(texture, x, y, GameConfig.Xscale(), GameConfig.Yscale());
        System.out.println(name + ": X = " + x + ", Y = " + y);
    }

    @Override
    public void dispose() {
        super.dispose();
        /* stage.dispose();
        burger.dispose();
        rain.dispose();
        platform.dispose(); */
    }
}
