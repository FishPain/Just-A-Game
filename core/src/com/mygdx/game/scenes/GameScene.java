package com.mygdx.game.scenes;

import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.scene.Scene;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.entity.Platform;
import com.mygdx.game.entity.Snake;

public class GameScene extends Scene {
    // this class will load all the required entityies using entity manager

    private EntityManager entityManager;

    public GameScene(EntityManager entityManager) {
        super();

        entityManager.addEntity(
                new Snake(GameConfig.SCREEN_WIDTH / 2 - 50, GameConfig.SCREEN_HEIGHT / 2, 50, 50,
                        "snakeHead.jpg", 200, GameEntityType.SNAKE_HEAD));

        entityManager.addEntity(new Snake(GameConfig.SCREEN_WIDTH / 2, GameConfig.SCREEN_HEIGHT / 2, 50, 50,
                "snakeBody.jpg", 200, GameEntityType.SNAKE_BODY));

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
    }

    @Override
    public void show() {

        throw new UnsupportedOperationException("Unimplemented method 'show'");
    }

    @Override
    public void hide() {
        throw new UnsupportedOperationException("Unimplemented method 'hide'");

    }

    @Override
    public void render(float delta) {
        entityManager.render();
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }

}
