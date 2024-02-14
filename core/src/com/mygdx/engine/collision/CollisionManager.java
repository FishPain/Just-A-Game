package com.mygdx.engine.collision;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.engine.entity.Entity;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {

    // Method to check for and return all collisions between entities
    public static List<Collision> checkCollisions(List<Entity> entities) {
        List<Collision> collisions = new ArrayList<>();

        // Compare every entity with every other entity to check for collisions
        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                Entity entityA = entities.get(i);
                Entity entityB = entities.get(j);

                // If the rectangles of the two entities overlap, we have a collision
                if (entityA.getRectangle().overlaps(entityB.getRectangle())) {
                    collisions.add(new Collision(entityA, entityB));
                }
            }
        }

        return collisions;
    }

    public static boolean willCollide(Entity entity, Vector2 newPosition, ArrayList<Entity> allEntities) {
        Rectangle newRect = new Rectangle(newPosition.x, newPosition.y, entity.getWidth(), entity.getHeight());
        for (Entity other : allEntities) {
            if (other != entity && newRect.overlaps(other.getRectangle())) {
                return true; // Collision detected
            }
        }
        return false; // No collision detected
    }

}
