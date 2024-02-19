package com.mygdx.game.collision;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.engine.entity.Entity;
import com.mygdx.game.GameConfig.GameEntityType;

public class Collision {
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
}
