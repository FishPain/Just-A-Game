package com.mygdx.engine.collision;

import java.util.ArrayList;
import com.mygdx.engine.entity.EntityManager;
import com.mygdx.engine.entity.Entity;

public class CollisionManager extends EntityManager {
    private ArrayList<Entity> collidableEntities;

    public CollisionManager() {
        collidableEntities = new ArrayList<>();
    }

    // public void checkCollisions() {
    //     updateCollidableEntities();
    //     for (int i = 0; i < collidableEntities.size(); i++) {
    //         for (int j = i + 1; j < collidableEntities.size(); j++) {
    //             Entity entityA = collidableEntities.get(i);
    //             Entity entityB = collidableEntities.get(j);
    //             if (entityA.isColliding(entityB)) {
    //                 // Entities are colliding, handle collision...
    //             }
    //         }
    //     }
    // }

    // private void updateCollidableEntities() {
    //     collidableEntities.clear(); // Clear the list to avoid duplicates
    //     for (Entity entity : getEntities()) {
    //         if (entity.isCollidable()) {
    //             collidableEntities.add(entity);
    //         }
    //     }
    // }
}
