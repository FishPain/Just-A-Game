package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import com.mygdx.engine.SimulationManager;
import com.mygdx.engine.ai.AIManager;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.io.IOManager;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.engine.scene.SceneManager;
import com.mygdx.engine.controls.PlayerControlManager;
import com.mygdx.game.scenes.GameOverWin;
import com.mygdx.game.scenes.GameOverLose;
import com.mygdx.game.scenes.GameScene;
// scenes
import com.mygdx.game.scenes.MainMenu;

// entities
import com.mygdx.game.GameConfig.GameSceneType;
import com.mygdx.game.GameConfig.Keystroke;
import com.mygdx.game.scenes.Settings;

public class Game extends SimulationManager {

    EntityManager EntityManager;
    CollisionManager CollisionManager;
    AIManager AIManager;
    IOManager InputManager;
    SceneManager SceneManager;
    PlayerControlManager PlayerControlManager;
    KeyStrokeManager keyStrokeManager;
    SpriteBatch batch;

    @Override
    public void create() {
        AIManager = new AIManager();
        InputManager = new IOManager();
        PlayerControlManager = new PlayerControlManager();
        CollisionManager = new CollisionManager();
        EntityManager = new EntityManager();

        SceneManager = new SceneManager();
        batch = new SpriteBatch();

        // Load the default key strokes from the file
        keyStrokeManager = new KeyStrokeManager(Keystroke.FILE_PATH.getKeystrokeName());

        // <game entry point> main menu screen
        SceneManager.addScene(GameSceneType.MAIN_MENU, new MainMenu(SceneManager));
        SceneManager.setScene(GameSceneType.MAIN_MENU);

        // settings scene
        SceneManager.addScene(GameSceneType.SETTINGS, new Settings(SceneManager));

        // the main game scene
        SceneManager.addScene(GameSceneType.GAME_SCENE, new GameScene(SceneManager, EntityManager, keyStrokeManager));

        // end scene
        SceneManager.addScene(GameSceneType.GAME_OVER_WIN, new GameOverWin(SceneManager));
        SceneManager.addScene(GameSceneType.GAME_OVER_LOSE, new GameOverLose(SceneManager));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 1, 1);
        batch.begin();
        SceneManager.getCurrentScene().setBatch(batch);
        PlayerControlManager.handleInput();
        SceneManager.getCurrentScene().render(0); // Assuming render method now accepts a SpriteBatch
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
