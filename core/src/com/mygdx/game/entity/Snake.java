package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.entity.EntityType;
import com.mygdx.engine.io.KeyStrokeManager;

import com.mygdx.game.movements.Movement;
import com.mygdx.game.GameConfig;
import com.mygdx.game.collision.Collision;

import java.util.ArrayList;

public class Snake extends Entity {
    private ArrayList<Vector2> bodyPositions;
    private Texture headTexture, bodyTexture;
    private Movement movement;
    private final float segmentSpacing = 50; // Fixed distance between segments
    private static final int BODY_SEGMENT_COUNT = 0; // Number of body segments

    public Snake(float x, float y, float width, float height, float speed, String headTexturePath,
            String bodyTexturePath, EntityType entityType, KeyStrokeManager keyStrokeManager) {
        super(x, y, width, height, headTexturePath, speed, true, entityType);
        this.headTexture = new Texture(Gdx.files.internal(headTexturePath));
        this.bodyTexture = new Texture(Gdx.files.internal(bodyTexturePath));
        this.movement = new Movement(keyStrokeManager, x, speed, false, 0, GameConfig.GRAVITY);
        this.bodyPositions = new ArrayList<Vector2>();
        initializeBodyPositions(x, y);
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
    
    public void moveUp() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float movementDeltaY = speed * deltaTime;
        moveVertically(movementDeltaY);
    }

    public void moveDown() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float movementDeltaY = -speed * deltaTime;
        moveVertically(movementDeltaY);
    }
    
    private void moveHorizontally(float deltaX) {
    	System.out.println("Move Horizontally works!");
        Vector2 newHorizontalPosition = new Vector2(this.x + deltaX, this.y);
        System.out.println("newHorizontalPosition = " + newHorizontalPosition);
        // Check collision
        // Apply movement if no collision
        this.x = newHorizontalPosition.x;
        System.out.println("Snake Coords = " + this.x + "," + this.y);
        // REQUIRES GRAPHICS TO UPDATE THE SNAKE POSITION ON SCREEN 
        updatePosition();
    }
    
    private void moveVertically(float deltaY) {
    	System.out.println("Move Vertically works!");
        Vector2 newVerticalPosition = new Vector2(this.x, this.y + deltaY);
        System.out.println("newVerticalPosition = " + newVerticalPosition);
        // Check collision
        // Apply movement if no collision
        this.y = newVerticalPosition.y;
        System.out.println("Snake Coords = " + this.x + "," + this.y);
        // REQUIRES GRAPHICS TO UPDATE THE SNAKE POSITION ON SCREEN 
        updatePosition();
    }


    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(headTexture, x, y, width, height);
        for (Vector2 pos : bodyPositions) {
            batch.draw(bodyTexture, pos.x, pos.y, width, height);
        }
    }

    @Override
    public void move(ArrayList<Entity> allEntities) {
        float deltaTime = Gdx.graphics.getDeltaTime();

        movement.applyHorizontalMovement(this, allEntities, bodyPositions, deltaTime);
        movement.applyVerticalMovement(this, allEntities, bodyPositions, deltaTime);

        updatePosition();
    }

    @Override
    public boolean isReachEnd(ArrayList<Entity> allEntities) {
        return Collision.isReachEnd(this, allEntities, movement);
    }

    @Override
    public void dispose() {
        headTexture.dispose();
        bodyTexture.dispose();
    }

    private void initializeBodyPositions(float x, float y) {
        for (int i = 1; i <= BODY_SEGMENT_COUNT; i++) {
            bodyPositions.add(new Vector2(x - (i * segmentSpacing), y));
        }
    }
}
