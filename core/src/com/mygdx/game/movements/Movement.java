package com.mygdx.game.movements;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.engine.controls.ControlManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.io.KeyStrokeManager;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.GameConfig.Keystroke;
import com.mygdx.game.collision.GameCollision;
import com.mygdx.game.entity.Player;

public class Movement extends CollisionManager {
    private KeyStrokeManager keyStrokeManager;

    public Movement(KeyStrokeManager keyStrokeManager, float x, float speed, boolean isJumping, float jumpSpeed,
            float gravity) {
        this.keyStrokeManager = keyStrokeManager;
    }

    public void applyHorizontalMovement(Entity entity, ArrayList<Entity> allEntities, float deltaTime) {
        Vector2 horizontalMovementDelta = calculateHorizontalMovement(entity.getSpeed(),
                deltaTime);

        // Use the CollisionManager for collision checks
        Entity horizontalCollision = CollisionManager.checkHorizontalCollision(entity, horizontalMovementDelta,
                allEntities);

        // Apply horizontal movement if no collision detected
        if (horizontalCollision == null) {
            entity.setX(entity.getX() + horizontalMovementDelta.x);
        } else {
            GameCollision.collideEffect(horizontalCollision, (Player) entity);
        }
        else
        {
            Collision.collideEffect(horizontalCollision, (Player) entity);
        }
    }

    public Vector2 calculateHorizontalMovement(float speed,
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

    public void applyVerticalMovement(Entity entity, ArrayList<Entity> allEntities, float deltaTime) {
        Vector2 verticalMovementDelta = calculateVerticalMovement(entity.getSpeed(), deltaTime);
        boolean isOnBlock = CollisionManager.isEntityOnBlock(entity, allEntities);
        Entity collidedEntity = CollisionManager.willCollide(entity,
                new Vector2(entity.getX(), entity.getY() + verticalMovementDelta.y),
                allEntities);

        if (collidedEntity == null
                || (isOnBlock && keyStrokeManager.isKeyPressed(Keystroke.UP.getKeystrokeName()))) {
            entity.setY(entity.getY() + verticalMovementDelta.y);
        } else {
            GameCollision.collideEffect(collidedEntity, (Player) entity);
        }
    }

    public Vector2 calculateVerticalMovement(float speed,
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

    /* public void applyMovement(Entity entity, ArrayList<Entity> allEntities, ArrayList<Vector2> bodyPositions, float deltaTime)
    {
        //horizontal movement
        Vector2 horizontalMovementDelta = calculateHorizontalMovement(entity.getSpeed(),
                deltaTime);
        // Update body positions horizontally
        entity.setX(entity.getX() + horizontalMovementDelta.x);
        for (int i = 0; i < bodyPositions.size(); i++) {
            Vector2 bodyPos = bodyPositions.get(i);
            bodyPositions.set(i, new Vector2(bodyPos.x + horizontalMovementDelta.x, bodyPos.y));
        }

        //vertical movement
        Vector2 verticalMovementDelta = calculateVerticalMovement(entity.getSpeed(), deltaTime);
        // Update body positions vertically
        entity.setY(entity.getY() + verticalMovementDelta.y);
        for (int i = 0; i < bodyPositions.size(); i++) {
            Vector2 bodyPos = bodyPositions.get(i);
            bodyPositions.set(i, new Vector2(bodyPos.x, bodyPos.y + verticalMovementDelta.y));
        }
    } */
}
