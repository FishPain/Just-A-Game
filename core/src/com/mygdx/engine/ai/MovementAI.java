package com.mygdx.engine.ai;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.engine.controls.ControlManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.entity.EntityManager;
import com.mygdx.game.scenes.GameSceneLvl2;

import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.GameConfig.Keystroke;
import com.mygdx.game.collision.GameCollision;
import com.mygdx.game.entity.AIPlayer;
import com.mygdx.game.entity.Player;

public class MovementAI extends CollisionManager {
    private CollisionManager collisionManager;
    private EntityManager entityManager;
    private GameSceneLvl2 gameSceneLvl2;

    // private AIManager aiManager = new AIManager();
    // Call getEntityPositionsByType with the desired entityType

    // get all the apple position
    // ArrayList<Point> applePositions =
    // aiManager.getEntityPositionsByType(GameConfig.GameEntityType.APPLE);
    // public ArrayList<Point> Apples() {
    // ArrayList<Point> applePositions =
    // aiManager.getEntityPositionsByType(GameConfig.GameEntityType.APPLE);
    // // System.out.println("Number of apple positions: " + applePositions.size());
    // for (Point position : applePositions) {
    // System.out.println("Apple position: (" + position.getX() + ", " +
    // position.getY() + ")");
    // }
    // return applePositions;
    // }

    // public void AIMovement(float x, float speed, boolean isJumping, float
    // jumpSpeed,
    // float gravity) {

    // }
    public MovementAI(EntityManager entityManager) {
        this.entityManager = entityManager;

    }

    public void applyHorizontalMovement(Entity entity, ArrayList<Entity> allEntities,
            ArrayList<Vector2> bodyPositions, float deltaTime) {

        Vector2 horizontalMovementDelta;

        Point snakePos;
        ArrayList<Point> applePos;
        snakePos = AIManager.getAISnakePosition();
        applePos = entityManager.getAllApplePosition();

        if (applePos.size() == 0) {

        } else {
            Point apple = applePos.get(0);

            System.out.println("SNAKE POINT : " + snakePos);
            System.out.println("APPLE POINT : " + applePos);

            if (snakePos.x > apple.x) { // MOVE RIGHt
                horizontalMovementDelta = calculateHorizontalMovement(-entity.getSpeed(), deltaTime);
            } else {
                horizontalMovementDelta = calculateHorizontalMovement(entity.getSpeed(), deltaTime);
            }
            // Use the CollisionManager for collision checks
            Entity horizontalCollision = CollisionManager.checkHorizontalCollision(entity, horizontalMovementDelta,
                    allEntities);

            // Apply horizontal movement if no collision detected
            if (horizontalCollision == null) {
                entity.setX(entity.getX() + horizontalMovementDelta.x);
            } else {
                GameCollision.collideEffectAI(horizontalCollision, (AIPlayer) entity);
            }
        }

    }

    public Vector2 calculateHorizontalMovement(float speed,
            float deltaTime) {
        // Vector2 movementDelta = new Vector2();
        // if (keyStrokeManager.isKeyPressed(Keystroke.LEFT.getKeystrokeName())) {
        // movementDelta = ControlManager.calculateMovement(movementDelta, -speed *
        // deltaTime, 0);
        // }
        // if (keyStrokeManager.isKeyPressed(Keystroke.RIGHT.getKeystrokeName())) {
        // movementDelta = ControlManager.calculateMovement(movementDelta, speed *
        // deltaTime, 0);
        // }
        // return movementDelta;
        Vector2 movementDelta = new Vector2();

        movementDelta = ControlManager.calculateMovement(movementDelta, speed * deltaTime, 0);

        // Move to the right by default
        // movementDelta = ControlManager.calculateMovement(movementDelta, speed *
        // deltaTime, 0);

        return movementDelta;
    }

    public void applyVerticalMovement(Entity entity, ArrayList<Entity> allEntities, ArrayList<Vector2> bodyPositions,
            float deltaTime) {

        Vector2 verticalMovementDelta;

        Point snakePos;
        ArrayList<Point> applePos;
        snakePos = AIManager.getAISnakePosition();
        applePos = entityManager.getAllApplePosition();
        if (applePos.size() == 0) {

        } else {
            Point apple = applePos.get(0);

            System.out.println("SNAKE POINT : " + snakePos);
            System.out.println("APPLE POINT : " + applePos);

            if (snakePos.y > apple.y) { // MOVE RIGHT
                verticalMovementDelta = calculateVerticalMovement(-entity.getSpeed(), deltaTime);
            } else {
                verticalMovementDelta = calculateVerticalMovement(entity.getSpeed(), deltaTime);
            }

            boolean isOnBlock = CollisionManager.isEntityOnBlock(entity, allEntities);
            Entity collidedEntity = CollisionManager.willCollide(entity,
                    new Vector2(entity.getX(), entity.getY() + verticalMovementDelta.y),
                    allEntities);

            // Allow upward movement if on a block or if there's no vertical collision
            if (collidedEntity == null || (isOnBlock)) {
                entity.setY(entity.getY() + verticalMovementDelta.y);
            } else {
                GameCollision.collideEffectAI(collidedEntity, (AIPlayer) entity);
            }
        }

    }

    public Vector2 calculateVerticalMovement(float speed,
            float deltaTime) {
        Vector2 movementDelta = new Vector2();
        // if (keyStrokeManager.isKeyPressed(Keystroke.UP.getKeystrokeName())) {
        // movementDelta = ControlManager.calculateMovement(movementDelta, 0, speed *
        // deltaTime);
        // }
        // if (keyStrokeManager.isKeyPressed(Keystroke.DOWN.getKeystrokeName())) {
        // movementDelta = ControlManager.calculateMovement(movementDelta, 0, -speed *
        // deltaTime);
        // }

        movementDelta = ControlManager.calculateMovement(movementDelta, 0, speed * deltaTime);

        // movementDelta = ControlManager.calculateMovement(movementDelta, 0, speed *
        // deltaTime);
        return movementDelta;
    }

}
