package com.mygdx.game.entity;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.entity.EntityManager;

import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.engine.ai.MovementAI;
import com.mygdx.engine.collision.CollisionManager;

import com.mygdx.game.movements.Movement;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.GameEntityType;

public class AIPlayer extends Entity {
    private ArrayList<Vector2> bodyPositions;
    private Texture headTexture, bodyTexture;
    private MovementAI movementAI;
    private Movement movement;
    private EntityManager entityManager;
    private boolean isAppleEffectActive;
    private boolean isCarrotEffectActive;
    private final float segmentSpacing = GameConfig.BLOCK_SIZE;

    public AIPlayer(float x, float y, float width, float height, String headTexturePath, float speed,
            String bodyTexturePath, String entityType, KeyStrokeManager keyStrokeManager,
            EntityManager entityManager) {
        super(x, y, width, height, headTexturePath, speed, true, entityType, true, false);

        this.headTexture = new Texture(Gdx.files.internal(headTexturePath));
        ths.bodyTexture = new Texture(Gdx.files.internal(bodyTexturePath));
        this.movementAI = new MovementAI();
        //this.movement = new Movement(keyStrokeManager, x, speed, false, 0, GameConfig.GRAVITY);
        th is.bodyPositions = new ArrayList<Vector2>();
        // 
        // 
        ths.isAppleEffectActive = false;
        this.isCarrotEffectActive = false;
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
    public void move(ArrayList<Entity> allEntities, float deltaTime) {
        if (this.isMovable) {
            movementAI.applyHorizontalMovement(this, allEntities, bodyPositions, deltaTime);
            movementAI.applyVerticalMovement(this, allEntities, bodyPositions, deltaTime);
        }
        updatePosition();
    }

    @Override
    public boolean isGameEnd() {
        // If the player has eaten all the apples, the game ends
        if (entityManager.getEntities(GameEntityType.APPLE.getValue()).size() == 0
                || entityManager.getEntities(GameEntityType.CARROT.getValue()).size() == 0) {
            for (Entity entity : entityManager.getEntities(GameEntityType.EXIT_PORTAL.getValue())) {
                if (!entity.isVisable()) {
                    entity.setVisable(true);
                } else if (CollisionManager.isCollidingWith(this, entity)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void dispose() {
        headTexture.dispose();
        bodyTexture.dispose();
    }

    private void initializeBodyPositions(float x, float y) {
        for (int i = 1; i <= GameConfig.PLAYER_BODY_LENGTH; i++) {
            bodyPositions.add(new Vector2(x - (i * segmentSpacing), y));
        }
    }
}
