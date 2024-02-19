package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.entity.EntityType;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.engine.collision.CollisionManager;

import com.mygdx.game.GameConfig.Keystroke;
import com.mygdx.game.movements.Movement;
import com.mygdx.game.collision.Collision;

import java.util.ArrayList;

public class Snake extends Entity {

    KeyStrokeManager keyStrokeManager = new KeyStrokeManager(Keystroke.FILE_PATH.getKeystrokeName());
    private Texture bodyTexture;
    private ArrayList<Vector2> bodySegments;

    public Snake(float x, float y, float width, float height, String headTexturePath, String bodyTexturePath,
            float speed, EntityType entityType) {
        super(x, y, width, height, headTexturePath, speed, true, entityType);
        this.bodyTexture = new Texture(Gdx.files.internal(bodyTexturePath));
        this.bodySegments = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            bodySegments.add(new Vector2(x + (width * i), y)); // Add body segments behind the head
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        // super.draw(batch);
        for (Vector2 segment : bodySegments) {
            batch.draw(getBodyTexture(), segment.x, segment.y, width, height);
        }
    }

    @Override
    public void move(ArrayList<Entity> allEntities) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        Vector2 horizontalMovementDelta = Movement.calculateHorizontalMovement(keyStrokeManager, this.x, this.speed,
                deltaTime);
        Vector2 verticalMovementDelta = Movement.calculateVerticalMovement(0, -GRAVITY, deltaTime);

        // Update head position
        Vector2 newHorizontalPosition = new Vector2(this.x + horizontalMovementDelta.x, this.y);
        boolean horizontalCollision = CollisionManager.willCollide(this, newHorizontalPosition, allEntities);

        if (!horizontalCollision) {
            this.x = newHorizontalPosition.x;
        }
        boolean onPlatform = Collision.isOnPlatform(this, allEntities);
        if (!onPlatform) {
            Vector2 newVerticalPosition = new Vector2(this.x, this.y + verticalMovementDelta.y);
            boolean verticalCollision = CollisionManager.willCollide(this, newVerticalPosition, allEntities);
            if (!verticalCollision) {
                this.y = newVerticalPosition.y;
            }
        }
        // Calculate new head position
        Vector2 newHeadPosition = new Vector2(this.x + horizontalMovementDelta.x, this.y);

        // Check for collision with body segments and head
        boolean collisionDetected = false;
        for (Vector2 segment : bodySegments) {
            if (CollisionManager.willCollide(this, segment, allEntities)) {
                collisionDetected = true;
                break;
            }
        }
        if (!collisionDetected && CollisionManager.willCollide(this, newHeadPosition, allEntities)) {
            collisionDetected = true;
        }

        // Update head position if no collision detected
        if (!collisionDetected) {
            this.x = newHeadPosition.x;
        }

        // Update body segments positions if no collision with head
        if (!CollisionManager.willCollide(this, newHeadPosition, allEntities)) {
            for (int i = bodySegments.size() - 1; i > 0; i--) {
                Vector2 currentSegment = bodySegments.get(i);
                Vector2 previousSegment = bodySegments.get(i - 1);
                currentSegment.set(previousSegment);
            }
            // Set the first body segment to the previous head position
            bodySegments.get(0).set(this.x, this.y);
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

    public ArrayList<Vector2> getBodySegments() {
        ArrayList<Vector2> segments = new ArrayList<>();
        segments.add(new Vector2(x + 50, y)); // Assuming coordinates are (0, 0) for both segments
        segments.add(new Vector2(x + 100, y));
        return segments;
    }

    public Texture getBodyTexture() {
        return this.bodyTexture = new Texture(Gdx.files.internal("snakeBody.jpg"));
    }

    @Override
    public void dispose() {
        super.dispose();
        bodyTexture.dispose();

    }
}