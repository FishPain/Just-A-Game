package com.mygdx.game.scenes;

import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.engine.io.SoundEffects;
import com.mygdx.engine.scene.Scene;
import com.mygdx.engine.scene.SceneManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.engine.entity.Entity;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Assets;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.GameConfig.GameSceneType;
import com.mygdx.game.entity.PlatformManager;
import com.mygdx.game.entity.Snake;

import java.util.ArrayList;

public class GameScene extends Scene {
        // this class will load all the required entityies using entity manager

        Snake snake;

        private EntityManager entityManager;
        private SceneManager sceneManager;
        private SoundEffects sound;
        KeyStrokeManager keyStrokeManager;

        public GameScene(SceneManager sceneManager, EntityManager entityManager, KeyStrokeManager keyStrokeManager) {
                super();
                this.sceneManager = sceneManager;
                this.entityManager = entityManager;
                this.sound = GameSceneType.GAME_SCENE.getSound();

                entityManager.addEntities(PlatformManager.createPlatforms(GameConfig.PLATFORM_POSITIONS));

                snake = new Snake(GameConfig.SCREEN_WIDTH / 2, GameConfig.SCREEN_HEIGHT / 2, 50, 50,
                                200, Assets.SNAKE_HEAD.getFileName(), Assets.SNAKE_BODY.getFileName(),
                                GameEntityType.SNAKE_HEAD, keyStrokeManager);

                setSnake(snake);
                entityManager.addPlayer(snake);

                /*
                 * entityManager.addPlayer(
                 * new Snake(GameConfig.SCREEN_WIDTH / 2, GameConfig.SCREEN_HEIGHT / 2, 50, 50,
                 * 200, Assets.SNAKE_HEAD.getFileName(), Assets.SNAKE_BODY.getFileName(),
                 * GameEntityType.SNAKE_HEAD, keyStrokeManager));
                 */
        }

        public Snake getSnake() {
                return snake;
        }

        public void setSnake(Snake snake) {
                this.snake = snake;
        }

        @Override
        public void show() {
                // throw new UnsupportedOperationException("Unimplemented method 'show'");
                sound.play(GameConfig.MUSIC_VOLUME);
        }

        @Override
        public void hide() {
                // throw new UnsupportedOperationException("Unimplemented method 'hide'");
                sound.stop();
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

                        else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) { // Temporary placeholder to test GAME OVER
                                                                              // LOSE scene
                                System.err.println("YOU LOST!");
                                sceneManager.setScene(GameSceneType.GAME_OVER_LOSE);
                        }

                }

        }

}
