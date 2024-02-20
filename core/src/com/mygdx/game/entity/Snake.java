package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.entity.EntityType;
import com.mygdx.engine.io.KeyStrokeManager;

import com.mygdx.game.movements.Movement;
import com.mygdx.game.collision.Collision;

import java.util.ArrayList;

public class Snake extends Entity {
    private ArrayList<Vector2> bodyPositions;
    private Texture headTexture, bodyTexture;
    private KeyStrokeManager keyStrokeManager;
    private final float segmentSpacing = 50; // Fixed distance between segments
    private static final float GRAVITY = -100f; // Assuming a gravity constant
    private static final int BODY_SEGMENT_COUNT = 2; // Number of body segments

    public Snake(float x, float y, float width, float height, float speed, String headTexturePath,
            String bodyTexturePath, EntityType entityType, KeyStrokeManager keyStrokeManager) {
        super(x, y, width, height, headTexturePath, speed, true, entityType);
        this.headTexture = new Texture(Gdx.files.internal(headTexturePath));
        this.bodyTexture = new Texture(Gdx.files.internal(bodyTexturePath));
        this.keyStrokeManager = keyStrokeManager;
        this.bodyPositions = new ArrayList<Vector2>();
        initializeBodyPositions(x, y);
    }

    public ArrayList<Vector2> getBodyPositions() {
        return bodyPositions;
    }

    private void initializeBodyPositions(float x, float y) {
        for (int i = 1; i <= BODY_SEGMENT_COUNT; i++) {
            bodyPositions.add(new Vector2(x - (i * segmentSpacing), y));
        }
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

        // Use the CollisionManager for collision checks
        boolean horizontalCollision = Collision.checkHorizontalCollision(this, horizontalMovementDelta,
                allEntities);
        boolean verticalCollision = Collision.willCollide(this,
                new Vector2(this.x, this.y + verticalMovementDelta.y), allEntities);
        boolean isOnPlatform = Collision.isOnPlatform(this, allEntities);

        // Apply horizontal movement if no collision detected
        if (!horizontalCollision) {
            this.x += horizontalMovementDelta.x;
            // Update body positions horizontally
            for (int i = 0; i < bodyPositions.size(); i++) {
                Vector2 bodyPos = bodyPositions.get(i);
                bodyPositions.set(i, new Vector2(bodyPos.x + horizontalMovementDelta.x, bodyPos.y));
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
        }

        updatePosition(); // Update the entity's rectangle for collision checks
    }

    public boolean isReachEnd(ArrayList<Entity> entity) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        Vector2 horizontalMovementDelta = Movement.calculateHorizontalMovement(keyStrokeManager, this.x, this.speed,
                deltaTime);
        Vector2 newHorizontalPosition = new Vector2(this.x + horizontalMovementDelta.x, this.y);
        boolean horizontalCollision = Collision.willCollide(this, newHorizontalPosition, entity);
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
