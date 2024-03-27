package com.mygdx.engine.collision;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.entity.Entity;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class CollisionManager {

    public static Entity willCollide(Entity entity, Vector2 newPosition, ArrayList<Entity> allEntities) {
        Rectangle newRect = new Rectangle(newPosition.x, newPosition.y, entity.getWidth(), entity.getHeight());
        for (Entity other : allEntities) {
            if (other != entity && newRect.overlaps(other.getRectangle())) {
                return other;
            }
        }
        return null;
    }

    public static boolean isCollidingWith(Entity entity, Entity other) {
        if (other != entity && entity.getRectangle().overlaps(other.getRectangle())) {
            return true; // Collision detected
        }
        return false; // No collision detected
    }

    public static Entity checkHorizontalCollision(Entity entity, Vector2 horizontalMovementDelta,
            ArrayList<Entity> allEntities) {
        return willCollide(entity, new Vector2(entity.getX() + horizontalMovementDelta.x, entity.getY()),
                allEntities);
    }

    public static Entity checkVerticalCollision(Entity entity, Vector2 verticalMovementDelta,
            ArrayList<Entity> allEntities) {
        return willCollide(entity, new Vector2(entity.getX(), entity.getY() + verticalMovementDelta.y), allEntities);
    }

}
