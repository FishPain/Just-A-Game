package com.mygdx.game.ai;

import java.awt.Point;
import java.util.ArrayList;
import com.badlogic.gdx.math.Vector2;

import com.mygdx.engine.ai.MovementAI;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.entity.EntityManager;
import com.mygdx.game.entity.AIPlayer;
import com.mygdx.game.collision.GameCollision;

public class AIMovement extends MovementAI {
    private EntityManager entityManager;
    private Vector2 horizontalMovementDelta;

    public AIMovement(EntityManager entityManager, AIPlayer aiPlayer) {
        this.entityManager = entityManager;
    }

    public void applyHorizontalMovement(Entity entity, ArrayList<Entity> allEntities,
            ArrayList<Vector2> bodyPositions, float deltaTime) {

        ArrayList<Point> snakePos = entityManager.getAllAISnakePosition();
        ArrayList<Point> applePos = entityManager.getAllApplePosition();

        if (applePos.size() != 0) {
            Point apple = applePos.get(0);
            Point snake = snakePos.get(0);

            System.out.println("SNAKE POINT : " + snakePos);
            System.out.println("APPLE POINT : " + applePos);

            // Move left if snake is to the right of the apple
            if (snake.x > apple.x) {
                horizontalMovementDelta = calculateAIHorizontalMovement(-entity.getSpeed(),
                        deltaTime);
            } else if (snake.x == apple.x) {
                horizontalMovementDelta = calculateAIHorizontalMovement(0, deltaTime);
            } else {
                horizontalMovementDelta = calculateAIHorizontalMovement(entity.getSpeed(),
                        deltaTime);
            }

            Entity horizontalCollision = CollisionManager.checkHorizontalCollision(entity, horizontalMovementDelta,
                    allEntities);

            System.out.println("ENTITY TYPE 1: " + entity.getEntityType());

            // Apply horizontal movement if no collision detected
            if (horizontalCollision == null) {
                entity.setX(entity.getX() + horizontalMovementDelta.x);
            }

            else {
                System.out.println("ENTITY TYPE 2: " + entity.getEntityType());
                if (entity.getEntityType() == "AI_PLAYER") {
                    System.out.println("ENTITY TYPE == AI_PLAYER");
                }
                GameCollision.collideEffectAI(horizontalCollision, (AIPlayer) entity, "horizontal");
            }
        }
    }

    public void applyVerticalMovement(Entity entity, ArrayList<Entity> allEntities, ArrayList<Vector2> bodyPositions,
            float deltaTime) {
        Vector2 verticalMovementDelta;
        ArrayList<Point> snakePos;
        ArrayList<Point> applePos;
        snakePos = entityManager.getAllAISnakePosition();
        applePos = entityManager.getAllApplePosition();
        if (applePos.size() == 0) {

        } else {
            Point apple = applePos.get(0);
            Point snake = snakePos.get(0);

            System.out.println("SNAKE POINT : " + snakePos);
            System.out.println("APPLE POINT : " + applePos);

            if (snake.y > apple.y) { // MOVE RIGHT
                verticalMovementDelta = calculateVerticalMovement(-entity.getSpeed(), deltaTime);
            } else if (snake.y == apple.y) {
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
