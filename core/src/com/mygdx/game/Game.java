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
    KeyStrokeManager keyStrokeManager;
    SpriteBatch batch;

    @Override
    public void create() {
        EntityManager = new EntityManager();
        SceneManager = new SceneManager();

        // Load the default key strokes from the file
        keyStrokeManager = new KeyStrokeManager(Keystroke.FILE_PATH.getKeystrokeName());
        batch = new SpriteBatch();

        // <game entry point> main menu screen
        SceneManager.addScene(GameSceneType.MAIN_MENU, new MainMenu(SceneManager));
        SceneManager.setScene(GameSceneType.MAIN_MENU);

        // settings scene
        SceneManager.addScene(GameSceneType.SETTINGS, new Settings(SceneManager));

        // tutorial scene
        SceneManager.addScene(GameSceneType.TUTORIAL, new Tutorial(SceneManager));

        // the game scene level 1
        SceneManager.addScene(GameSceneType.GAME_SCENE_LVL1,
                new GameSceneLvl1(SceneManager, EntityManager, keyStrokeManager));

        // the game scene level 2
        SceneManager.addScene(GameSceneType.GAME_SCENE_LVL2,
                new GameSceneLvl2(SceneManager, EntityManager, keyStrokeManager));

        // end scene
        SceneManager.addScene(GameSceneType.GAME_OVER_WIN, new GameOverWin(SceneManager, EntityManager));
        SceneManager.addScene(GameSceneType.GAME_OVER_LOSE, new GameOverLose(SceneManager, EntityManager));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 1, 1);
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
        EntityManager.dispose(batch);
        batch.dispose();
    }
}
