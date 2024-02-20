package com.mygdx.engine.controls;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.GameConfig.Keystroke;
import com.mygdx.game.entity.Snake;
import com.mygdx.game.scenes.GameScene;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.engine.scene.SceneManager;
public class PlayerControlManager {
	
	private Snake snake;
	
	SceneManager sceneManager = new SceneManager(); // Initialize SceneManager
    EntityManager entityManager = new EntityManager(); // Initialize EntityManager
    KeyStrokeManager keyStrokeManager = new KeyStrokeManager(); // Initialize KeyStrokeManager

	
    public PlayerControlManager() {
        // Create an instance of GameScene
        GameScene gameScene = new GameScene(sceneManager, entityManager, keyStrokeManager);

        // Access the snake object using the getter method
        snake = gameScene.getSnake();

        // Now you can use the snake object as needed
        // For example:
        handleInput(); // Assuming there's a method like move() in Snake class
    }
	 
	/* private entityManager snake; */
	

	
	public PlayerControlManager(Snake snake) {
        this.snake = snake;
    }

	
	public void handleInput() {
        // Move Snake left or right based on input
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            snake.moveLeft();
        } 
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            snake.moveRight();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            snake.moveUp();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            snake.moveDown();
        }
        
        // Add other input handling if needed
    }
	
	 
	    
	  
	
}
