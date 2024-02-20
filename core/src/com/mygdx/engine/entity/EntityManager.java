package com.mygdx.engine.entity;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entity.Snake;

public class EntityManager {
    private ArrayList<Entity> entities;

    public EntityManager() {
        entities = new ArrayList<Entity>();
        new ArrayList<>();
    }

    public void addPlayer(Snake player) {
        System.out.println("Entity entity: " + (player));

        entities.add(player);
    }

    public void addEntity(Entity entity) {
        System.out.println("Entity entity: " + (entity));
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    // get all entities by type
    public ArrayList<Entity> getEntities(EntityType entityType) {
        ArrayList<Entity> entitiesByType = new ArrayList<Entity>();
        for (Entity entity : entities) {
            if (entity.getEntityType() == entityType) {
                entitiesByType.add(entity);
            }
        }
        return entitiesByType;
    }

    public void dispose(SpriteBatch batch) {
        for (Entity entity : entities) {
            entity.dispose();
        }
        batch.end();
        batch.dispose();
    }
}
