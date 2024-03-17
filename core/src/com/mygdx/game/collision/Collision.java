package com.mygdx.game.collision;

import java.util.ArrayList;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.GameEntityType;

public class Collision extends CollisionManager {

    public static boolean isOnBlock(Entity entity, ArrayList<Entity> blocks, ArrayList<Vector2> bodyPositions) {
        // Check entity's position
        if (isEntityOnBlock(entity, blocks))
            return true;

        // Check body positions
        return bodyPositions.stream().anyMatch(bodyPosition -> isBodyPositionOnBlock(bodyPosition, blocks));
    }

    private static boolean isEntityOnBlock(Entity entity, ArrayList<Entity> blocks) {
        Rectangle slightlyBelow = new Rectangle(entity.getRectangle());
        slightlyBelow.y -= 1; // Check just below the entity
        for (Entity block : blocks) {
            if (block.getEntityType() == GameEntityType.BLOCK
                    && slightlyBelow.overlaps(block.getRectangle())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isBodyPositionOnBlock(Vector2 bodyPosition, ArrayList<Entity> blocks) {
        Rectangle bodyRect = new Rectangle(bodyPosition.x, bodyPosition.y, 1, 1);
        bodyRect.y -= 1;
        for (Entity block : blocks) {
            if (block.getEntityType() == GameEntityType.BLOCK && bodyRect.overlaps(block.getRectangle())) {
                return true;
            }
        }
        return false;
    }

    // A helper method to check horizontal collisions for both head and body
    // segments
    public static boolean checkHorizontalCollision(Entity entity, Vector2 horizontalMovementDelta,
            ArrayList<Entity> allEntities, ArrayList<Vector2> bodyPositions) {
        if (willCollide(entity, new Vector2(entity.getX() + horizontalMovementDelta.x, entity.getY()), allEntities)) {
            return true;
        }
        for (Vector2 body : bodyPositions) {
            if (willCollide(body, new Vector2(body.x + horizontalMovementDelta.x, body.y), allEntities)) {
                return true;
            }
        }
        return false;
    }

    public static boolean willCollide(Entity entity, Vector2 newPosition, ArrayList<Entity> allEntities) {
        Rectangle newRect = new Rectangle(newPosition.x, newPosition.y, entity.getWidth(), entity.getHeight());
        for (Entity other : allEntities) {
            if (other != entity && newRect.overlaps(other.getRectangle())) {
                if (other.getEntityType() == GameEntityType.APPLE) {
                    other.setToRemove(true);
                } else if (other.getEntityType() == GameEntityType.BURGER) {
                    other.setToRemove(true);
                    entity.setSpeed(entity.getSpeed() / GameConfig.SPEED_REDUCTION_FACTOR);
                }
                return true; // Collision detected
            }
        }
        return false; // No collision detected
    }

    public static boolean willCollide(Vector2 entity, Vector2 newPosition, ArrayList<Entity> allEntities) {
        Rectangle newRect = new Rectangle(newPosition.x, newPosition.y, 1, 1);
        for (Entity other : allEntities) {
            if (newRect.overlaps(other.getRectangle())) {
                if (other.getEntityType() == GameEntityType.APPLE || other.getEntityType() == GameEntityType.BURGER) {
                    other.setToRemove(true);
                }
                return true; // Collision detected
            }
        }
        return false; // No collision detected
    }

}
