package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.engine.SimulationManager;
import com.mygdx.engine.ai.AIManager;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.engine.controls.PlayerControlManager;
import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.entity.EntityType;
import com.mygdx.engine.io.IOManager;
import com.mygdx.engine.scene.SceneManager;
import com.mygdx.game.scenes.GameOver;
import com.mygdx.game.scenes.GameScene;
// scenes
import com.mygdx.game.scenes.MainMenu;

// entities
import com.mygdx.game.entity.Snake;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.entity.Platform;
import com.mygdx.game.scenes.GameScene;
import com.mygdx.game.scenes.GameOver;
import com.mygdx.game.scenes.MainMenu;
import com.mygdx.game.scenes.Settings;

public class Game extends SimulationManager {

    EntityManager EntityManager;
    CollisionManager CollisionManager;
    AIManager AIManager;
    IOManager InputManager;
    SceneManager SceneManager;
    PlayerControlManager PlayerControlManager;

    @Override
    public void create() {
        AIManager = new AIManager();
        InputManager = new IOManager();
        SceneManager = new SceneManager();
        PlayerControlManager = new PlayerControlManager();
        CollisionManager = new CollisionManager();
        EntityManager = new EntityManager();

        // <game entry point> main menu screen
        SceneManager.addScene("MainMenu", new MainMenu());
        SceneManager.setScene("MainMenu");

        // settings scene
        SceneManager.addScene("Settings", new Settings());

        // the main game scene
        SceneManager.addScene("MainGame", new GameScene(EntityManager));

        // end scene
        SceneManager.addScene("EndGame", new GameOver());
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 1, 1);
        SpriteBatch batch = new SpriteBatch();
        batch.begin();
        SceneManager.getCurrentScene().render(0);
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
    }
}
