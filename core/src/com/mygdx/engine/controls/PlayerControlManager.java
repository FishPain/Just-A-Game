package com.mygdx.engine.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.game.entity.Snake;

public class PlayerControlManager {
	private Snake snake;
	private float speed;
	
	public void PlayerController(Snake snake) {
        this.snake = snake;
        
       
    }
	public void moveLeft() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float movementDeltaX = -speed * deltaTime;
        moveHorizontally(movementDeltaX);
    }

    public void moveRight() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float movementDeltaX = speed * deltaTime;
        moveHorizontally(movementDeltaX);
    }
	
	public void handleInput() {
        // Move Snake left or right based on input
        if (Gdx.input.isKeyPressed(Input.Keys.T)) {
            moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.Y)) {
            moveRight();
        }
        // Add other input handling if needed
    }
	
	 
	    
	  
	private void moveHorizontally(float deltaX) {
		System.out.println("TEST WORKS!!");
//	        Vector2 newHorizontalPosition = new Vector2(this.x + deltaX, this.y);
//	        boolean horizontalCollision = CollisionManager.willCollide(this, newHorizontalPosition, allEntities);
//	        if (!horizontalCollision) {
//	            this.x = newHorizontalPosition.x;
//	            updatePosition(); // Update the entity's rectangle for collision checks
	      //  }
	    }
}
