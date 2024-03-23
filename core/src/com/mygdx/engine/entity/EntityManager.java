package com.mygdx.engine.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameConfig.GameEntityType;

import java.util.ArrayList;
import java.awt.Point;

public class EntityManager {
    private ArrayList<Entity> entities;

    public EntityManager() {
        entities = new ArrayList<Entity>();
    }

    public void addPlayer(Entity player) {
        System.out.println("Entity: " + (player));
        entities.add(player);
    }

    public void addEntity(Entity entity) {
        System.out.println("Entity: " + (entity));
        entities.add(entity);
    }

    public void addEntities(ArrayList<? extends Entity> entities) {
        this.entities.addAll(entities);
    }

    public void setMovability(ArrayList<? extends Entity> entities, boolean isMovable) {
        for (Entity entity : entities) {
            entity.setMovable(isMovable);
        }
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public void removeEntities(ArrayList<Entity> entities) {
        this.entities.removeAll(entities);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ArrayList<Point> getAllEntityPosition() {
        ArrayList<Point> positions = new ArrayList<Point>();
        for (Entity entity : entities) {
            positions.add(new Point((int) entity.x, (int) entity.y));
        }
        return positions;
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

    public ArrayList<Entity> getAllCollidableEntity() {
        ArrayList<Entity> collidableEntities = new ArrayList<Entity>();
        for (Entity entity : entities) {
            if (entity.isCollidable()) {
                collidableEntities.add(entity);
            }
        }
        return collidableEntities;
    }
    public int getEntitiesCount() {
        return entities.size();
    }

    public int getEntitiesCount(EntityType entityType) {
        return (int) entities.stream()
                .filter(entity -> entity.getEntityType() == entityType)
                .count();
    }

    public void removeEntities() {
        ArrayList<Entity> entitiesToRemove = new ArrayList<Entity>();
        for (Entity entity : entities) {
            if (entity.isToRemove()) {
                entitiesToRemove.add(entity);
            }
        }
        entities.removeAll(entitiesToRemove);
    }

    public void dispose(SpriteBatch batch) {
        for (Entity entity : entities) {
            entity.dispose();
        }
        entities.clear();
    }
}
