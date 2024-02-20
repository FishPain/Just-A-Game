package com.mygdx.game.movements;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.controls.ControlManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.Keystroke;
import com.mygdx.game.collision.Collision;

public class Movement extends Collision {
    private KeyStrokeManager keyStrokeManager;
    private float x, speed;
    private boolean isJumping;
    private float jumpSpeed;
    private float gravity;

    public Movement(KeyStrokeManager keyStrokeManager, float x, float speed, boolean isJumping, float jumpSpeed,
            float gravity) {
        this.keyStrokeManager = keyStrokeManager;
        this.x = x;
        this.speed = speed;
        this.isJumping = isJumping;
        this.jumpSpeed = jumpSpeed;
        this.gravity = gravity;
    }

    public void applyHorizontalMovement(Entity entity, ArrayList<Entity> allEntities,
            ArrayList<Vector2> bodyPositions, float deltaTime) {
        Vector2 horizontalMovementDelta = calculateHorizontalMovement(entity.x, entity.speed,
                deltaTime);
        // Use the CollisionManager for collision checks
        boolean horizontalCollision = checkHorizontalCollision(entity, horizontalMovementDelta,
                allEntities);
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
        boolean isOnPlatform = isOnPlatform(entity, allEntities);
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
            movementDelta = ControlManager.calculateMovement(movementDelta, x, -speed, deltaTime);
        }
        if (keyStrokeManager.isKeyPressed(Keystroke.RIGHT.getKeystrokeName())) {
            movementDelta = ControlManager.calculateMovement(movementDelta, x, speed, deltaTime);
        }
        return movementDelta;
    }
}
