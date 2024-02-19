package com.mygdx.engine.collision;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.entity.Entity;
import com.mygdx.game.GameConfig.GameEntityType;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class CollisionManager {

    public static boolean willCollide(Entity entity, Vector2 newPosition, ArrayList<Entity> allEntities) {
        Rectangle newRect = new Rectangle(newPosition.x, newPosition.y, entity.getWidth(), entity.getHeight());
        for (Entity other : allEntities) {
            if (other != entity && newRect.overlaps(other.getRectangle())) {
                return true; // Collision detected
            }
        }
        return false; // No collision detected
    }

    public boolean isOnPlatform(Entity entity, ArrayList<Entity> allEntities) {
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
