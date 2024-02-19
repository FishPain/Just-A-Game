package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.entity.EntityType;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.engine.collision.CollisionManager;

import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.GameConfig.Keystroke;
import com.mygdx.game.movements.Movement;
import com.mygdx.engine.controls.ControlManager;

import java.util.ArrayList;

public class Snake extends Entity {

    KeyStrokeManager keyStrokeManager = new KeyStrokeManager(Keystroke.FILE_PATH.getKeystrokeName());

    public Snake(float x, float y, float width, float height, String texturePath, float speed, EntityType entityType) {
        super(x, y, width, height, texturePath, speed, true, entityType);
    }

    @Override
    public void move(ArrayList<Entity> allEntities) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        Vector2 horizontalMovementDelta = Movement.calculateHorizontalMovement(keyStrokeManager, this.x, this.speed,
                deltaTime);
        Vector2 verticalMovementDelta = Movement.calculateVerticalMovement(0, -GRAVITY, deltaTime);

        // Check for horizontal movement possibility
        Vector2 newHorizontalPosition = new Vector2(this.x + horizontalMovementDelta.x, this.y);
        boolean horizontalCollision = CollisionManager.willCollide(this, newHorizontalPosition, allEntities);

        // Apply horizontal movement if no collision detected
        if (!horizontalCollision) {
            this.x = newHorizontalPosition.x;
        }

        // Apply vertical movement (gravity) if not on a platform
        boolean onPlatform = isOnPlatform(this, allEntities);
        if (!onPlatform) {
            Vector2 newVerticalPosition = new Vector2(this.x, this.y + verticalMovementDelta.y);
            boolean verticalCollision = CollisionManager.willCollide(this, newVerticalPosition, allEntities);
            if (!verticalCollision) {
                this.y = newVerticalPosition.y;
            }
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

    private Vector2 calculateHorizontalMovement(float deltaTime) {
        Vector2 movementDelta = new Vector2();
        if (keyStrokeManager.isKeyPressed(Keystroke.LEFT.getKeystrokeName())) {
            movementDelta = ControlManager.calculateMovement(movementDelta, x, -speed, deltaTime);
        }
        if (keyStrokeManager.isKeyPressed(Keystroke.RIGHT.getKeystrokeName())) {
            movementDelta = ControlManager.calculateMovement(movementDelta, x, speed, deltaTime);
        }
        return movementDelta;
    }

    private Vector2 calculateVerticalMovement(float deltaTime) {
        // This assumes gravity is constantly applied, you might adjust if you have
        // jumping logic
        Vector2 movementDelta = new Vector2(0, -GRAVITY * deltaTime);
        return ControlManager.calculateMovement(movementDelta, 0, -GRAVITY, deltaTime);
    }

    private boolean isOnPlatform(Entity entity, ArrayList<Entity> allEntities) {
        for (Entity other : allEntities) {
            if (other != entity && other.getEntityType() == GameEntityType.PLATFORM) {
                Rectangle slightlyBelow = new Rectangle(entity.getRectangle());
                slightlyBelow.y -= 1; // Check just below the entity
                if (slightlyBelow.overlaps(other.getRectangle())) {
                    return true;
                }
            }
        }
        return false;
    }

}