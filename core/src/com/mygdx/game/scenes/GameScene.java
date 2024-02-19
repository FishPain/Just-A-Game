package com.mygdx.game.scenes;

import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.scene.SceneManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.GameConfig.GameSceneType;
import com.mygdx.game.entity.Platform;
import com.mygdx.game.entity.Snake;

import java.util.ArrayList;

public class GameScene extends Scene {
    // this class will load all the required entityies using entity manager

    private EntityManager entityManager;
    private SceneManager sceneManager;

    public GameScene(SceneManager sceneManager, EntityManager entityManager) {
        super();
        this.sceneManager = sceneManager;
        this.entityManager = entityManager;

        entityManager.addPlayer(
                new Snake(GameConfig.SCREEN_WIDTH / 2, GameConfig.SCREEN_HEIGHT / 2, 50, 50,
                "snakeHead.jpg","snakeBody.jpg", 200, GameEntityType.SNAKE_HEAD));
        
        // entityManager.renderPlayers(batch,GameConfig.SCREEN_WIDTH / 2 +50, GameConfig.SCREEN_HEIGHT / 2);

        entityManager.addEntity(
                new Platform(GameConfig.SCREEN_WIDTH / 2 - 150, GameConfig.SCREEN_HEIGHT / 4 + 50, 50,
                        50, "stoneTex.jpg", GameEntityType.PLATFORM));
        entityManager.addEntity(
                new Platform(GameConfig.SCREEN_WIDTH / 2 - 100, GameConfig.SCREEN_HEIGHT / 4, 50, 50,
                        "stoneTex.jpg", GameEntityType.PLATFORM));
        entityManager.addEntity(
                new Platform(GameConfig.SCREEN_WIDTH / 2 - 50, GameConfig.SCREEN_HEIGHT / 4, 50, 50,
                        "stoneTex.jpg", GameEntityType.PLATFORM));
        entityManager.addEntity(new Platform(GameConfig.SCREEN_WIDTH / 2, GameConfig.SCREEN_HEIGHT / 4, 50, 50,
                "stoneTex.jpg", GameEntityType.PLATFORM));
        entityManager.addEntity(
                new Platform(GameConfig.SCREEN_WIDTH / 2 + 50, GameConfig.SCREEN_HEIGHT / 4, 50, 50,
                        "stoneTex.jpg", GameEntityType.PLATFORM));
        entityManager.addEntity(
                new Platform(GameConfig.SCREEN_WIDTH / 2 + 100, GameConfig.SCREEN_HEIGHT / 4, 50, 50,
                        "stoneTex.jpg", GameEntityType.PLATFORM));
        entityManager.addEntity(
                new Platform(GameConfig.SCREEN_WIDTH / 2 + 150, GameConfig.SCREEN_HEIGHT / 4, 50, 50,
                        "stoneTex.jpg", GameEntityType.PLATFORM));
        entityManager.addEntity(
                new Platform(GameConfig.SCREEN_WIDTH / 2 + 100, GameConfig.SCREEN_HEIGHT / 4 + 50, 50,
                        50,
                        "badlogic.jpg", GameEntityType.TARGET));

    }

    @Override
    public void show() {
        // throw new UnsupportedOperationException("Unimplemented method 'show'");
    }

    @Override
    public void hide() {
        // throw new UnsupportedOperationException("Unimplemented method 'hide'");

    }

    @Override
    public void render(float delta) {
        ArrayList<Entity> entities = entityManager.getEntities();

        for (Entity entity : entities) {
            entity.draw(batch);
            entity.move(entities);
            if (entity.isReachEnd(entityManager.getEntities(GameEntityType.TARGET))) {
                System.err.println("Reached the end");
                sceneManager.setScene(GameSceneType.GAME_OVER_WIN);
            }
            // else if (isTimeUp()) {
            // sceneManager.setScene(GameSceneType.GAME_OVER_LOSE);
            // }
        }

    }

}
