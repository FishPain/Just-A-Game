package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.entity.EntityType;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.game.movements.Movement;
import com.mygdx.game.collision.Collision;
import java.util.ArrayList;

public class Snake extends Entity {
    private ArrayList<Vector2> bodyPositions;
    private Texture headTexture, bodyTexture;
    private KeyStrokeManager keyStrokeManager;
    private final float segmentSpacing = 50; // Fixed distance between segments
    private static final float GRAVITY = -100f; // Assuming a gravity constant
    private static final int BODY_SEGMENT_COUNT = 1; // Number of body segments

    public Snake(float x, float y, float width, float height, float speed, String headTexturePath,
            String bodyTexturePath, EntityType entityType, KeyStrokeManager keyStrokeManager) {
        super(x, y, width, height, headTexturePath, speed, true, entityType);
        this.headTexture = new Texture(Gdx.files.internal(headTexturePath));
        this.bodyTexture = new Texture(Gdx.files.internal(bodyTexturePath));
        this.keyStrokeManager = keyStrokeManager;
        this.bodyPositions = new ArrayList<Vector2>();
        initializeBodyPositions(x, y);
    }

    private void initializeBodyPositions(float x, float y) {
        for (int i = 1; i <= BODY_SEGMENT_COUNT; i++) {
            bodyPositions.add(new Vector2(x - (i * segmentSpacing), y));
        }
        System.err.println("Body positions: " + bodyPositions.toString());
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
        Vector2 horizontalMovementDelta = Movement.calculateHorizontalMovement(keyStrokeManager, this.x, this.speed,
                deltaTime);
        Vector2 verticalMovementDelta = new Vector2(0, GRAVITY * deltaTime);

        // Initially check for head's horizontal and vertical collisions
        boolean horizontalCollision = CollisionManager.willCollide(this,
                new Vector2(this.x + horizontalMovementDelta.x, this.y), allEntities);

        boolean verticalCollision = CollisionManager.willCollide(this,
                new Vector2(this.x, verticalMovementDelta.y + this.y), allEntities);

        // If no collision for the head, check for each body segment
        if (!horizontalCollision) {
            for (Vector2 bodyPos : bodyPositions) {
                Vector2 newPos = new Vector2(bodyPos.x + horizontalMovementDelta.x, bodyPos.y);
                if (CollisionManager.willCollide(newPos, newPos, allEntities)) {
                    horizontalCollision = true;
                    break; // Break out of the loop if any body segment collides
                }
            }
        }

        // Apply horizontal movement if no collision detected
        if (!horizontalCollision) {
            this.x += horizontalMovementDelta.x;
            // Update body positions horizontally
            for (int i = 0; i < bodyPositions.size(); i++) {
                Vector2 bodyPos = bodyPositions.get(i);
                bodyPositions.set(i, new Vector2(bodyPos.x + horizontalMovementDelta.x, bodyPos.y));
            }
        }

        // Check if the snake's head or any body part is on a platform
        boolean isOnPlatform = Collision.isOnPlatform(this, allEntities);
        for (int i = 0; !isOnPlatform && i < bodyPositions.size(); i++) {
            Vector2 bodyPos = bodyPositions.get(i);
            if (Collision.isOnPlatform(bodyPos, allEntities)) {
                isOnPlatform = true;
                break;
            }
        }

        // Apply vertical movement if no collision detected and not on a platform
        if (!verticalCollision && !isOnPlatform) {
            this.y += verticalMovementDelta.y;
            // Update body positions vertically
            for (int i = 0; i < bodyPositions.size(); i++) {
                Vector2 bodyPos = bodyPositions.get(i);
                bodyPositions.set(i, new Vector2(bodyPos.x, bodyPos.y + verticalMovementDelta.y));
            }
        } else if (isOnPlatform) {
            // Logic to handle when the snake is on a platform, e.g., stop falling
            // Optionally adjust the snake's vertical position if needed
        }

        updatePosition(); // Update the entity's rectangle for collision checks
    }

    public boolean isReachEnd(ArrayList<Entity> entity) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        Vector2 horizontalMovementDelta = Movement.calculateHorizontalMovement(keyStrokeManager, this.x, this.speed,
                deltaTime);
        Vector2 newHorizontalPosition = new Vector2(this.x + horizontalMovementDelta.x, this.y);
        boolean horizontalCollision = CollisionManager.willCollide(this, newHorizontalPosition, entity);
        if (horizontalCollision) {
            return true;
        }
        return false;
    }

    @Override
    public void dispose() {
        headTexture.dispose();
        bodyTexture.dispose();
    }
}
