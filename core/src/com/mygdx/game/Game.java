package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.engine.SimulationManager;
import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.engine.scene.SceneManager;
import com.mygdx.game.scenes.GameOverWin;
import com.mygdx.game.scenes.GameOverLose;
import com.mygdx.game.scenes.GameSceneLvl1;
import com.mygdx.game.scenes.GameSceneLvl2;
import com.mygdx.game.scenes.Tutorial;
// scenes
import com.mygdx.game.scenes.MainMenu;
import com.mygdx.game.scenes.Settings;
// entities
import com.mygdx.game.GameConfig.GameSceneType;
import com.mygdx.game.GameConfig.Keystroke;

public class Game extends SimulationManager {

    EntityManager EntityManager;
    SceneManager SceneManager;
    KeyStrokeManager KeyStrokeManager;
    SpriteBatch batch;

    @Override
    public void create() {
        EntityManager = new EntityManager();
        SceneManager = new SceneManager();

        // Load the default key strokes from the file
        KeyStrokeManager = new KeyStrokeManager(Keystroke.FILE_PATH.getKeystrokeName());
        batch = new SpriteBatch();

        // <game entry point> main menu screen
        SceneManager.addScene(new MainMenu(SceneManager));
        SceneManager.setScene(GameSceneType.MAIN_MENU.getValue());

        // settings scene
        SceneManager.addScene(new Settings(SceneManager));

        // tutorial scene
        SceneManager.addScene(new Tutorial(SceneManager));

        // the game scene level 1
        SceneManager.addScene(
                new GameSceneLvl1(SceneManager, EntityManager, KeyStrokeManager));

        // the game scene level 2
        SceneManager.addScene(
                new GameSceneLvl2(SceneManager, EntityManager, KeyStrokeManager));

        // end scene
        SceneManager.addScene(new GameOverWin(SceneManager));
        SceneManager.addScene(new GameOverLose(SceneManager));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        float deltaTime = Gdx.graphics.getDeltaTime();
        batch.begin();
        SceneManager.getCurrentScene().setBatch(batch);
        SceneManager.getCurrentScene().render(deltaTime); // Assuming render method now accepts a SpriteBatch
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        EntityManager.dispose();
        SceneManager.dispose();
        KeyStrokeManager.dispose();
        batch.dispose();
    }
}
