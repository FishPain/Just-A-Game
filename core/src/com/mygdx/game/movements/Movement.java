package com.mygdx.game.movements;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.controls.PlayerControlManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Keystroke;
import com.mygdx.game.collision.Collision;

public class Movement extends Collision {
    private KeyStrokeManager keyStrokeManager;

    public Movement(KeyStrokeManager keyStrokeManager, float x, float speed, boolean isJumping, float jumpSpeed,
            float gravity) {
        this.keyStrokeManager = keyStrokeManager;
    }
    
    public static Vector2 calculateMovement(Vector2 movementDelta, float x, float speed, float deltaTime) {
        movementDelta.x += speed * deltaTime;
        return movementDelta;
    }

    public static Vector2 calculateMovement(Vector2 movementDelta, float gravity, float deltaTime) {
        movementDelta.y += gravity * deltaTime;
        return movementDelta;
    }

    public void applyHorizontalMovement(Entity entity, ArrayList<Entity> allEntities,
            ArrayList<Vector2> bodyPositions, float deltaTime) {
        Vector2 horizontalMovementDelta = calculateHorizontalMovement(entity.x, entity.speed,
                deltaTime);
        // Use the CollisionManager for collision checks
        boolean horizontalCollision = checkHorizontalCollision(entity, horizontalMovementDelta,
                allEntities);

        if (!horizontalCollision) {
            for (Vector2 body : bodyPositions) {
                if (checkHorizontalCollision(body, horizontalMovementDelta,
                        allEntities)) {
                    horizontalCollision = true;
                    break;
                }
            }
        }

        // Apply horizontal movement if no collision detected
        if (!horizontalCollision) {
            entity.x += horizontalMovementDelta.x;
            // Update body positions horizontally
            for (int i = 0; i < bodyPositions.size(); i++) {
                Vector2 bodyPos = bodyPositions.get(i);
                bodyPositions.set(i, new Vector2(bodyPos.x + horizontalMovementDelta.x, bodyPos.y));
            }
        }
    }

    public void applyVerticalMovement(Entity entity, ArrayList<Entity> allEntities,
            ArrayList<Vector2> bodyPositions, float deltaTime) {
        Vector2 verticalMovementDelta = new Vector2(0, GameConfig.GRAVITY * deltaTime);
        // only fall if neither the entity nor its body segments are on a platform
        boolean isOnPlatform = isOnPlatform(entity, allEntities);
        if (!isOnPlatform) {
            for (Vector2 body : bodyPositions) {
                if (isOnPlatform(body, allEntities)) {
                    isOnPlatform = true;
                    break;
                }
            }
        }
        boolean verticalCollision = willCollide(entity,
                new Vector2(entity.x, entity.y + verticalMovementDelta.y), allEntities);

        // Apply vertical movement if no collision detected and not on a platform
        if (!verticalCollision && !isOnPlatform) {
            entity.y += verticalMovementDelta.y;
            // Update body positions vertically
            for (int i = 0; i < bodyPositions.size(); i++) {
                Vector2 bodyPos = bodyPositions.get(i);
                bodyPositions.set(i, new Vector2(bodyPos.x, bodyPos.y + verticalMovementDelta.y));
            }
        }
    }

    public Vector2 calculateHorizontalMovement(float x, float speed,
            float deltaTime) {
        Vector2 movementDelta = new Vector2();
        if (keyStrokeManager.isKeyPressed(Keystroke.LEFT.getKeystrokeName())) {
            movementDelta = calculateMovement(movementDelta, x, -speed, deltaTime);
        }
        if (keyStrokeManager.isKeyPressed(Keystroke.RIGHT.getKeystrokeName())) {
            movementDelta = calculateMovement(movementDelta, x, speed, deltaTime);
        }
        return movementDelta;
    }
}
