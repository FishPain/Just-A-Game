package com.mygdx.game.collision;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.collision.CollisionManager;
import com.mygdx.engine.entity.Entity;
import com.mygdx.game.GameConfig.GameEntityType;
import com.mygdx.game.entity.Snake;

public class Collision extends CollisionManager {
    public static boolean isOnPlatform(Entity entity, ArrayList<Entity> allEntities) {
        // Extend your CollisionManager or add logic here to check if the entity is
        // standing on a platform
        // This could involve checking for a collision directly beneath the entity,
        // indicating it's supported
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

    public static boolean isOnPlatform(Vector2 entity, ArrayList<Entity> allEntities) {
        // Extend your CollisionManager or add logic here to check if the entity is
        // standing on a platform
        // This could involve checking for a collision directly beneath the entity,
        // indicating it's supported
        for (Entity other : allEntities) {
            if (other.getEntityType() == GameEntityType.PLATFORM) {
                Rectangle slightlyBelow = new Rectangle(entity.x, entity.y, 1, 1);
                slightlyBelow.y -= 1; // Check just below the entity
                if (slightlyBelow.overlaps(other.getRectangle())) {
                    return true;
                }
            }
        }
        return false;
    }

    // A helper method to check horizontal collisions for both head and body
    // segments
    public static boolean checkHorizontalCollision(Entity entity, Vector2 horizontalMovementDelta,
            ArrayList<Entity> allEntities) {
        // Check collision for the entity's head
        if (willCollide(entity, new Vector2(entity.getX() + horizontalMovementDelta.x, entity.getY()), allEntities)) {
            return true;
        }

        // If entity has body segments, check each segment
        if (entity instanceof Snake) { // Assuming Snake is your entity class
            for (Vector2 bodyPos : ((Snake) entity).getBodyPositions()) {
                Vector2 newPos = new Vector2(bodyPos.x + horizontalMovementDelta.x, bodyPos.y);
                if (willCollide(newPos, newPos, allEntities)) {
                    return true;
                }
            }
        }
        return false;
    }

}
