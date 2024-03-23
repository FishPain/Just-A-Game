package com.mygdx.engine.collision;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.entity.Entity;
import com.mygdx.engine.io.Timer;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.entity.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class CollisionManager {
    protected static GameEntityType collidedEntity;

    public static GameEntityType willCollide(Entity entity, Vector2 newPosition, ArrayList<Entity> allEntities) {

        Rectangle newRect = new Rectangle(newPosition.x, newPosition.y, entity.getWidth(), entity.getHeight());
        for (Entity other : allEntities) {
            if (other != entity && newRect.overlaps(other.getRectangle())) {
                if (other.getEntityType() == GameEntityType.APPLE) {
                    other.setToRemove(true);
                    return GameEntityType.APPLE;
                } else if (other.getEntityType() == GameEntityType.CARROT) {
                    other.setToRemove(true);
                    return GameEntityType.CARROT;
                } else if (other.getEntityType() == GameEntityType.BURGER) {
                    other.setToRemove(true);
                    return GameEntityType.BURGER;
                } else if (other.getEntityType() == GameEntityType.BLOCK) {
                    return GameEntityType.BLOCK;
                }
            }
        }
        return null;
    }

    public static boolean willCollide(Vector2 entity, Vector2 newPosition, ArrayList<Entity> allEntities) {
        Rectangle newRect = new Rectangle(newPosition.x, newPosition.y, 1, 1);
        for (Entity other : allEntities) {
            if (newRect.overlaps(other.getRectangle())) {
                if (other.getEntityType() == GameEntityType.APPLE || other.getEntityType() == GameEntityType.BURGER
                        || other.getEntityType() == GameEntityType.CARROT) {
                    other.setToRemove(true);
                }
                return true; // Collision detected
            }
        }
        return false; // No collision detected
    }

    /*
     * public static boolean willCollide(Entity entity, Vector2 newPosition,
     * ArrayList<Entity> allEntities) {
     * Rectangle newRect = new Rectangle(newPosition.x, newPosition.y,
     * entity.getWidth(), entity.getHeight());
     * for (Entity other : allEntities) {
     * if (other != entity && newRect.overlaps(other.getRectangle())) {
     * return true; // Collision detected
     * }
     * }
     * return false; // No collision detected
     * }
     * 
     * public static boolean willCollide(Vector2 entity, Vector2 newPosition,
     * ArrayList<Entity> allEntities) {
     * Rectangle newRect = new Rectangle(newPosition.x, newPosition.y, 1, 1);
     * for (Entity other : allEntities) {
     * if (newRect.overlaps(other.getRectangle())) {
     * return true; // Collision detected
     * }
     * }
     * return false; // No collision detected
     * }
     */

    public static boolean isCollidingWith(Entity entity, Entity other) {
        if (other != entity && entity.getRectangle().overlaps(other.getRectangle())) {
            return true; // Collision detected
        }
        return false; // No collision detected
    }

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
    public static GameEntityType checkHorizontalCollision(Entity entity, Vector2 horizontalMovementDelta,
            ArrayList<Entity> allEntities, ArrayList<Vector2> bodyPositions) {
        GameEntityType toReturn = willCollide(entity,
                new Vector2(entity.getX() + horizontalMovementDelta.x, entity.getY()),
                allEntities);
        if (toReturn != null) {
            return toReturn;
        }
        // for (Vector2 body : bodyPositions) {
        // if (willCollide(body, new Vector2(body.x + horizontalMovementDelta.x,
        // body.y), allEntities)) {
        // return true;
        // }
        // }
        return null;
    }
}
