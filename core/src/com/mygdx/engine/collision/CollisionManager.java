package com.mygdx.engine.collision;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.entity.Entity;
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

    public static boolean willCollide(Vector2 entity, Vector2 newPosition, ArrayList<Entity> allEntities) {
        Rectangle newRect = new Rectangle(newPosition.x, newPosition.y, 1, 1);
        for (Entity other : allEntities) {
            if (newRect.overlaps(other.getRectangle())) {
                return true; // Collision detected
            }
        }
        return false; // No collision detected
    }

    public static boolean isCollidingWith(Entity entity, Entity other) {
        if (other != entity && entity.getRectangle().overlaps(other.getRectangle())) {
            return true; // Collision detected
        }
        return false; // No collision detected
    }
}
