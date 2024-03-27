package com.mygdx.game.ai;

import java.awt.Point;
import java.util.ArrayList;
import com.badlogic.gdx.math.Vector2;

import com.mygdx.engine.ai.MovementAI;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.entity.EntityManager;
import com.mygdx.game.entity.AIPlayer;
import com.mygdx.game.GameConfig;
import com.mygdx.game.collision.GameCollision;

public class AIMovement extends MovementAI {
    private EntityManager entityManager;
    private Vector2 horizontalMovementDelta;
    ArrayList<Point> appleAndCarrotPos;

    public AIMovement(EntityManager entityManager, AIPlayer aiPlayer) {
        this.entityManager = entityManager;
    }

    public void retrieveAppleAndCarrotPos() {
        this.appleAndCarrotPos = entityManager
                .getAllEntityPosition(GameConfig.GameEntityType.APPLE.toString());
        appleAndCarrotPos.addAll(entityManager.getAllEntityPosition(GameConfig.GameEntityType.CARROT.toString()));
    }

    public ArrayList<Point> getAppleAndCarrotPos() {
        return this.appleAndCarrotPos;
    }

    public void applyHorizontalMovement(Entity entity, ArrayList<Entity> allEntities,
            float deltaTime) {

        ArrayList<Point> aiPos = entityManager.getAllEntityPosition(GameConfig.GameEntityType.AI_PLAYER.toString());
        retrieveAppleAndCarrotPos();

        if (appleAndCarrotPos.size() != 0) {
            Point appleAndCarrot = appleAndCarrotPos.get(0);
            Point snake = aiPos.get(0);

            // Move left if snake is to the right of the apple
            if (snake.x > appleAndCarrot.x) {
                horizontalMovementDelta = calculateAIHorizontalMovement(-entity.getSpeed(),
                        deltaTime);
            } else if (snake.x == appleAndCarrot.x) {
                horizontalMovementDelta = calculateAIHorizontalMovement(0, deltaTime);
            } else {
                horizontalMovementDelta = calculateAIHorizontalMovement(entity.getSpeed(),
                        deltaTime);
            }

            Entity horizontalCollision = CollisionManager.checkHorizontalCollision(entity, horizontalMovementDelta,
                    allEntities);

            // Apply horizontal movement if no collision detected
            if (horizontalCollision == null) {
                entity.setX(entity.getX() + horizontalMovementDelta.x);
            }

            else {
                GameCollision.collideEffectAI(horizontalCollision, (AIPlayer) entity, "horizontal");
            }
        }
    }

    public void applyVerticalMovement(Entity entity, ArrayList<Entity> allEntities, float deltaTime) {
        Vector2 verticalMovementDelta;
        ArrayList<Point> aiPos = entityManager.getAllEntityPosition(GameConfig.GameEntityType.AI_PLAYER.toString());
        retrieveAppleAndCarrotPos();

        if (appleAndCarrotPos.size() == 0) {

        } else {
            Point appleAndCarrot = appleAndCarrotPos.get(0);
            Point snake = aiPos.get(0);

            if (snake.y > appleAndCarrot.y) { // MOVE RIGHT
                verticalMovementDelta = calculateVerticalMovement(-entity.getSpeed(), deltaTime);
            } else if (snake.y == appleAndCarrot.y) {
                verticalMovementDelta = calculateAIHorizontalMovement(0, deltaTime);
            }

            else {
                verticalMovementDelta = calculateVerticalMovement(entity.getSpeed(), deltaTime);
            }

            boolean isOnBlock = GameCollision.isEntityOnBlock(entity, allEntities);
            Entity collidedEntity = CollisionManager.willCollide(entity,
                    new Vector2(entity.getX(), entity.getY() + verticalMovementDelta.y),
                    allEntities);

            // Allow upward movement if on a block or if there's no vertical collision
            if (collidedEntity == null || (isOnBlock)) {
                entity.setY(entity.getY() + verticalMovementDelta.y);
            } else {
                GameCollision.collideEffectAI(collidedEntity, (AIPlayer) entity, "vertical");
            }
        }
    }
}
