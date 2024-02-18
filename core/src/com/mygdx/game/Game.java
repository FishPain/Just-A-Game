package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.engine.SimulationManager;
import com.mygdx.engine.ai.AIManager;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.engine.controls.PlayerControlManager;
import com.mygdx.engine.entity.Entity.EntityType;
import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.io.IOManager;
import com.mygdx.engine.scene.SceneManager;

import com.mygdx.game.entity.Snake;
import com.mygdx.game.entity.Platform;

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

        EntityManager.addEntity(new Snake(GameConfig.SCREEN_WIDTH / 2 - 50, GameConfig.SCREEN_HEIGHT / 2, 50, 50,
                "snakeHead.jpg", 200, EntityType.SNAKE_HEAD));

        EntityManager.addEntity(new Snake(GameConfig.SCREEN_WIDTH / 2, GameConfig.SCREEN_HEIGHT / 2, 50, 50,
                "snakeBody.jpg", 200, EntityType.SNAKE_BODY));

        EntityManager.addEntity(new Platform(GameConfig.SCREEN_WIDTH / 2 - 150, GameConfig.SCREEN_HEIGHT / 4 + 50, 50,
                50, "stoneTex.jpg", EntityType.PLATFORM));
        EntityManager.addEntity(new Platform(GameConfig.SCREEN_WIDTH / 2 - 100, GameConfig.SCREEN_HEIGHT / 4, 50, 50,
                "stoneTex.jpg", EntityType.PLATFORM));
        EntityManager.addEntity(new Platform(GameConfig.SCREEN_WIDTH / 2 - 50, GameConfig.SCREEN_HEIGHT / 4, 50, 50,
                "stoneTex.jpg", EntityType.PLATFORM));
        EntityManager.addEntity(new Platform(GameConfig.SCREEN_WIDTH / 2, GameConfig.SCREEN_HEIGHT / 4, 50, 50,
                "stoneTex.jpg", EntityType.PLATFORM));
        EntityManager.addEntity(new Platform(GameConfig.SCREEN_WIDTH / 2 + 50, GameConfig.SCREEN_HEIGHT / 4, 50, 50,
                "stoneTex.jpg", EntityType.PLATFORM));
        EntityManager.addEntity(new Platform(GameConfig.SCREEN_WIDTH / 2 + 100, GameConfig.SCREEN_HEIGHT / 4, 50, 50,
                "stoneTex.jpg", EntityType.PLATFORM));
        EntityManager.addEntity(new Platform(GameConfig.SCREEN_WIDTH / 2 + 150, GameConfig.SCREEN_HEIGHT / 4, 50, 50,
                "stoneTex.jpg", EntityType.PLATFORM));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 1, 1);
        SpriteBatch batch = new SpriteBatch();
        batch.begin();
        EntityManager.render(batch);
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
