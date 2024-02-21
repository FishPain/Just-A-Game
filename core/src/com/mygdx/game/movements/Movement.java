package com.mygdx.game.movements;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.controls.ControlManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.game.GameConfig.Keystroke;
import com.mygdx.game.collision.Collision;

public class Movement extends Collision {
    private KeyStrokeManager keyStrokeManager;

    public Movement(KeyStrokeManager keyStrokeManager, float x, float speed, boolean isJumping, float jumpSpeed,
            float gravity) {
        this.keyStrokeManager = keyStrokeManager;
    }

    public void applyHorizontalMovement(Entity entity, ArrayList<Entity> allEntities,
            ArrayList<Vector2> bodyPositions, float deltaTime) {
        Vector2 horizontalMovementDelta = calculateHorizontalMovement(entity.x, entity.speed,
                deltaTime);

        // Use the CollisionManager for collision checks
        boolean horizontalCollision = checkHorizontalCollision(entity, horizontalMovementDelta,
                allEntities, bodyPositions);

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

    public Vector2 calculateHorizontalMovement(float x, float speed,
            float deltaTime) {
        Vector2 movementDelta = new Vector2();
        if (keyStrokeManager.isKeyPressed(Keystroke.LEFT.getKeystrokeName())) {
            movementDelta = ControlManager.calculateMovement(movementDelta, -speed * deltaTime, 0);
        }
        if (keyStrokeManager.isKeyPressed(Keystroke.RIGHT.getKeystrokeName())) {
            movementDelta = ControlManager.calculateMovement(movementDelta, speed * deltaTime, 0);
        }
        return movementDelta;
    }

    public void applyVerticalMovement(Entity entity, ArrayList<Entity> allEntities, ArrayList<Vector2> bodyPositions,
            float deltaTime) {
        Vector2 verticalMovementDelta = calculateVerticalMovement(entity.y, entity.speed, deltaTime);
        boolean isOnPlatform = isOnPlatform(entity, allEntities, bodyPositions);

        boolean verticalCollision = willCollide(entity, new Vector2(entity.x, entity.y + verticalMovementDelta.y),
                allEntities);

        // Allow upward movement if on a platform or if there's no vertical collision
        if (!verticalCollision || (isOnPlatform && keyStrokeManager.isKeyPressed(Keystroke.UP.getKeystrokeName()))) {
            entity.y += verticalMovementDelta.y;
            // Update body positions vertically
            for (int i = 0; i < bodyPositions.size(); i++) {
                Vector2 bodyPos = bodyPositions.get(i);
                bodyPositions.set(i, new Vector2(bodyPos.x, bodyPos.y + verticalMovementDelta.y));
            }
        }
    }

    public Vector2 calculateVerticalMovement(float y, float speed,
            float deltaTime) {
        Vector2 movementDelta = new Vector2();
        if (keyStrokeManager.isKeyPressed(Keystroke.UP.getKeystrokeName())) {
            movementDelta = ControlManager.calculateMovement(movementDelta, 0, speed * deltaTime);
        }
        if (keyStrokeManager.isKeyPressed(Keystroke.DOWN.getKeystrokeName())) {
            movementDelta = ControlManager.calculateMovement(movementDelta, 0, -speed * deltaTime);
        }
        return movementDelta;
    }
}
